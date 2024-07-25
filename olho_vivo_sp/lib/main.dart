import 'package:flutter/material.dart';

import 'widgets/home_widget.dart';

void main() {
  runApp(const OlhoVivoSp());
}

class OlhoVivoSp extends StatelessWidget {
  const OlhoVivoSp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'OlhoVivoSP',
      theme: ThemeData.from(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.amber,
        ),
      ),
      home: const Home(),
    );
  }
}
