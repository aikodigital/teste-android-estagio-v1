import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_route/%20view/bus_routes_view.dart';
import 'package:sp_movement/app/modules/bus_route/repositories/bus_route_repository.dart';
import 'package:sp_movement/app/modules/bus_route/stores/bus_route_store.dart';

import 'auth_store.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => AuthStore());
  }

  @override
  void routes(r) {
  }
}
