// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables, use_super_parameters, avoid_print

import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'package:provider/provider.dart';
import 'package:sp_transporte/services/services.dart';
import 'package:sp_transporte/utils/provider.dart';
import 'package:sp_transporte/widgets/search_lines.dart';

class StopPoints extends StatefulWidget {
  const StopPoints({Key? key}) : super(key: key);

  @override
  State<StopPoints> createState() => _StopPointsState();
}

class _StopPointsState extends State<StopPoints> {
  final SpTransService _spTransService = SpTransService();
  final MapController _mapController = MapController();
  List<dynamic> _stopPoints = [];
  String _codigoLinha = '';

  void _loadStopPoints() async {
    try {
      final stopPoints = await _spTransService.fetchParadas(_codigoLinha);
      setState(() {
        _stopPoints = stopPoints;
      });

      _fitMapToBounds();
    } catch (e) {
      print(e);
    }
  }

  void _fitMapToBounds() {
    if (_stopPoints.isEmpty) return;

    var latitudes = _stopPoints.map((pos) => pos['py'] as double).toList();
    var longitudes = _stopPoints.map((pos) => pos['px'] as double).toList();

    var bounds = LatLngBounds(
      LatLng(latitudes.reduce((a, b) => a < b ? a : b),
          longitudes.reduce((a, b) => a < b ? a : b)),
      LatLng(latitudes.reduce((a, b) => a > b ? a : b),
          longitudes.reduce((a, b) => a > b ? a : b)),
    );

    _mapController.fitCamera(
        CameraFit.bounds(bounds: bounds, padding: EdgeInsets.all(50.0)));
  }

  List<Marker> _buildMarkers() {
    return _stopPoints.map((pos) {
      return Marker(
        point: LatLng(pos['py'], pos['px']),
        width: 80,
        height: 80,
        child: Icon(
          Icons.location_on,
          color: Colors.red,
        ),
      );
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    final linhaProvider = Provider.of<LinhaProvider>(context);

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.primary,
        title: Text(
          'Paradas',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Pesquise e selecione a linha na qual você deseja exibir as paradas.',
              style: TextStyle(fontSize: 16.0),
            ),
            SizedBox(height: 20),
            SearchLines(
              onSuggestionSelected: (p0) {
                setState(() {
                  _codigoLinha = linhaProvider.linha;
                  _loadStopPoints();
                });
              },
            ),
            SizedBox(height: 20),
            Expanded(
              child: FlutterMap(
                mapController: _mapController,
                options: MapOptions(
                  initialCenter: LatLng(-23.55052, -46.633308),
                  initialZoom: 12.0,
                ),
                children: [
                  TileLayer(
                    urlTemplate:
                        "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
                    subdomains: ['a', 'b', 'c'],
                  ),
                  MarkerLayer(
                    markers: _buildMarkers(),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
