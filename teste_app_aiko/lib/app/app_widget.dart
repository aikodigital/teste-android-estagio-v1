import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:teste_app_aiko/app/constants/app_routes.dart';
import 'package:teste_app_aiko/app/repositories/previsao_chegada_repository.dart';
import 'package:teste_app_aiko/app/services/impl/dio_client.dart';

import 'controllers/g_map_controller.dart';
import 'modules/home/home_page.dart';
import 'repositories/bus_markers_repository.dart';
import 'repositories/stops_markers_repository.dart';

class AppWidget extends StatelessWidget {
  const AppWidget({super.key});

  final _baseUrl = 'https://aiko-olhovivo-proxy.aikodigital.io/';

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        Provider(
            create: (_) => Dio(BaseOptions(baseUrl: _baseUrl, queryParameters: {
                  'token':
                      '5fdcf2d7ce111ed9592d7053e4e83d7951558b933e43a264b519536b0e406c59'
                }))),
        Provider(create: (context) => DioSevice(context.read<Dio>())),
        Provider(
            create: (context) =>
                BusMarkersRepository(context.read<DioSevice>())),
        Provider(
            create: (context) =>
                StopsMarkersRepository(context.read<DioSevice>())),
        Provider(
            create: (context) =>
                PrevisaoChegadaRepository(context.read<DioSevice>())),
        ChangeNotifierProvider(
            create: (context) => GMapController(
                  context.read<BusMarkersRepository>(),
                  context.read<StopsMarkersRepository>(),
                  context.read<PrevisaoChegadaRepository>(),
                )),
      ],
      child: MaterialApp(
        title: 'Teste Aiko App',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.blueAccent),
          useMaterial3: true,
        ),
        routes: {
          AppRoutes.home: (context) => const HomePage(title: 'Home'),
        },
      ),
    );
  }
}
