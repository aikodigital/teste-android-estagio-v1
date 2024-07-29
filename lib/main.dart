import 'package:flutter/material.dart';
import 'package:flutter_config/flutter_config.dart';
import 'package:get/get.dart';
import 'package:mova_sp/auth/authentication.dart';
import 'package:mova_sp/controllers/detail_map_controller.dart';
import 'package:mova_sp/controllers/map_controller.dart';
import 'package:mova_sp/pages/app_home.dart';
import 'package:mova_sp/repository/sptrans_repository.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await FlutterConfig.loadEnvVariables();
  Get.lazyPut<MapController>(() => MapController());
  Get.lazyPut<DetailMapController>(() => DetailMapController());
  Get.lazyPut<SpTransRepository>(() => SpTransRepository());
  Get.lazyPut<Authentication>(() => Authentication());
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    Authentication().authenticate();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Mova SP',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const AppHome(),
    );
  }
}
