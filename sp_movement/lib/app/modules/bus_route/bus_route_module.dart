import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_route/widgets/bottomnavigationbar_component.dart';

class BusRouteModule extends Module {
  @override
  final List<Bind> binds = [
    // Adicione os binds necessários aqui
  ];

  @override
  final List<ModularRoute> routes = [
    ChildRoute(Modular.initialRoute, child: (_, __) => BottomNavigationBarComponent()),
  ];
}
