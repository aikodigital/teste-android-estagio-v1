import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';

import '../services/olho_vivo_service.dart';

class MapTab extends StatefulWidget {
  @override
  _MapTabState createState() => _MapTabState();
}

class _MapTabState extends State<MapTab> {
  GoogleMapController? _controller;
  final Set<Marker> _markers = {};
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadBusPositions();
  }

  Future<void> _loadBusPositions() async {
    try {
      final api = Provider.of<OlhoVivoService>(context, listen: false);
      await api.authenticate();
      final busPositions = await api.getBusPositions();

      setState(() {
        for (var position in busPositions) {
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
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : GoogleMap(
        initialCameraPosition: const CameraPosition(
          target: LatLng(-23.5505, -46.6333),
          zoom: 13,
        ),
        markers: _markers,
        onMapCreated: (controller) => _controller = controller,
      ),
    );
  }
}
