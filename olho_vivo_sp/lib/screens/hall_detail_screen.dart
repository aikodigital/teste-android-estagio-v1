import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';

import '../util/routes.dart';

class HallDetailScreen extends StatelessWidget {
  const HallDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final hall = ModalRoute.of(context)?.settings.arguments as HallModel;

    final options = [
      {
        'icon': Icons.bus_alert,
        'title': 'Paradas',
        'subtitle': 'Exibe todas as paradas do corredor ${hall.name}',
        'fn': () => Navigator.of(context).pushNamed(
              Routes.bus_stops_screen,
              arguments: hall,
            ),
      },
      {
        'icon': Icons.watch_later_outlined,
        'title': 'Previsão de chegada',
        'subtitle':
            'Exibe a previsão de chegada nas paradas do corredor ${hall.name}',
        'fn': () {},
      },
      {
        'icon': Icons.speed,
        'title': 'Velocidade nas vias',
        'subtitle': 'Exibe velocidade nas vias',
        'fn': () {},
      }
    ];

    return Scaffold(
      appBar: AppBar(
        title: Text('Corredor ${hall.name}'),
      ),
      body: ListView(
        padding: const EdgeInsets.only(
          left: 20,
          right: 20,
          top: 8,
        ),
        children: options
            .map(
              (option) => Card(
                child: ListTile(
                  onTap: option['fn'] as Function(),
                  leading: Icon(
                    option['icon'] as IconData,
                  ),
                  title: Text(
                    option['title'] as String,
                  ),
                  subtitle: Text(
                    option['subtitle'] as String,
                  ),
                ),
              ),
            )
            .toList(),
      ),
    );
  }
}
