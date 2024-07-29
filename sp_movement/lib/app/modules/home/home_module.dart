import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_lines/stores/bus_lines_store.dart';
import 'package:sp_movement/app/modules/home/home_store.dart';
import 'package:sp_movement/app/modules/home/view/home_view.dart';

import '../estimated_arrival/stores/estimated_arrival_store.dart';
import '../vehicle_position/stores/vehicle_position_store.dart';
import '../vehicle_position/vehicle_position_module.dart';

class HomeModule extends Module {
  @override
  void binds(i){
    i.addSingleton(HomeStore.new);
    i.addLazySingleton(VehiclePositionStore.new);
    i.addLazySingleton(BusLinesStore.new);
    i.addLazySingleton(EstimatedArrivalStore.new);
  }
  @override
  void routes(r){
    r.child(Modular.initialRoute, child: (_)=> const HomeView());
  }
}