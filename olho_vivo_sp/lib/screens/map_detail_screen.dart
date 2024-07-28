import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';
import 'package:provider/provider.dart';

import '../models/vehicles_model.dart';

class MapDetailScreen extends StatelessWidget {
  const MapDetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    final busStop = ModalRoute.of(context)?.settings.arguments as BusStopModel;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Previsão de chegada dos ônibus'),
      ),
      body: Consumer<ApiService>(
        builder: (ctx, apiService, _) => FutureBuilder(
          future: apiService.getArrivalForecastByBusStop(
            busStop.code.toString(),
          ),
          builder: (ctx, snp) {
            if (snp.hasData && snp.connectionState == ConnectionState.done) {
              final vehicles = snp.data as List<VehiclesModel>;

              return MapWidget(
                markers: vehicles
                    .map(
                      (vehicle) => Marker(
                        markerId: MarkerId(
                          vehicle.prefix,
                        ),
                        position: LatLng(
                          vehicle.yPos,
                          vehicle.xPos,
                        ),
                        infoWindow: InfoWindow(
                          title:
                              '${vehicle.lineCode} ${vehicle.labelOrigin} - ${vehicle.labelDestination} ${vehicle.label}',
                          snippet: 'Clique para obter mais informações',
                          onTap: () {
                            showModalBottomSheet(
                              context: context,
                              builder: (context) {
                                return Container(
                                  padding: const EdgeInsets.symmetric(
                                    vertical: 20,
                                    horizontal: 20,
                                  ),
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Container(
                                        margin:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Column(
                                          crossAxisAlignment:
                                              CrossAxisAlignment.start,
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
                                                const Icon(Icons
                                                    .arrow_forward_rounded),
                                                Text(vehicle.labelDestination),
                                              ],
                                            ),
                                          ],
                                        ),
                                      ),
                                      Container(
                                        margin:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Column(
                                          crossAxisAlignment:
                                              CrossAxisAlignment.start,
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
                                        margin:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Column(
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
                                        margin:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Column(
                                          children: [
                                            const Text(
                                              'Parada',
                                              style: TextStyle(
                                                fontWeight: FontWeight.bold,
                                              ),
                                            ),
                                            Text(busStop.address),
                                          ],
                                        ),
                                      ),
                                      Container(
                                        margin:
                                            const EdgeInsets.only(bottom: 12),
                                        child: Column(
                                          children: [
                                            const Text(
                                              'Parada',
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
                              },
                            );
                          },
                        ),
                      ),
                    )
                    .toSet(),
              );
            }

            return const Center(
              child: CircularProgressIndicator(),
            );
          },
        ),
      ),
    );
  }
}
