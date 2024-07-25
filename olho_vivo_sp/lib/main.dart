import 'package:flutter/material.dart';

void main() {
  runApp(const OlhoVivoSp());
}

class OlhoVivoSp extends StatelessWidget {
  const OlhoVivoSp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: Scaffold(
        body: Center(
          child: Text('Hello World!'),
        ),
      ),
    );
  }
}
