import 'package:flutter/material.dart';

class BottomNavigationWidget extends StatefulWidget {
  const BottomNavigationWidget({
    super.key,
  });

  @override
  State<BottomNavigationWidget> createState() => _BottomNavigationWidgetState();
}

class _BottomNavigationWidgetState extends State<BottomNavigationWidget> {
  int _currentIndex = 0;

  final _items = [
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
    const BottomNavigationBarItem(
      icon: Icon(
        Icons.dangerous,
      ),
      label: 'Paradas',
    ),
    const BottomNavigationBarItem(
      icon: Icon(
        Icons.route,
      ),
      label: 'Corredores',
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: _currentIndex,
      items: _items,
      onTap: (value) {
        setState(() {
          _currentIndex = value;
        });
      },
    );
  }
}
