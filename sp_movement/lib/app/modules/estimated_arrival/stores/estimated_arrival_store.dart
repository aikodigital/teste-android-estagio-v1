import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/estimated_arrival/models/bus_stops_model.dart';
import 'package:sp_movement/app/modules/estimated_arrival/repository/bus_stops_repository.dart';

import '../repository/estimated_arrival_repository.dart';

part "estimated_arrival_store.g.dart";

class EstimatedArrivalStore = _EstimatedArrivalStore with _$EstimatedArrivalStore;

abstract class _EstimatedArrivalStore with Store {
  _EstimatedArrivalStore() {
    fetchStopPoints();
  }

  ObservableList<BusStopsModel> busStopPointsFuture = ObservableList<BusStopsModel>();

  @action
  Future<void> fetchStopPoints() async {

    List<BusStopsModel> stopModels = await BusStopRepository.searchListStop();
    busStopPointsFuture = stopModels.asObservable();
  }

  @action
  Future<void>findEstimatedArrivalByStopPoint(int stopId) async {
   var stopModel = await EstimatedArrivalRepository.searchEstimatedArrival(stopId);
  }

}
