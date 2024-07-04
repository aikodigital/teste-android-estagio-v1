// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bus_stop_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BusStopModel _$BusStopModelFromJson(Map<String, dynamic> json) => BusStopModel(
      id: (json['cp'] as num).toInt(),
      name: json['np'] as String,
      address: json['ed'] as String,
      latitude: (json['py'] as num).toDouble(),
      longitude: (json['px'] as num).toDouble(),
    );

Map<String, dynamic> _$BusStopModelToJson(BusStopModel instance) =>
    <String, dynamic>{
      'cp': instance.id,
      'np': instance.name,
      'ed': instance.address,
      'py': instance.latitude,
      'px': instance.longitude,
    };

BusArrivalModel _$BusArrivalModelFromJson(Map<String, dynamic> json) =>
    BusArrivalModel(
      prefix: json['prefix'] as String,
      arrivalTime: json['arrivalTime'] as String,
      accessible: json['accessible'] as bool,
      captureTime: json['captureTime'] as String,
      latitude: (json['latitude'] as num).toDouble(),
      longitude: (json['longitude'] as num).toDouble(),
    );

Map<String, dynamic> _$BusArrivalModelToJson(BusArrivalModel instance) =>
    <String, dynamic>{
      'prefix': instance.prefix,
      'arrivalTime': instance.arrivalTime,
      'accessible': instance.accessible,
      'captureTime': instance.captureTime,
      'latitude': instance.latitude,
      'longitude': instance.longitude,
    };
