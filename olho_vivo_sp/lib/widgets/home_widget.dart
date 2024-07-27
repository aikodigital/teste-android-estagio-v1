import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/widgets/hall_list.dart';
import 'bottom_navigation_widget.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  final _screens = [
    const HallList(),
    const Placeholder(),
    const Placeholder(),
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
      icon: Icon(Icons.search),
      label: 'Linhas',
    ),
    const BottomNavigationBarItem(
      icon: Icon(
        Icons.directions_bus,
      ),
      label: 'Veículos no mapa',
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
