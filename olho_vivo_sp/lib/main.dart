import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/screens/bus_stops_screen.dart';
import 'package:olho_vivo_sp/screens/map_detail_screen.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:provider/provider.dart';

import 'util/routes.dart';
import 'widgets/home_widget.dart';

void main() {
  runApp(const OlhoVivoSp());
}

class OlhoVivoSp extends StatelessWidget {
  const OlhoVivoSp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<ApiService>(
      create: (ctx) {
        final dataProvider = ApiService();
        dataProvider.authenticate();

        return dataProvider;
      },
      child: MaterialApp(
        title: 'OlhoVivoSP',
        theme: ThemeData.from(
          colorScheme: ColorScheme.fromSeed(
            seedColor: Colors.amber,
          ),
        ),
        home: const Home(),
        routes: {
          Routes.bus_stops_screen: (ctx) => const BusStopsScreen(),
          Routes.map_detail_screen: (ctx) => const MapDetailScreen(),
        },
      ),
    );
  }
}
