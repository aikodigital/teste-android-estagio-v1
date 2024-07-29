import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_lines/stores/bus_lines_store.dart';
import 'package:sp_movement/app/modules/home/view/home_page.dart';

import '../vehicle_position/stores/vehicle_position_store.dart';
import '../vehicle_position/vehicle_position_module.dart';

class HomeModule extends Module {
  @override
  void binds(i){
    i.addLazySingleton(VehiclePositionStore.new);
    i.addLazySingleton(BusLinesStore.new);
  }
  @override
  void routes(r){
    r.child(Modular.initialRoute, child: (_)=> const HomePage(), children: [
        // ModuleRoute('/bus-lines', module: VehiclePositionModule()),
        // ModuleRoute('/estimated-arrival', module: VehiclePositionModule()),
        // ModuleRoute('/vehicle-position', module: VehiclePositionModule())
    ]);

  }
}