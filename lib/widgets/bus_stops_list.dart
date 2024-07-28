import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/bus_stop_model.dart';
import 'package:olho_vivo_sp/widgets/bus_stop_list_item.dart';

class BusStopsList extends StatelessWidget {
  final List<BusStopModel> busStops;

  const BusStopsList({super.key, required this.busStops});

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (ctx, cts) => Column(
        children: [
          SizedBox(
            height: cts.maxHeight * 0.1,
            child: const Padding(
              padding: EdgeInsets.symmetric(
                vertical: 8,
                horizontal: 20,
              ),
              child: TextField(
                decoration: InputDecoration(
                  icon: Icon(Icons.search),
                  hintText: 'Pesquisar parada',
                ),
              ),
            ),
          ),
          SizedBox(
            height: cts.maxHeight * 0.9,
            child: ListView.builder(
              padding: const EdgeInsets.only(
                left: 20,
                right: 20,
                top: 8,
              ),
              itemBuilder: (ctx, i) {
                return BusStopListItem(busStop: busStops[i]);
              },
              itemCount: busStops.length,
            ),
          ),
        ],
      ),
    );
  }
}
