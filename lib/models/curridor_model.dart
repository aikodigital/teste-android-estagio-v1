import 'package:json_annotation/json_annotation.dart';

part 'curridor_model.g.dart';

@JsonSerializable()
class CurridorModel {
  final int id;
  final String name;

  CurridorModel({required this.id, required this.name});

  factory CurridorModel.fromJson(Map<String, dynamic> json) {
    return CurridorModel(
      id: json['cc'],
      name: json['nc'],
    );
  }

}