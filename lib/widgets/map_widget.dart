import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class MapWidget extends StatelessWidget {
  final LatLng? target;
  final Set<Marker> markers;
  final bool enableSearch;
  final String searchLabel;

  const MapWidget({
    super.key,
    required this.markers,
    this.target,
    this.enableSearch = true,
    this.searchLabel = 'Pesquisar',
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        GoogleMap(
          initialCameraPosition: CameraPosition(
            target: target ?? markers.first.position,
            zoom: 12,
          ),
          markers: markers,
        ),
        Visibility(
          visible: enableSearch,
          child: Container(
            width: double.infinity,
            height: MediaQuery.of(context).size.height * 0.10,
            color: Theme.of(context).cardColor,
            child: Padding(
              padding: const EdgeInsets.symmetric(
                vertical: 8,
                horizontal: 20,
              ),
              child: TextField(
                onSubmitted: (value) {},
                decoration: InputDecoration(
                  icon: const Icon(Icons.search),
                  label: Text(
                    searchLabel,
                  ),
                ),
              ),
            ),
          ),
        ),
      ],
    );
  }
}
