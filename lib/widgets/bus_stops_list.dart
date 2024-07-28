import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/widgets/bus_stop_list_item.dart';

class BusStopsList extends StatelessWidget {
  final List<BusStopModel> busStops;

  const BusStopsList({super.key, required this.busStops});

  @override
  Widget build(BuildContext context) {
    var busStopData = [...busStops];
    return ListView.builder(
      padding: const EdgeInsets.only(
        left: 20,
        right: 20,
        top: 8,
      ),
      itemBuilder: (ctx, i) {
        return BusStopListItem(busStop: busStopData[i]);
      },
      itemCount: busStopData.length,
    );
  }
}
