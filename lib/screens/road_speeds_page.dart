import 'dart:convert';
import 'dart:typed_data';
import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:archive/archive_io.dart';
import '../services/api_service.dart';

class RoadSpeedsPage extends StatefulWidget {
  final ApiService apiService;

  RoadSpeedsPage({required this.apiService});

  @override
  _RoadSpeedsPageState createState() => _RoadSpeedsPageState();
}

class _RoadSpeedsPageState extends State<RoadSpeedsPage> {
  late GoogleMapController mapController;
  Set<Marker> _markers = {};
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _fetchRoadSpeeds();
  }

  Future<void> _fetchRoadSpeeds({String? sentido}) async {
    setState(() {
      _isLoading = true;
    });
    try {
      final bytes = await widget.apiService.fetchRoadSpeeds(sentido: sentido);
      final markers = await _processKMZBytes(bytes);
      setState(() {
        _markers = markers;
      });
      print('Velocidades das vias atualizadas.');
    } catch (e) {
      print('Erro ao buscar velocidades das vias: $e');
    }
    setState(() {
      _isLoading = false;
    });
  }

  Future<Set<Marker>> _processKMZBytes(Uint8List bytes) async {
    final archive = ZipDecoder().decodeBytes(bytes);

    Set<Marker> markers = {};

    for (final file in archive) {
      if (file.isFile && file.name.endsWith('.kml')) {
        final content = utf8.decode(file.content as List<int>);
        // Processar o conteúdo do arquivo KML e criar marcadores
        // Esta parte depende do formato do arquivo KML
        // Aqui você precisa analisar o KML e extrair as coordenadas para criar os marcadores
      }
    }

    return markers;
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Velocidades das Vias'),
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : GoogleMap(
              onMapCreated: _onMapCreated,
              initialCameraPosition: CameraPosition(
                target: LatLng(-23.5505, -46.6333),
                zoom: 12,
              ),
              markers: _markers,
            ),
    );
  }
}