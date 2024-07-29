import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:olho_vivo_sp/widgets/search_text_field_widget.dart';

class MapWidget extends StatelessWidget {
  final Set<Marker> markers;
  final LatLng? target;
  final bool enableSearch;
  final String searchFieldLabel;
  final Function(String)? onChange;

  const MapWidget({
    super.key,
    required this.markers,
    this.target,
    this.onChange,
    this.enableSearch = false,
    this.searchFieldLabel = '',
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        GoogleMap(
          myLocationEnabled: true,
          initialCameraPosition: CameraPosition(
            target: target ?? markers.first.position,
            zoom: 12,
          ),
          markers: markers,
        ),
        SearchTextFieldWidget(
          enabled: enableSearch,
          label: searchFieldLabel,
          onChange: onChange ?? (value) {},
        ),
      ],
    );
  }
}
