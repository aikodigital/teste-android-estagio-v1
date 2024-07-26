import 'package:sp_movement/app/modules/bus_route/models/domain/vehicle.dart';

class VehicleModel extends Vehicle{
  VehicleModel({
    vehiclePrefix,
    isAccessibleForDisabilities,
    timeLocationCaptured,
    latitude,
    longitude,
}): super(vehiclePrefix: vehiclePrefix,
    isAccessibleForDisabilities:isAccessibleForDisabilities,
    timeLocationCaptured: timeLocationCaptured,
    latitude:latitude,
    longitude:longitude,
  );

  factory VehicleModel.fromJson(Map<String, dynamic> json){
    return VehicleModel(
      vehiclePrefix: json['p'],
      isAccessibleForDisabilities: json['a'],
      timeLocationCaptured: json['ta'],
      latitude: json['py'],
      longitude: json['px'],

    );
  }

}
