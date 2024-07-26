import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_route/%20view/bus_routes_view.dart';
import 'package:sp_movement/app/modules/bus_route/repositories/bus_route_repository.dart';
import 'package:sp_movement/app/modules/bus_route/stores/bus_route_store.dart';

class BusRouteModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => BusRouteStore());
  }

  @override
  void routes(r) {
    r.child(
      '/',
      child: (context) => BusRoutesView(),
    );
  }
}
