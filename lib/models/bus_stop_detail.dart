import 'package:mova_sp/models/vehicle.dart';

class BusStopDetail {
  String fullLabel; // [string]c
  int lineId; // [int]cl
  int lineDirection; // [int]sl
  String destinationLabel; // [string]lt0
  String originLabel; // [string]lt1
  int vehicleCount; // [int]qv
  List<Vehicle> vehicles; // [{}]vs

  BusStopDetail({
    required this.fullLabel,
    required this.lineId,
    required this.lineDirection,
    required this.destinationLabel,
    required this.originLabel,
    required this.vehicleCount,
    required this.vehicles,
  });

  factory BusStopDetail.fromJson(Map<String, dynamic> json) {
    var vehiclesJson = json['vs'] as List<dynamic>? ?? [];
    List<Vehicle> vehicles = vehiclesJson.map((e) => Vehicle.fromJson(e as Map<String, dynamic>)).toList();

    return BusStopDetail(
      fullLabel: json['c'] ?? '',
      lineId: json['cl'] ?? 0,
      lineDirection: json['sl'] ?? 0,
      destinationLabel: json['lt0'] ?? '',
      originLabel: json['lt1'] ?? '',
      vehicleCount: json['qv'] ?? 0,
      vehicles: vehicles,
    );
  }
}