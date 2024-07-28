import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:olho_vivo_sp/widgets/hall_list.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';
import 'bottom_navigation_widget.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final _screens = [
    const HallList(),
    const MapWidget(
      searchLabel: 'Pesquisar(Linha, ônibus, parada, previsão de chegada)',
      target: LatLng(-23.5489, -46.6388),
      markers: {},
    ),
  ];
  int _index = 0;

  void changeScreen(int value) {
    setState(() {
      _index = value;
    });
  }

  final _items = [
    const BottomNavigationBarItem(
      icon: Icon(
        Icons.route,
      ),
      label: 'Corredores',
    ),
    const BottomNavigationBarItem(
      icon: Icon(
        Icons.map,
      ),
      label: 'Mapa',
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('OlhoVivoSP'),
      ),
      body: _screens[_index],
      bottomNavigationBar: BottomNavigationWidget(
        items: _items,
        onChangeScreen: changeScreen,
      ),
    );
  }
}
