// ignore_for_file: library_private_types_in_public_api, use_build_context_synchronously

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:transportepublicosp/services/api_service.dart';
import 'package:transportepublicosp/widgets/custom_bottom_navigation_bar.dart';
import 'package:transportepublicosp/screens/home_screen.dart';
import 'package:transportepublicosp/screens/gtfs_loader.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:transportepublicosp/widgets/filter_icon_widget.dart';
import 'package:transportepublicosp/screens/bus_position_screen.dart';

class StationsScreen extends StatefulWidget {
  const StationsScreen({super.key});

  @override
  _StationsScreenState createState() => _StationsScreenState();
}

class _StationsScreenState extends State<StationsScreen> {
  final TextEditingController _searchController = TextEditingController();
  List<Map<String, dynamic>> _searchResults = [];
  List<Map<String, dynamic>> _nearbyStops = [];
  bool _isLoading = false;
  int _selectedIndex = 1; 
  // ignore: unused_field
  bool _isLocationPermissionGranted = false;
  int _sentido =
      0; // 0 para todos, 1 para Terminal Principal, 2 para Terminal Secundário

  Future<void> _searchLines(String query) async {
    setState(() {
      _isLoading = true;
    });

    final apiService = ApiService();
    try {
      List<Map<String, dynamic>> results;
      if (_sentido == 0) {
        results = await apiService.searchLines(query);
      } else {
        results = await apiService.fetchBusLinesByDirection(query, _sentido);
      }
      if (mounted) {
        setState(() {
          _searchResults = results;
        });
      }
    } catch (e) {
      if (kDebugMode) {
        print('Error fetching search results: $e');
      }
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  Future<void> _fetchNearbyStops() async {
    final gtfsLoader = GTFSLoader();
    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);
    try {
      List<Map<String, dynamic>> stops = await gtfsLoader.loadStopsNearby(
          position.latitude, position.longitude, 150); // 100 metros de raio
      setState(() {
        _nearbyStops = stops;
      });
    } catch (e) {
      if (kDebugMode) {
        print('Error fetching nearby stops: $e');
      }
    }
  }

  Future<void> _checkLocationPermission() async {
    bool serviceEnabled;
    LocationPermission permission;

    serviceEnabled = await Geolocator.isLocationServiceEnabled();
    if (!serviceEnabled) {
      return Future.error('Location services are disabled.');
    }

    permission = await Geolocator.checkPermission();
    if (permission == LocationPermission.denied) {
      permission = await Geolocator.requestPermission();
      if (permission == LocationPermission.denied) {
        return Future.error('Location permissions are denied');
      }
    }

    if (permission == LocationPermission.deniedForever) {
      return Future.error(
          'Location permissions are permanently denied, we cannot request permissions.');
    }

    setState(() {
      _isLocationPermissionGranted = true;
    });

    _fetchNearbyStops();
  }

  @override
  void initState() {
    super.initState();
    _checkLocationPermission();
    _searchController.addListener(() {
      if (_searchController.text.isNotEmpty) {
        _searchLines(_searchController.text);
      } else {
        setState(() {
          _searchResults = [];
        });
      }
    });
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    switch (_selectedIndex) {
      case 0:
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(builder: (context) => const HomeScreen()),
        ).then((_) {
          setState(() {
            _selectedIndex = 0;
          });
        });
        break;
      case 1:
        break;
    }
  }

  void _navigateToBusPositions(Map<String, dynamic> result) async {
    final apiService = ApiService();
    try {
      final busPositions =
          await apiService.fetchBusPositions(result['cl'].toString());
      if (busPositions.isNotEmpty) {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => BusPositionScreen(
              lineCode: result['cl'].toString(),
              busPositions: busPositions,
              busLine: const {},
            ),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content:
                Text('Nenhuma posição de ônibus disponível para esta linha.'),
          ),
        );
      }
    } catch (e) {
      if (kDebugMode) {
        print('Error fetching bus positions: $e');
      }
    }
  }

  void _navigateToStop(Map<String, dynamic> stop) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => HomeScreen(
          initialLocation: LatLng(stop['stop_lat'], stop['stop_lon']),
        ),
      ),
    ).then((_) {
      setState(() {
        _selectedIndex = 0;
      });
    });
  }

  void _toggleSentidoFilter(int value) {
    setState(() {
      _sentido = value;
      if (_searchController.text.isNotEmpty) {
        _searchLines(_searchController.text);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context, _selectedIndex);
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Estações'),
          automaticallyImplyLeading: false,
        ),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              Row(
                children: [
                  Expanded(
                    child: TextField(
                      controller: _searchController,
                      decoration: const InputDecoration(
                        hintText: 'Pesquisar por bairro ou ponto de ônibus...',
                        border: OutlineInputBorder(),
                      ),
                      onSubmitted: (value) {
                        _searchLines(value);
                      },
                    ),
                  ),
                  FilterIconWidget(
                    sentido: _sentido,
                    onFilterChanged: _toggleSentidoFilter,
                  ),
                ],
              ),
              const SizedBox(height: 16.0),
              if (_isLoading)
                const CircularProgressIndicator()
              else
                Expanded(
                  child: ListView(
                    children: [
                      if (_searchResults.isNotEmpty)
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Resultados da pesquisa:',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            ListView.builder(
                              shrinkWrap: true,
                              physics: const NeverScrollableScrollPhysics(),
                              itemCount: _searchResults.length,
                              itemBuilder: (context, index) {
                                final result = _searchResults[index];
                                return ListTile(
                                  leading: const Icon(Icons.directions_bus),
                                  title: Text(result['lt']),
                                  subtitle:
                                      Text('${result['tp']} - ${result['ts']}'),
                                  onTap: () => _navigateToBusPositions(result),
                                );
                              },
                            ),
                          ],
                        ),
                      const SizedBox(height: 16.0),
                      if (_nearbyStops.isNotEmpty)
                        Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const Text(
                              'Pontos por perto:',
                              style: TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            ListView.builder(
                              shrinkWrap: true,
                              physics: const NeverScrollableScrollPhysics(),
                              itemCount: _nearbyStops.length,
                              itemBuilder: (context, index) {
                                final stop = _nearbyStops[index];
                                return ListTile(
                                  leading: const Icon(Icons.location_on),
                                  title: Text(stop['stop_name']),
                                  onTap: () => _navigateToStop(stop),
                                );
                              },
                            ),
                          ],
                        ),
                    ],
                  ),
                ),
            ],
          ),
        ),
        bottomNavigationBar: CustomBottomNavigationBar(
          currentIndex: _selectedIndex,
          onItemTapped: _onItemTapped,
        ),
      ),
    );
  }
}
