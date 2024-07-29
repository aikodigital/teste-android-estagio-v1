import 'package:flutter/material.dart';
import 'package:mova_sp/models/bus_stop.dart';
import 'package:mova_sp/models/bus_stop_detail.dart';
import 'package:mova_sp/repository/sptrans_repository.dart';

class BusStopDetailWrap extends StatelessWidget {
  final BusStop busStop;
  final List<BusStopDetail> busStopDetail = [];

  BusStopDetailWrap({super.key, required this.busStop});

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<BusStopDetail>>(
      future: SpTransRepository.to
          .getBusStopDetail(busStop.id),
      builder:
          (BuildContext context, AsyncSnapshot<List<BusStopDetail>> snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const SizedBox.expand(
            child: Center(
              child: CircularProgressIndicator(),
            ),
          );
        } else if (snapshot.hasError) {
          return SizedBox.expand(
            child: Center(
              child: Text('Erro: ${snapshot.error}'),
            ),
          );
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return const SizedBox.expand(
            child: Center(
              child: Text('Nenhuma previsão disponível'),
            ),
          );
        } else {
          List<BusStopDetail> busLineDetails = snapshot.data!;
          return SizedBox.expand(
            child: Wrap(
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 45, left: 24),
                  child: Text(
                    busStop.address,
                    style: const TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.w600,
                    ),
                  ),
                ),
                ...busLineDetails.expand((detail) {
                  return detail.vehicles.map((vehicle) {
                    return Column(
                      children: [
                        ListTile(
                          title: Text('Linha: ${detail.destinationLabel} / ${detail.originLabel}'),
                          subtitle: Text('Chegada: ${vehicle.arrivalTime}'),
                          trailing: vehicle.isAccessible
                              ? const Icon(Icons.accessible)
                              : const Icon(Icons.accessible_forward),
                        ),
                        const Divider(),
                      ],
                    );
                  }).toList();
                })
              ],
            ),
          );
        }
      },
    );
  }
}
