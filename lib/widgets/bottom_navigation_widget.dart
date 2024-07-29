import 'package:flutter/material.dart';

class BottomNavigationWidget extends StatefulWidget {
  final List<BottomNavigationBarItem> items;
  final Function(int) onChangeScreen;

  const BottomNavigationWidget({
    super.key,
    required this.items,
    required this.onChangeScreen,
  });

  @override
  State<BottomNavigationWidget> createState() => _BottomNavigationWidgetState();
}

class _BottomNavigationWidgetState extends State<BottomNavigationWidget> {
  int _currentIndex = 0;

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: _currentIndex,
      items: widget.items,
      onTap: (value) {
        setState(() {
          _currentIndex = value;
        });
        widget.onChangeScreen(value);
      },
    );
  }
}
