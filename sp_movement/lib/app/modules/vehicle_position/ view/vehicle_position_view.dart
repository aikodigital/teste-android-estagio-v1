import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:latlong2/latlong.dart';
import 'package:mobx/mobx.dart';
import '../stores/vehicle_position_store.dart';

class VehiclePositionsView extends StatelessWidget {
  final store = Modular.get<VehiclePositionStore>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Posição dos Veículos')),
      body: Observer(
        builder: (_) {
          return FlutterMap(
            options: const MapOptions(
              initialCenter: LatLng(-23.5505, -46.6333),
              initialZoom: 12,
            ),
            children: [
              TileLayer(
                urlTemplate: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
                subdomains: const ['a', 'b', 'c'],
              ),
              MarkerLayer(
                markers: store.VehiclePositionsFuture.map((vehicle) {
                  return Marker(
                    width: 80.0,
                    height: 80.0,
                    point: LatLng(vehicle.latitude, vehicle.longitude),
                    child: const Icon(
                      Icons.location_on,
                      color: Colors.red,
                    ),
                  );
                }).toList(),
              ),
            ],
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: store.fetchVehicles,
        child: const Icon(Icons.refresh),
      ),
    );
  }
}
