import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/models/vehicles_model.dart';

class MarkerInfoWidget extends StatelessWidget {
  final VehiclesModel vehicle;
  final BusStopModel busStop;

  const MarkerInfoWidget({
    super.key,
    required this.vehicle,
    required this.busStop,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(
        vertical: 20,
        horizontal: 20,
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            margin: const EdgeInsets.only(bottom: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  'Linha(${vehicle.lineCode})',
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Row(
                  children: [
                    Text(vehicle.labelOrigin),
                    const Icon(Icons.arrow_forward_rounded),
                    Text(vehicle.labelDestination),
                  ],
                ),
              ],
            ),
          ),
          Container(
            margin: const EdgeInsets.only(bottom: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Sentido',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(vehicle.direction == 1
                    ? 'Terminal Principal para Terminal Secundário'
                    : 'Terminal Secundário para Terminal Principal '),
              ],
            ),
          ),
          Container(
            margin: const EdgeInsets.only(bottom: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Parada',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(busStop.name),
              ],
            ),
          ),
          Container(
            margin: const EdgeInsets.only(bottom: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Endereço da parada',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(busStop.address),
              ],
            ),
          ),
          Container(
            margin: const EdgeInsets.only(bottom: 12),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                const Text(
                  'Horário previsto para chegada do ônibus',
                  style: TextStyle(
                    fontWeight: FontWeight.bold,
                  ),
                ),
                Text(vehicle.arrivalTime),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
