import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';

class MapDetailScreen extends StatelessWidget {
  const MapDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Mapa'),
      ),
      body: const MapWidget(markers: {}),
    );
  }
}
