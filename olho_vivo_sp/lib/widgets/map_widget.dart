import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MapWidget extends StatelessWidget {
  final Set<Marker> markers;

  const MapWidget({super.key, required this.markers});

  @override
  Widget build(BuildContext context) {
    return GoogleMap(
      initialCameraPosition: const CameraPosition(
        target: LatLng(-23.5505, -46.6333),
        zoom: 12,
      ),
      markers: markers,
    );
  }
}
