import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/auth/auth_module.dart';
import 'package:sp_movement/app/modules/auth/auth_store.dart';
import 'package:sp_movement/app/modules/vehicle_position/vehicle_position_module.dart';

class AppModule extends Module {
  @override
  void binds(i) {
    i.addSingleton(AuthStore.new);
  }

  @override
  void routes(r) {
    r.module('/', module: VehiclePositionModule());
    r.module('/auth', module: AuthModule());
  }
}
