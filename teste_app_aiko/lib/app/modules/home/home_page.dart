import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';
import 'package:sliding_up_panel/sliding_up_panel.dart';

import '../../controllers/g_map_controller.dart';
import '../../models/stops_markers_model.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.title});
  final String title;

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late final GMapController _gMapController;
  final panelController = PanelController();

  @override
  void initState() {
    _gMapController = context.read<GMapController>();
    _gMapController.fetchMarkers();
    super.initState();
  }

  Widget buildMainWidget(GMapController gMap) {
    final selectedMarker = _gMapController.selectedStopMarker;
    return SlidingUpPanel(
      controller: panelController,
      parallaxEnabled: true,
      parallaxOffset: .2,
      borderRadius: const BorderRadius.vertical(top: Radius.circular(16)),
      body: Padding(
        padding: const EdgeInsets.only(bottom: 100),
        child: GoogleMap(
          mapToolbarEnabled: false,
          myLocationEnabled: true,
          zoomControlsEnabled: false,
          mapType: MapType.normal,
          minMaxZoomPreference: const MinMaxZoomPreference(8, 16),
          onMapCreated: gMap.onMapCreated,
          initialCameraPosition: gMap.initialPosition,
          markers: _gMapController.setMarkers,
        ),
      ),
      panelBuilder: (sc) {
        return Column(
          children: [
            selectedMarker == null
                ? const HeaderWidget(title: 'Paradas')
                : HeaderWidget(
                    title: 'Parada',
                    onTap: () {
                      _gMapController.onCloseSelectedStop();
                      panelController.open();
                    }),
            if (selectedMarker == null)
              StopsWidget(
                  onTap: _gMapController.navToStop,
                  sc: sc,
                  stopsMarkers: _gMapController.stopsMarkersRepository.markers)
            else
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    ListTile(
                      title: Text(selectedMarker.nome),
                      subtitle: Text(selectedMarker.endereco),
                    ),
                    const Divider(),
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text('Linhas',
                          style: Theme.of(context).textTheme.titleLarge),
                    ),
                    Expanded(
                      child: ListView.builder(
                        controller: sc,
                        itemCount: selectedMarker.lines!.length,
                        itemBuilder: (context, index) {
                          final line = selectedMarker.lines![index];
                          return ListTile(
                            title: Text(line.cod.toString()),
                            subtitle: Text(
                                '${line.letreiroOrigem} / ${line.letreiroDestino}'),
                          );
                        },
                      ),
                    )
                  ],
                ),
              ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Consumer<GMapController>(builder: (_, gMap, __) {
        switch (gMap.mapLocationState) {
          case MapLocationState.error:
            return Center(
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 20),
                child: Text(gMap.error, textAlign: TextAlign.center),
              ),
            );
          case MapLocationState.loading:
            return const Center(child: CircularProgressIndicator());
          case MapLocationState.success:
          default:
            return buildMainWidget(gMap);
        }
      }),
    );
  }
}

class StopsWidget extends StatelessWidget {
  final ScrollController sc;
  final List<StopsMarkerModel> stopsMarkers;
  final void Function(StopsMarkerModel) onTap;

  const StopsWidget({
    super.key,
    required this.sc,
    required this.stopsMarkers,
    required this.onTap,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ListView.builder(
        controller: sc,
        itemCount: stopsMarkers.length,
        itemBuilder: (context, index) {
          final stop = stopsMarkers[index];
          return ListTile(
            title: Text(stop.nome),
            subtitle: Text(stop.endereco),
            onTap: () => onTap(stop),
          );
        },
      ),
    );
  }
}

class HeaderWidget extends StatelessWidget {
  final String title;
  final VoidCallback? onTap;
  const HeaderWidget({
    super.key,
    this.onTap,
    required this.title,
  });

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Center(
          heightFactor: 2.5,
          child: Text(title, style: Theme.of(context).textTheme.titleLarge),
        ),
        if (onTap != null)
          Align(
              alignment: Alignment.topRight,
              child:
                  IconButton(onPressed: onTap, icon: const Icon(Icons.close)))
      ],
    );
  }
}
