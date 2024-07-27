import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
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
            final busStops = snp.data as List;

            return ListView.builder(
              padding: const EdgeInsets.only(
                left: 20,
                right: 20,
                top: 8,
              ),
              itemBuilder: (ctx, i) {
                return Card(
                  child: ListTile(
                    leading: const Icon(Icons.bus_alert),
                    onTap: () {},
                    title: Text(busStops[i]['np']),
                    subtitle: Text(busStops[i]['ed']),
                  ),
                );
              },
              itemCount: busStops.length,
            );
          }

          if (snp.hasError && snp.connectionState == ConnectionState.done) {
            return const Text('Deu ruim');
          }

          return const Center(
            child: CircularProgressIndicator(),
          );
        },
      ),
    );
  }
}
