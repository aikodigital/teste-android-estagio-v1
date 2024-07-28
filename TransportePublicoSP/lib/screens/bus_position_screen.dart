import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:transportepublicosp/services/api_service.dart';
import 'dart:async';

class BusPositionScreen extends StatefulWidget {
  final String lineCode;
  final List<Map<String, dynamic>> busPositions;
  final Map<String, dynamic> busLine;

  const BusPositionScreen({
    super.key,
    required this.lineCode,
    required this.busPositions,
    required this.busLine,
  });

  @override
  // ignore: library_private_types_in_public_api
  _BusPositionScreenState createState() => _BusPositionScreenState();
}

class _BusPositionScreenState extends State<BusPositionScreen> {
  GoogleMapController? mapController;
  final Set<Marker> _markers = {};
  BitmapDescriptor? busIcon;
  Timer? _timer;
  final ApiService apiService = ApiService();
  LatLng? _lastPosition;

  @override
  void initState() {
    super.initState();
    _loadBusIcon();
    _setInitialBusPositions(widget.busPositions);
    _startUpdatingBusPositions();
  }

  Future<void> _loadBusIcon() async {
    busIcon = await BitmapDescriptor.asset(
      const ImageConfiguration(size: Size(35, 35)), 
      'assets/images/busloc_icon.png',
    );
  }

  void _setInitialBusPositions(List<Map<String, dynamic>> busPositions) {
    if (busPositions.isNotEmpty) {
      final initialPosition = LatLng(
        busPositions[0].containsKey('latitude')
            ? busPositions[0]['latitude']
            : busPositions[0]['py'],
        busPositions[0].containsKey('longitude')
            ? busPositions[0]['longitude']
            : busPositions[0]['px'],
      );

      setState(() {
        _markers.add(
          Marker(
            markerId: const MarkerId('bus'),
            position: initialPosition,
            icon: busIcon ?? BitmapDescriptor.defaultMarker,
          ),
        );
        _lastPosition = initialPosition;
      });
    }
  }

  void _startUpdatingBusPositions() {
    _timer = Timer.periodic(const Duration(seconds: 15), (timer) {
      _updateBusPositions();
    });
    _updateBusPositions();
  }

  Future<void> _updateBusPositions() async {
    List<Map<String, dynamic>> busPositions =
        await apiService.fetchBusPositions(widget.lineCode);

    if (!mounted) {
      return; // #TODO Verificar se o widget ainda está montado antes de chamar setState
    }

    if (busPositions.isNotEmpty) {
      LatLng newPosition = LatLng(
        busPositions[0].containsKey('latitude')
            ? busPositions[0]['latitude']
            : busPositions[0]['py'],
        busPositions[0].containsKey('longitude')
            ? busPositions[0]['longitude']
            : busPositions[0]['px'],
      );
      _animateMarker(newPosition);
    }
  }

  void _animateMarker(LatLng newPosition) {
    if (_lastPosition == null) {
      // Primeira vez, apenas posiciona o marcador
      setState(() {
        _markers.clear();
        _markers.add(
          Marker(
            markerId: const MarkerId('bus'),
            position: newPosition,
            icon: busIcon ?? BitmapDescriptor.defaultMarker,
          ),
        );
        _lastPosition = newPosition;
        mapController!.moveCamera(CameraUpdate.newLatLng(newPosition));
      });
    } else {
      LatLng start = _lastPosition!;
      double animationDuration = 1500;
      int steps = 30;

      for (int i = 1; i <= steps; i++) {
        Future.delayed(
            Duration(milliseconds: (animationDuration / steps).round() * i),
            () {
          if (!mounted) return;

          double progress = i / steps;
          double lat =
              _interpolate(start.latitude, newPosition.latitude, progress);
          double lng =
              _interpolate(start.longitude, newPosition.longitude, progress);
          LatLng interpolatedPosition = LatLng(lat, lng);

          setState(() {
            _markers.clear();
            _markers.add(
              Marker(
                markerId: const MarkerId('bus'),
                position: interpolatedPosition,
                icon: busIcon ?? BitmapDescriptor.defaultMarker,
              ),
            );
          });

          if (i == steps) {
            _lastPosition = newPosition;
          }

          mapController!
              .moveCamera(CameraUpdate.newLatLng(interpolatedPosition));
        });
      }
    }
  }

  double _interpolate(double start, double end, double progress) {
    return start + (end - start) * progress;
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
    _updateBusPositions();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Localização dos Ônibus'),
      ),
      body: GoogleMap(
        onMapCreated: _onMapCreated,
        initialCameraPosition: const CameraPosition(
          target: LatLng(-23.555211, -46.635581), // Posição inicial do mapa
          zoom: 18.0, // Nível de zoom inicial
        ),
        markers: _markers,
        scrollGesturesEnabled: false, // Desabilitar movimentos de scroll
        zoomGesturesEnabled: false, // Desabilitar movimentos de zoom
        tiltGesturesEnabled: false, // Desabilitar movimentos de tilt
        rotateGesturesEnabled: false, // Desabilitar movimentos de rotação
      ),
    );
  }
}
