import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/bus_stops_list.dart';
import 'package:olho_vivo_sp/widgets/map_widget.dart';
import 'package:provider/provider.dart';

class BusStopsScreen extends StatefulWidget {
  const BusStopsScreen({super.key});

  @override
  State<BusStopsScreen> createState() => _BusStopsScreenState();
}

class _BusStopsScreenState extends State<BusStopsScreen> {
  bool _isShowingMap = false;

  @override
  Widget build(BuildContext context) {
    final hall = ModalRoute.of(context)?.settings.arguments as HallModel;

    Provider.of<ApiService>(
      context,
      listen: false,
    ).getBusStopsByHall(
      hall.code.toString(),
    );

    return Scaffold(
      appBar: AppBar(
        title: Text('Paradas do corredor ${hall.name}'),
        actions: [
          IconButton(
            onPressed: () {
              setState(
                () {
                  _isShowingMap = !_isShowingMap;
                },
              );
            },
            icon: Icon(!_isShowingMap ? Icons.map : Icons.list),
          ),
        ],
      ),
      body: Consumer<ApiService>(
        builder: (context, apiService, child) => !_isShowingMap
            ? BusStopsList(busStops: apiService.busStops)
            : MapWidget(
                markers: apiService.busStops
                    .map(
                      (busStop) => Marker(
                        markerId: MarkerId(
                          busStop.code.toString(),
                        ),
                        position: LatLng(
                          busStop.yPos,
                          busStop.xPos,
                        ),
                        infoWindow: InfoWindow(
                          title: '${busStop.code} ${busStop.name}',
                          snippet: busStop.address,
                        ),
                      ),
                    )
                    .toSet(),
              ),
      ),
    );
  }
}
