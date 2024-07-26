import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';

class HallListItem extends StatelessWidget {
  final HallModel hall;

  const HallListItem({super.key, required this.hall});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        onTap: () {},
        leading: const Icon(Icons.route),
        title: Text(hall.name),
      ),
    );
  }
}
