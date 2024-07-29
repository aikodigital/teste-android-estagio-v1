import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import '../services/api_service.dart';

class CorridorsPage extends StatefulWidget {
  final ApiService apiService;

  CorridorsPage({required this.apiService});

  @override
  _CorridorsPageState createState() => _CorridorsPageState();
}

class _CorridorsPageState extends State<CorridorsPage> {
  late GoogleMapController mapController;
  Set<Marker> _markers = {};
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _initialize();
  }

  Future<void> _initialize() async {
    setState(() {
      _isLoading = true;
    });
    try {
      await widget.apiService.authenticate();
      await _fetchCorridors();
    } catch (e) {
      print('Erro durante a inicialização: $e');
    }
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _fetchCorridors() async {
    try {
      print('Buscando corredores...');
      final corridors = await widget.apiService.fetchCorridors();
      setState(() {
        _markers = corridors.map((corridor) {
          // Aqui você terá que ajustar as coordenadas dos corredores
          // No exemplo, estou usando coordenadas fictícias
          return Marker(
            markerId: MarkerId(corridor['cc'].toString()),
            position: LatLng(-23.5505, -46.6333), // Ajuste conforme necessário
            icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueGreen),
            infoWindow: InfoWindow(
              title: 'Corredor ${corridor['nc']}',
            ),
          );
        }).toSet();
      });
      print('Corredores atualizados.');
    } catch (e) {
      print('Erro ao buscar corredores: $e');
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Corredores'),
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