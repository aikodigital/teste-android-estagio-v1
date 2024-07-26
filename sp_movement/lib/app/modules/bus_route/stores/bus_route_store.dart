import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/bus_route/models/vehiclelocation_model.dart';
import '../repositories/bus_route_repository.dart';

part 'bus_route_store.g.dart';

class BusRouteStore = _BusRouteStore with _$BusRouteStore;

abstract class _BusRouteStore with Store {
  final BusRouteRepository repository;

  _BusRouteStore(this.repository);

  @observable
  ObservableFuture<List<VehicleLocationModel>>? busRoutesFuture;

  @action
  Future fetchBusRoutes() => busRoutesFuture = ObservableFuture(BusRouteRepository.getPosition());
}
