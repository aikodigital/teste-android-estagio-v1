import 'package:mova_sp/models/vehicle.dart';

class BusLineDetail{

  int stopCode;
  String stopName;
  double latitude;
  double longitude;
  List<Vehicle> vehicles;

  BusLineDetail({
    required this.stopCode,
    required this.stopName,
    required this.latitude,
    required this.longitude,
    required this.vehicles,
  });

  factory BusLineDetail.fromJson(Map<String, dynamic> json) {
    return BusLineDetail(
      stopCode: json['cp'],
      stopName: json['np'],
      latitude: json['py'],
      longitude: json['px'],
      vehicles: (json['vs'] as List<dynamic>).map((vehicleJson) => Vehicle.fromJson(vehicleJson)).toList(),
    );
  }
}