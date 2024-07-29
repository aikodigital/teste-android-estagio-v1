import 'package:flutter/material.dart';
import 'package:android_joana/pages/map_screen.dart';
import 'package:android_joana/pages/bus_stops_page.dart';
import 'package:android_joana/pages/bus_lines_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Mapa de Ônibus',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MapScreen(), 
      routes: {
        '/bus-stops': (context) => const BusStopsPage(), 
        '/bus-lines': (context) => const BusLinesPage(), 
      },
    );
  }
}
