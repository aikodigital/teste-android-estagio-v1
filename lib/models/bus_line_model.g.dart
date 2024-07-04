// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bus_line_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

BusLineModel _$BusLineModelFromJson(Map<String, dynamic> json) => BusLineModel(
      id: (json['cl'] as num).toInt(),
      circular: json['lc'] as bool,
      primaryLine: json['lt'] as String,
      secondaryLine: (json['tl'] as num).toInt(),
      startTerminal: json['tp'] as String,
      endTerminal: json['ts'] as String,
    );

Map<String, dynamic> _$BusLineModelToJson(BusLineModel instance) =>
    <String, dynamic>{
      'cl': instance.id,
      'lc': instance.circular,
      'lt': instance.primaryLine,
      'tl': instance.secondaryLine,
      'tp': instance.startTerminal,
      'ts': instance.endTerminal,
    };
