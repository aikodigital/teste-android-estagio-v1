import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/estimated_arrival/models/bus_stops_model.dart';
import 'package:sp_movement/app/modules/estimated_arrival/repository/bus_stops_repository.dart';

import '../models/stop_model.dart';
import '../repository/estimated_arrival_repository.dart';

part "estimated_arrival_store.g.dart";

class EstimatedArrivalStore = _EstimatedArrivalStore
    with _$EstimatedArrivalStore;

abstract class _EstimatedArrivalStore with Store {
  _EstimatedArrivalStore() {
    fetchStopPoints();
  }

  @observable
  StopModel? stopModel;

  @action
  void setStopModel(StopModel value) => stopModel = value;

  ObservableList<BusStopsModel> busStopPointsFuture =
      ObservableList<BusStopsModel>();

  @observable
  List<String> busStopPoints = [];

  @action
  Future<void> fetchStopPoints() async {
    List<BusStopsModel> stopModels = await BusStopRepository.searchListStop();
    busStopPointsFuture = stopModels.asObservable();
  }

  @action
  Future<void> findEstimatedArrivalByStopPoint(int stopId) async {
    stopModel = await EstimatedArrivalRepository.searchEstimatedArrival(stopId);
    if(stopModel != null)
      stopModel!.localizedLines.forEach((lines) {
        lines.vehicles.forEach((vehicle) {
          busStopPoints.add("${vehicle.vehiclePrefix} - ${vehicle.scheduledArrivalTime} - ${vehicle.accessible?"Acessivel Pcd":"Não Acessivel Pcd"}");
        });
      });
  }
}
