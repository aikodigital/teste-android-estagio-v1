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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _screens[_index],
      bottomNavigationBar: BottomNavigationWidget(
        onChangeScreen: changeScreen,
      ),
    );
  }
}
