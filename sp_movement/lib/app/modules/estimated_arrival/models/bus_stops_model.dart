import 'package:sp_movement/app/modules/estimated_arrival/models/domain/bus_stops.dart';

class BusStopsModel extends BusStops{
  BusStopsModel({
     stopId,
     stopName,
     stopAddress,
     latitude,
    longitude,
  }): super(
    stopId:stopId,
    stopName: stopName,
    stopAddress : stopAddress,
    latitude: latitude,
    longitude: longitude,
  );

  factory BusStopsModel.fromJson(Map<String, dynamic> json){
    return BusStopsModel(
      stopId: json['cp'],
      stopName: json['np'],
      stopAddress: json['ed'],
      latitude: json['py'],
      longitude: json['px'],
    );
  }
}