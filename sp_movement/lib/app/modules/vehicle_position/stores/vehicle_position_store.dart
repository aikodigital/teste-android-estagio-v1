import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/vehicle_position/models/vehiclelocation_model.dart';
import '../models/vehicle_model.dart';
import '../repositories/vehicle_position_repository.dart';

part 'vehicle_position_store.g.dart';

class VehiclePositionStore = _VehiclePositionStore with _$VehiclePositionStore;

abstract class _VehiclePositionStore with Store {

  @observable
  bool loading = false;
  @action
  void setLoading(bool value) => loading = value;

  ObservableList<VehicleModel> vehiclePositionsFuture = ObservableList<VehicleModel>();

  @action
  Future<void> fetchVehicles() async{
    setLoading(true);
    List<VehicleLocationModel> list= await VehiclePositionRepository.getPosition();
    List<VehicleModel> emptyList = [];
    for (var route in list) {
      for (var vehicle in route.listVehicles) {
        emptyList.add(vehicle as VehicleModel);
      }
    }
    vehiclePositionsFuture = emptyList.asObservable();
    setLoading(false);
  }
  // Future fetchVehiclePositions() => VehiclePositionsFuture = ObservableFuture(VehiclePositionRepository.getPosition());
}
