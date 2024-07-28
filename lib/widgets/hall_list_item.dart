import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/util/routes.dart';

class HallListItem extends StatelessWidget {
  final HallModel hall;

  const HallListItem({super.key, required this.hall});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        onTap: () => Navigator.of(context).pushNamed(
          Routes.bus_stops_screen,
          arguments: hall,
        ),
        leading: const Icon(Icons.route),
        title: Text('${hall.code} ${hall.name}'),
      ),
    );
  }
}
