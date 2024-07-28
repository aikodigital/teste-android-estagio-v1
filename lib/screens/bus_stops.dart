import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import '../services/api_service.dart';

class BusStops extends StatefulWidget {
  @override
  _BusStopsState createState() => _BusStopsState();
}

class _BusStopsState extends State<BusStops> {
  late GoogleMapController mapController;
  final ApiService apiService = ApiService(
      '28449b6fc5bffd3d0f282bf499f6296c79650c7c04d244bb1d74ad485b8e5a62');
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
      await apiService.authenticate();
      await _fetchAllBusStops();
    } catch (e) {
      print('Erro durante a inicialização: $e');
    }
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _fetchAllBusStops() async {
    try {
      print('Buscando todos os pontos de parada...');
      final stops = await apiService.fetchBusStops('');
      setState(() {
        _markers = stops.map((stop) {
          return Marker(
            markerId: MarkerId(stop['cp'].toString()),
            position: LatLng(stop['py'], stop['px']),
            icon:
                BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
            infoWindow: InfoWindow(
              title: 'Parada ${stop['np']}',
              snippet: stop['ed'],
            ),
          );
        }).toSet();
      });
      print('Todos os pontos de parada atualizados.');
    } catch (e) {
      print('Erro ao buscar todos os pontos de parada: $e');
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Paradas de Ônibus'),
      ),
      body: Column(
        children: [
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextField(
              decoration: InputDecoration(
                hintText: 'Pesquisar linhas de ônibus',
                suffixIcon: IconButton(
                  icon: Icon(Icons.search),
                  onPressed: () {
                    _fetchAllBusStops();
                  },
                ),
              ),
            ),
          ),
          Expanded(
            child: _isLoading
                ? Center(child: CircularProgressIndicator())
                : GoogleMap(
                    onMapCreated: _onMapCreated,
                    initialCameraPosition: CameraPosition(
                      target: LatLng(-23.5505, -46.6333),
                      zoom: 12,
                    ),
                    markers: _markers,
                  ),
          ),
        ],
      ),
    );
  }
}
