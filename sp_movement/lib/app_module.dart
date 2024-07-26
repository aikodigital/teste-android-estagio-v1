import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_route/bus_route_module.dart';


class AppModule extends Module {
  @override
  final List<Bind> binds = [];

  @override
  final List<ModularRoute> routes = [
    ModuleRoute(Modular.initialRoute, module: BusRouteModule()),
  ];
}

