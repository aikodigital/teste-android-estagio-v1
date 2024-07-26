import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:mobx/mobx.dart';
import '../repositories/bus_route_repository.dart';
import '../stores/bus_route_store.dart';

class BusRoutesView extends StatelessWidget {
  final BusRouteStore store = BusRouteStore(BusRouteRepository());

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Bus Routes'),
      ),
      body: Observer(
        builder: (_) {
          if (store.busRoutesFuture == null) {
            store.fetchBusRoutes();
            return Center(child: CircularProgressIndicator());
          }

          switch (store.busRoutesFuture!.status) {
            case FutureStatus.pending:
              return Center(child: CircularProgressIndicator());
            case FutureStatus.rejected:
              return Center(child: Text('Failed to load data'));
            case FutureStatus.fulfilled:
              final busRoutes = store.busRoutesFuture!.result;
              return ListView.builder(
                itemCount: busRoutes.length,
                itemBuilder: (context, index) {
                  final route = busRoutes[index];
                  return ListTile(
                    title: Text('Route ID: ${route.id}'),
                    subtitle: Text('Route Name: ${route.name}'),
                  );
                },
              );
          }
        },
      ),
    );
  }
}
