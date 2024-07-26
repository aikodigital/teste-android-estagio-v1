import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/bus_route/models/vehiclelocation_model.dart';
import '../models/vehicle_model.dart';
import '../repositories/bus_route_repository.dart';

part 'bus_route_store.g.dart';

class BusRouteStore = _BusRouteStore with _$BusRouteStore;

abstract class _BusRouteStore with Store {



  ObservableList<VehicleModel> busRoutesFuture = ObservableList<VehicleModel>();

  @action
  Future<void> fetchVehicles() async{
    List<VehicleLocationModel> list= await BusRouteRepository.getPosition();
    List<VehicleModel> emptyList = [];
    for (var route in list) {
      for (var vehicle in route.listVehicles) {
        emptyList.add(vehicle as VehicleModel);
      }
    }
    busRoutesFuture = emptyList.asObservable();
  }
  // Future fetchBusRoutes() => busRoutesFuture = ObservableFuture(BusRouteRepository.getPosition());
}
