import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/vehicle_position/%20view/vehicle_positions_view.dart';
import 'package:sp_movement/app/modules/vehicle_position/repositories/vehicle_position_repository.dart';
import 'package:sp_movement/app/modules/vehicle_position/stores/vehicle_position_store.dart';

class VehiclePositionModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => VehiclePositionStore());
  }

  @override
  void routes(r) {
    r.child(
      '/',
      child: (context) => VehiclePositionsView(),
    );
  }
}
