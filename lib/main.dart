import 'package:flutter/material.dart';
import 'package:teste_android_estagio_v1/views/home/home_view.dart';

Future<void> main() async {
  runApp(const MaterialApp(
    debugShowCheckedModeBanner: false,
    title: "MEU BUSÃO",
    home: HomeView(),
  ));
}
