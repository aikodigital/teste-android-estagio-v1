import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/widgets/marker_info_widget.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';
import 'package:provider/provider.dart';

import '../models/vehicles_model.dart';
import '../util/environment.dart';

class MapDetailScreen extends StatelessWidget {
  const MapDetailScreen({super.key});

  _onTap(BuildContext context, VehiclesModel vehicle, BusStopModel busStop) {
    showModalBottomSheet(
      context: context,
      builder: (context) => MarkerInfoWidget(
        vehicle: vehicle,
        busStop: busStop,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final busStop = ModalRoute.of(context)?.settings.arguments as BusStopModel;

    Provider.of<ApiService>(context).getArrivalForecastByBusStop(
      busStop.code.toString(),
    );

    return Scaffold(
      appBar: AppBar(
        title: const Text('Previsão de chegada dos ônibus'),
      ),
      body: Consumer<ApiService>(
        builder: (ctx, apiService, _) => apiService.vehicles.isEmpty
            ? const Center(
                child: CircularProgressIndicator(),
              )
            : MapWidget(
                markers: apiService.vehicles
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
                          snippet: Environment.snippet_vehicle_marker_msg,
                          onTap: () => _onTap(
                            context,
                            vehicle,
                            busStop,
                          ),
                        ),
                      ),
                    )
                    .toSet(),
              ),
      ),
    );
  }
}
