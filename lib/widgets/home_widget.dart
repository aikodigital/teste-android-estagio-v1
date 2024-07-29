import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/util/environment.dart';
import 'package:olho_vivo_sp/widgets/hall_list.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';
import 'bottom_navigation_widget.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  late List<Widget> _screens;
  late List<BottomNavigationBarItem> _items;

  int _index = 0;

  void _changeScreen(int value) {
    setState(() {
      _index = value;
    });
  }

  @override
  void initState() {
    _screens = [
      const HallList(),
      const MapWidget(
        target: Environment.sao_paulo_downtown_coordinates,
        markers: {},
      ),
    ];

    _items = [
      const BottomNavigationBarItem(
        icon: Icon(
          Icons.route,
        ),
        label: Environment.halls_label,
      ),
      const BottomNavigationBarItem(
        icon: Icon(
          Icons.map,
        ),
        label: Environment.maps_label,
      ),
    ];

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(Environment.app_name),
      ),
      body: _screens[_index],
      bottomNavigationBar: BottomNavigationWidget(
        items: _items,
        onChangeScreen: _changeScreen,
      ),
    );
  }
}
