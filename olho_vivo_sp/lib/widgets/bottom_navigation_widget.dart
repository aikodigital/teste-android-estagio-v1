import 'package:flutter/material.dart';

class BottomNavigationWidget extends StatefulWidget {
  final Function(int) onChangeScreen;

  const BottomNavigationWidget({
    super.key,
    required this.onChangeScreen,
  });

  @override
  State<BottomNavigationWidget> createState() => _BottomNavigationWidgetState();
}

class _BottomNavigationWidgetState extends State<BottomNavigationWidget> {
  int _currentIndex = 0;

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
    return BottomNavigationBar(
      currentIndex: _currentIndex,
      items: _items,
      onTap: (value) {
        setState(() {
          _currentIndex = value;
        });
        widget.onChangeScreen(value);
      },
    );
  }
}
