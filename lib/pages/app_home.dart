import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:mova_sp/controllers/map_controller.dart';
import 'package:mova_sp/pages/search_page.dart';

class AppHome extends StatelessWidget {
  const AppHome({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: const Text('Mova SP'),
        actions: [
          IconButton(
            icon: const Icon(Icons.search_outlined),
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => const SearchPage(),
                ),
              );
            },
          ),
        ],
      ),
      body: Obx(
        () => GoogleMap(
          myLocationButtonEnabled: true,
          mapType: MapType.normal,
          initialCameraPosition: CameraPosition(
            target: LatLng(MapController.to.lat.value,
                MapController.to.long.value),
            zoom: 15,
          ),
          onMapCreated: (GoogleMapController controller) {
            MapController.to.onMapCreated(controller, context);
          },
          markers: MapController.to.markers,
        ),
      ),
    );
  }
}
