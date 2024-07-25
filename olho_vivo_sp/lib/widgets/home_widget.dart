import 'package:flutter/material.dart';
import 'bottom_navigation_widget.dart';

class Home extends StatelessWidget {
  const Home({super.key});

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      body: Center(
        child: Text('Hello World!'),
      ),
      bottomNavigationBar: BottomNavigationWidget(),
    );
  }
}
