import 'package:json_annotation/json_annotation.dart';

part 'bus_stop_model.g.dart';

@JsonSerializable()
class BusStopModel {
  @JsonKey(name: 'cp')
  final int id;

  @JsonKey(name: 'np')
  final String name;

  @JsonKey(name: 'ed')
  final String address;

  @JsonKey(name: 'py')
  final double latitude;

  @JsonKey(name: 'px')
  final double longitude;

  BusStopModel({
    required this.id,
    required this.name,
    required this.address,
    required this.latitude,
    required this.longitude,
  });

  factory BusStopModel.fromJson(Map<String, dynamic> json) =>
      _$BusStopModelFromJson(json);

  Map<String, dynamic> toJson() => _$BusStopModelToJson(this);
}

@JsonSerializable()
class BusArrivalModel {
  final String prefix;
  final String arrivalTime;
  final bool accessible;
  final String captureTime;
  final double latitude;
  final double longitude;

  BusArrivalModel({
    required this.prefix,
    required this.arrivalTime,
    required this.accessible,
    required this.captureTime,
    required this.latitude,
    required this.longitude,
  });

  factory BusArrivalModel.fromJson(Map<String, dynamic> json) {
    return BusArrivalModel(
      prefix: json['p'],
      arrivalTime: json['t'],
      accessible: json['a'],
      captureTime: json['ta'],
      latitude: json['py'],
      longitude: json['px'],
    );
  }
}
