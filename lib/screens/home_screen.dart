import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import '../services/api_service.dart';
import 'bus_stops.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late GoogleMapController mapController;
  final ApiService apiService = ApiService(
      '28449b6fc5bffd3d0f282bf499f6296c79650c7c04d244bb1d74ad485b8e5a62');
  Set<Marker> _markers = {};
  final TextEditingController _searchController = TextEditingController();
  List<dynamic> _busLines = [];
  List<dynamic> _busStops = [];
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    _initialize();
  }

  Future<void> _initialize() async {
    try {
      print('Iniciando autenticação...');
      await apiService.authenticate();
      print('Autenticação concluída. Buscando posições dos veículos...');
      final positions = await apiService.fetchVehiclePositions();
      setState(() {
        _markers = positions.map((position) {
          return Marker(
            markerId: MarkerId(position['vs'][0]['p'].toString()),
            position: LatLng(position['vs'][0]['py'], position['vs'][0]['px']),
            infoWindow: InfoWindow(
              title: 'Ônibus ${position['vs'][0]['p']}',
              snippet: 'Linha ${position['c']}',
            ),
          );
        }).toSet();
      });
      print('Posições dos veículos atualizadas.');
    } catch (e) {
      print('Erro durante a inicialização: $e');
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
  }

  Future<void> _searchBusLines() async {
    final searchTerm = _searchController.text;
    final lines = await apiService.fetchBusLines(searchTerm);
    setState(() {
      _busLines = lines;
    });
    print('Linhas de ônibus encontradas: $_busLines');
  }

  Future<void> _fetchCorridors() async {
    setState(() {
      _isLoading = true;
    });
    try {
      final corridors = await apiService.fetchCorridors();
      setState(() {
        _markers = corridors.map((corridor) {
          return Marker(
            markerId: MarkerId(corridor['cc'].toString()),
            position: LatLng(corridor['py'], corridor['px']),
            icon: BitmapDescriptor.defaultMarkerWithHue(
                BitmapDescriptor.hueGreen),
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
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _fetchRoadSpeeds() async {
    setState(() {
      _isLoading = true;
    });
    try {
      final speeds = await apiService.fetchRoadSpeeds();
      setState(() {
        _markers = speeds.map((speed) {
          return Marker(
            markerId: MarkerId(speed['id'].toString()),
            position: LatLng(speed['py'], speed['px']),
            icon:
                BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueRed),
            infoWindow: InfoWindow(
              title: 'Velocidade: ${speed['velocidade']} km/h',
            ),
          );
        }).toSet();
      });
      print('Velocidades das vias atualizadas.');
    } catch (e) {
      print('Erro ao buscar velocidades das vias: $e');
    }
    setState(() {
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Transporte Público SP'),
      ),
      body: Column(
        children: [
          Padding(
            padding: EdgeInsets.all(8.0),
            child: TextField(
              controller: _searchController,
              decoration: InputDecoration(
                hintText: 'Pesquisar linhas de ônibus',
                suffixIcon: IconButton(
                  icon: Icon(Icons.search),
                  onPressed: _searchBusLines,
                ),
              ),
            ),
          ),
          Padding(
            padding: EdgeInsets.symmetric(horizontal: 8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                Expanded(
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue, // Background color
                      foregroundColor: Colors.white, // Text color
                    ),
                    onPressed: _fetchCorridors,
                    child: Text('Corredores'),
                  ),
                ),
                SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue, // Background color
                      foregroundColor: Colors.white, // Text color
                    ),
                    onPressed: _fetchRoadSpeeds,
                    child: Text('Velocidades das Vias'),
                  ),
                ),
                SizedBox(width: 8),
                Expanded(
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blue, // Background color
                      foregroundColor: Colors.white, // Text color
                    ),
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(builder: (context) => BusStops()),
                      );
                    },
                    child: Text('Paradas'),
                  ),
                ),
              ],
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
          Expanded(
            child: ListView.builder(
              itemCount: _busLines.length,
              itemBuilder: (context, index) {
                final line = _busLines[index];
                return ListTile(
                  title: Text('Linha ${line['lt']} - ${line['tp']}'),
                  subtitle: Text('Sentido: ${line['ts']}'),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
