// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'curridor_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CurridorModel _$CurridorModelFromJson(Map<String, dynamic> json) =>
    CurridorModel(
      id: (json['id'] as num).toInt(),
      name: json['name'] as String,
    );

Map<String, dynamic> _$CurridorModelToJson(CurridorModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
    };
