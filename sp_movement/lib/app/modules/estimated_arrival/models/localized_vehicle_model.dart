import 'package:sp_movement/app/modules/estimated_arrival/models/domain/localized_vehicles.dart';

class LocalizedVehicleModel extends LocalizedVehicles {
  LocalizedVehicleModel({
    vehiclePrefix,
    scheduledArrivalTime,
    accessible,
    timeLocationCaptured,
    latitude,
    longitude,
  }) : super(
          vehiclePrefix: vehiclePrefix,
          scheduledArrivalTime: scheduledArrivalTime,
          accessible: accessible,
          timeLocationCaptured: timeLocationCaptured,
          latitude: latitude,
          longitude: longitude,
        );

  factory LocalizedVehicleModel.fromJson(Map<String, dynamic> json) {

    return LocalizedVehicleModel(
      vehiclePrefix: json['p'],
      scheduledArrivalTime: json['t'],
      accessible: json['a'],
      timeLocationCaptured: json['ta'],
      latitude: json['py'],
      longitude: json['px'],
    );
  }
}
