import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'app/modules/home/home_store.dart';
import 'app_module.dart';
import 'app_widget.dart';

void main() async {
  runApp(ModularApp(module: AppModule(), child: AppWidget()));
}
