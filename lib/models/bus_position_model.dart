import 'package:json_annotation/json_annotation.dart';

part 'bus_position_model.g.dart';

@JsonSerializable()
class BusPositionModel {
  final int id;
  final String name;
  final List<VehicleModel> vehicles;
  final String hours;

  BusPositionModel(
      {required this.id,
        required this.name,
        required this.vehicles,
        required this.hours});

  factory BusPositionModel.fromJson(Map<String, dynamic> json) =>
      _$BusPositionModelFromJson(json);

  Map<String, dynamic> toJson() => _$BusPositionModelToJson(this);
}

@JsonSerializable()
class VehicleModel {
  final int id;
  final double latitude;
  final double longitude;
  final String prefix;

  VehicleModel(
      {required this.id,
        required this.latitude,
        required this.longitude,
        required this.prefix});

  factory VehicleModel.fromJson(Map<String, dynamic> json) =>
      _$VehicleModelFromJson(json);

  Map<String, dynamic> toJson() => _$VehicleModelToJson(this);
}
