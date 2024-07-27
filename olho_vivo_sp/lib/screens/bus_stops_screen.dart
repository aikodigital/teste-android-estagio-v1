import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/bus_stops_list.dart';
import 'package:provider/provider.dart';

class BusStopsScreen extends StatelessWidget {
  const BusStopsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final apiService = Provider.of<ApiService>(context);
    final hall = ModalRoute.of(context)?.settings.arguments as HallModel;

    return Scaffold(
      appBar: AppBar(
        title: Text('Paradas do corredor ${hall.name}'),
        actions: [
          IconButton(
            onPressed: () {},
            icon: const Icon(Icons.map),
          ),
        ],
      ),
      body: FutureBuilder(
        future: apiService.getBusStopsByHall(
          hall.code.toString(),
        ),
        builder: (ctx, snp) {
          if (snp.hasData && snp.connectionState == ConnectionState.done) {
            final busStops = snp.data as List<BusStopModel>;

            return BusStopsList(busStops: busStops);
          }

          if (snp.hasError && snp.connectionState == ConnectionState.done) {
            return Center(child: Text(snp.error.toString()));
          }

          return const Center(
            child: CircularProgressIndicator(),
          );
        },
      ),
    );
  }
}
