import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:teste_android_estagio_v1/screens/home_screen.dart';
import 'package:teste_android_estagio_v1/services/olho_vivo_service.dart';


void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (_) => OlhoVivoService(),
      child: MaterialApp(
        title: 'Transporte público',
        debugShowCheckedModeBanner: false,
        theme: ThemeData(
          primarySwatch: Colors.blue,
          primaryColor: const Color.fromARGB(255, 4, 125, 141),
        ),
        home: HomeScreen(),
      ),
    );
  }
}
