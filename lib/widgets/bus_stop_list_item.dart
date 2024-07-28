import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';

import '../util/routes.dart';

class BusStopListItem extends StatelessWidget {
  final BusStopModel busStop;

  const BusStopListItem({super.key, required this.busStop});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        onTap: () {
          Navigator.of(context).pushNamed(
            Routes.map_detail_screen,
            arguments: busStop,
          );
        },
        leading: const Icon(Icons.bus_alert),
        title: Text('${busStop.code} ${busStop.name}'),
        subtitle: Text(busStop.address),
      ),
    );
  }
}
