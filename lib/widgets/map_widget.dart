import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MapWidget extends StatelessWidget {
  final LatLng? target;
  final Set<Marker> markers;

  const MapWidget({super.key, required this.markers, this.target});

  @override
  Widget build(BuildContext context) {
    return GoogleMap(
      initialCameraPosition: CameraPosition(
        target: target ?? markers.first.position,
        zoom: 12,
      ),
      markers: markers,
    );
  }
}
