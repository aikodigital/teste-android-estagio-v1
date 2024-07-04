// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bus_position_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BusPositionModel _$BusPositionModelFromJson(Map<String, dynamic> json) =>
    BusPositionModel(
      id: (json['id'] as num).toInt(),
      name: json['name'] as String,
      vehicles: (json['vehicles'] as List<dynamic>)
          .map((e) => VehicleModel.fromJson(e as Map<String, dynamic>))
          .toList(),
      hours: json['hours'] as String,
    );

Map<String, dynamic> _$BusPositionModelToJson(BusPositionModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'vehicles': instance.vehicles,
      'hours': instance.hours,
    };

VehicleModel _$VehicleModelFromJson(Map<String, dynamic> json) => VehicleModel(
      id: (json['id'] as num).toInt(),
      latitude: (json['latitude'] as num).toDouble(),
      longitude: (json['longitude'] as num).toDouble(),
      prefix: json['prefix'] as String,
    );

Map<String, dynamic> _$VehicleModelToJson(VehicleModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
      'prefix': instance.prefix,
    };
