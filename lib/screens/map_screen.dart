import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';

import '../models/bus_line_model.dart';
import '../models/curridor_model.dart';
import '../services/olho_vivo_service.dart';

class MapScreen extends StatefulWidget {
  BusLineModel? busLine;
  CurridorModel? curridor;

  MapScreen({super.key, this.busLine, this.curridor});

  @override
  _MapScreenState createState() => _MapScreenState();
}

class _MapScreenState extends State<MapScreen> {
  GoogleMapController? _controller;
  final Set<Marker> _markers = {};
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadBusPositions();
    _loadStops();
  }

  Future<void> _loadBusPositions() async {
    try {
      final api = Provider.of<OlhoVivoService>(context, listen: false);
      await api.authenticate();
      final busPositions = await api.getBusPositions();

      setState(() {
        for (var position in busPositions) {
          if (position['cl'] == widget.busLine!.id) {
            for (var vehicle in position['vs']) {
              _markers.add(
                Marker(
                  markerId: MarkerId(vehicle['p'].toString()),
                  position: LatLng(vehicle['py'], vehicle['px']),
                  infoWindow: InfoWindow(
                    title: 'Linha ${position['c']}',
                    snippet: 'Prefixo: ${vehicle['p']}',
                  ),
                ),
              );
            }
          }
        }
        _isLoading = false;
      });
    } catch (e) {
      print('Error loading bus positions: $e');
      setState(() {
        _isLoading = false;
      });
    }
  }

  Future<void> _loadStops() async {
    try {
      final api = Provider.of<OlhoVivoService>(context, listen: false);
      await api.authenticate();
      final stops = await api.searchStopsByCorridor(widget.curridor!.id);

      setState(() {
        _markers.clear();
        for (var stop in stops) {
          _markers.add(
            Marker(
              markerId: MarkerId(stop['cp'].toString()),
              position: LatLng(stop['py'], stop['px']),
              infoWindow: InfoWindow(
                title: stop['np'],
                snippet: 'Código: ${stop['cp']}',
              ),
            ),
          );
        }
        _isLoading = false;
      });
    } catch (e) {
      print('Error loading stops: $e');
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: widget.busLine != null
            ? Text('Ônibus da linha ${widget.busLine!.primaryLine}')
            : widget.curridor != null
            ? Text('Corredor ${widget.curridor!.name}')
            : Text(''),
        backgroundColor: Theme.of(context).primaryColor,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : GoogleMap(
        initialCameraPosition: const CameraPosition(
          target: LatLng(-23.5505, -46.6333),
          zoom: 12,
        ),
        markers: _markers,
        onMapCreated: (controller) => _controller = controller,
      ),
    );
  }
}
