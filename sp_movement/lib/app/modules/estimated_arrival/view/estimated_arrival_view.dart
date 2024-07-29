import 'package:flutter/material.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:latlong2/latlong.dart';
import 'package:sp_movement/app/modules/estimated_arrival/stores/estimated_arrival_store.dart';

import '../models/bus_stops_model.dart';



class EstimatedArrivalView extends StatelessWidget {
  final store = Modular.get<EstimatedArrivalStore>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Previsao de Chegada')),
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
                markers: store.busStopPointsFuture.map((busStopPoint) {
                  return Marker(
                    width: 180.0,
                    height: 180.0,
                    point: LatLng(busStopPoint.latitude, busStopPoint.longitude),
                    child: GestureDetector(
                      onTap: () {
                        store.findEstimatedArrivalByStopPoint(busStopPoint.stopId);
                      },
                      child: const Icon(
                        Icons.location_on,
                        color: Colors.red,
                      ),
                    ),
                  );
                }).toList(),
              ),
            ],
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: store.fetchStopPoints,
        child: const Icon(Icons.refresh),
      ),
    );
  }

  Future<void> _showDialog(context, BusStopsModel busStopPoint) async {
    return showDialog<void>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(busStopPoint.name),
          content: Text('Latitude: ${busStopPoint.latitude} \nLongitude: ${busStopPoint.longitude}'),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text('Fechar'),
            ),
          ],
        );
      },
    );
  }
}
