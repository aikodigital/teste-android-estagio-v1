import 'package:flutter/material.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:mova_sp/controllers/detail_map_controller.dart';
import 'package:mova_sp/models/bus_line.dart';

class LineDetail extends StatelessWidget {
  const LineDetail({super.key, required this.busLine});

  final BusLine busLine;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text('Mova SP'),
        actions: [
          IconButton(
            icon: const Icon(Icons.update),
            onPressed: () {},
          ),
        ],
      ),
      body: Obx(
        () => GoogleMap(
          myLocationButtonEnabled: true,
          mapToolbarEnabled: false,
          zoomControlsEnabled: false,
          mapType: MapType.normal,
          initialCameraPosition: CameraPosition(
            target: LatLng(DetailMapController.to.lat.value,
                DetailMapController.to.long.value),
            zoom: 15,
          ),
          onMapCreated: (GoogleMapController controller) {
            DetailMapController.to.onMapCreated(controller, context, busLine.id);
          },
          markers: DetailMapController.to.markers,
        ),
      ),
    );
  }
}
