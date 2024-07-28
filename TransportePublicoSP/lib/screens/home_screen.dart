// ignore_for_file: library_private_types_in_public_api, use_build_context_synchronously

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:geolocator/geolocator.dart';
import 'package:transportepublicosp/screens/gtfs_loader.dart';
import 'package:transportepublicosp/widgets/custom_bottom_navigation_bar.dart';
import 'package:transportepublicosp/widgets/bus_lines_widget.dart';
import 'package:transportepublicosp/services/api_service.dart';
import 'package:transportepublicosp/screens/search_screen.dart';
import 'package:transportepublicosp/screens/stations_screen.dart';
import 'dart:async';

import '../widgets/nearby_bus_lines_widget.dart';

class HomeScreen extends StatefulWidget {
  final LatLng? initialLocation;

  const HomeScreen({super.key, this.initialLocation});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  GoogleMapController? mapController;
  int _selectedIndex = 0;
  final Set<Marker> _markers = {};
  BitmapDescriptor? busIcon;
  bool _isLocationPermissionGranted = false;
  double _currentZoom = 15.0;
  Timer? _debounce;
  Map<String, List<Map<String, dynamic>>> stopsCache = {};
  Position? _currentPosition;

  @override
  void initState() {
    super.initState();
    _loadCustomMarker();
    _checkLocationPermission();
  }

  Future<void> _loadCustomMarker() async {
    busIcon = await BitmapDescriptor.asset(
      const ImageConfiguration(),
      'assets/images/bus_icon.png',
      height: 25,
      width: 25,
    );
  }

  Future<void> _loadStopsInVisibleRegion() async {
    if (mapController == null) return;

    if (_currentZoom < 14) {
      setState(() {
        _markers.clear();
      });
      return;
    }

    final bounds = await mapController!.getVisibleRegion();
    final southwest = bounds.southwest;
    final northeast = bounds.northeast;

    final regionKey =
        '${southwest.latitude},${southwest.longitude},${northeast.latitude},${northeast.longitude}';

    if (stopsCache.containsKey(regionKey)) {
      _updateMarkers(stopsCache[regionKey]!);
      return;
    }

    GTFSLoader gtfsLoader = GTFSLoader();
    List<Map<String, dynamic>> stops = await gtfsLoader.loadStopsInRegion(
      southwest.latitude,
      northeast.latitude,
      southwest.longitude,
      northeast.longitude,
    );

    stopsCache[regionKey] = stops;
    _updateMarkers(stops);
  }

  void _updateMarkers(List<Map<String, dynamic>> stops) {
    setState(() {
      final newMarkers = stops.map((stop) {
        return Marker(
          markerId: MarkerId(stop['stop_id']),
          position: LatLng(stop['stop_lat'], stop['stop_lon']),
          icon: busIcon ?? BitmapDescriptor.defaultMarker,
          infoWindow: InfoWindow(
            title: stop['stop_name'],
            snippet: stop['stop_desc'] ?? '',
            onTap: () => _showBusLines(stop['stop_id'], stop['stop_name']),
          ),
        );
      }).toSet();
      _markers
        ..clear()
        ..addAll(newMarkers);
    });
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

    if (widget.initialLocation == null) {
      _centerMapOnUserLocation();
    }
  }

  Future<void> _centerMapOnUserLocation() async {
    Position position = await Geolocator.getCurrentPosition(
        desiredAccuracy: LocationAccuracy.high);
    setState(() {
      _currentPosition = position;
    });
    mapController!.animateCamera(
      CameraUpdate.newCameraPosition(
        CameraPosition(
          target: LatLng(position.latitude, position.longitude),
          zoom: 18,
        ),
      ),
    );
  }

  Future<void> _centerMapOnLocation(LatLng location) async {
    if (mapController != null) {
      mapController!.animateCamera(
        CameraUpdate.newCameraPosition(
          CameraPosition(
            target: location,
            zoom: 18,
          ),
        ),
      );
    }
  }

  void _onMapCreated(GoogleMapController controller) {
    mapController = controller;
    if (widget.initialLocation != null) {
      _centerMapOnLocation(widget.initialLocation!);
    } else {
      _loadStopsInVisibleRegion();
    }
  }

  void _onCameraIdle() {
    if (_debounce?.isActive ?? false) _debounce!.cancel();
    _debounce = Timer(const Duration(milliseconds: 500), () {
      _loadStopsInVisibleRegion();
    });
  }

  Future<void> _showBusLines(String stopId, String stopName) async {
    final apiService = ApiService();
    List<Map<String, dynamic>> busLines =
        await apiService.fetchBusLinesStops(stopId);

    showModalBottomSheet(
      context: context,
      builder: (context) =>
          BusLinesWidget(stopName: stopName, busLines: busLines),
    );
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    switch (_selectedIndex) {
      case 0:
        // Tela atual
        break;
      case 1:
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => const StationsScreen()),
        );
        break;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Transporte Público SP'),
        automaticallyImplyLeading: false,
        actions: [
          IconButton(
            icon: const Icon(Icons.search),
            onPressed: () async {
              final result = await Navigator.push(
                context,
                MaterialPageRoute(builder: (context) => const SearchScreen()),
              );

              if (result != null) {
                mapController!.animateCamera(
                  CameraUpdate.newCameraPosition(
                    CameraPosition(
                      target: LatLng(result['stop_lat'], result['stop_lon']),
                      zoom: 18,
                    ),
                  ),
                );
              }
            },
          ),
        ],
      ),
      body: Stack(
        children: [
          GoogleMap(
            onMapCreated: _onMapCreated,
            onCameraIdle: _onCameraIdle,
            onCameraMove: (position) {
              _currentZoom = position.zoom;
            },
            initialCameraPosition: CameraPosition(
              target: widget.initialLocation ??
                  const LatLng(-23.555211, -46.635581),
              zoom: 15.0, // Nível de zoom inicial mais próximo
            ),
            markers: _markers,
            myLocationEnabled: _isLocationPermissionGranted,
            myLocationButtonEnabled: false,
          ),
          if (!_isLocationPermissionGranted)
            Positioned(
              bottom: 80, 
              left: 16,
              right: 16,
              child: Container(
                padding:
                    const EdgeInsets.symmetric(horizontal: 16.0, vertical: 8.0),
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(8.0),
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black26,
                      blurRadius: 4.0,
                      offset: Offset(0, -2),
                    ),
                  ],
                ),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Expanded(
                      child: Text(
                        'Para melhor experiência habilite o GPS',
                        style: TextStyle(fontSize: 14.0),
                      ),
                    ),
                    TextButton(
                      onPressed: _checkLocationPermission,
                      child: const Text('Permitir'),
                    ),
                  ],
                ),
              ),
            ),
          Positioned(
            bottom: 80,
            left: 16,
            child: FloatingActionButton(
              onPressed: _isLocationPermissionGranted
                  ? _centerMapOnUserLocation
                  : null,
              child: const Icon(Icons.my_location),
            ),
          ),
          if (_currentPosition != null)
            Positioned(
              top: 16,
              left: 16,
              right: 16,
              child: NearbyBusLinesWidget(
                userLat: _currentPosition!.latitude,
                userLon: _currentPosition!.longitude,
              ),
            ),
        ],
      ),
      bottomNavigationBar: CustomBottomNavigationBar(
        currentIndex: _selectedIndex,
        onItemTapped: _onItemTapped,
      ),
    );
  }
}
