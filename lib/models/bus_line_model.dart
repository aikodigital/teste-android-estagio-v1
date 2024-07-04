import 'package:json_annotation/json_annotation.dart';

part 'bus_line_model.g.dart';

@JsonSerializable()
class BusLineModel {
  @JsonKey(name: 'cl')
  final int id;

  @JsonKey(name: 'lc')
  final bool circular;

  @JsonKey(name: 'lt')
  final String primaryLine;

  @JsonKey(name: 'tl')
  final int secondaryLine;

  @JsonKey(name: 'tp')
  final String startTerminal;

  @JsonKey(name: 'ts')
  final String endTerminal;

  BusLineModel({
    required this.id,
    required this.circular,
    required this.primaryLine,
    required this.secondaryLine,
    required this.startTerminal,
    required this.endTerminal,
  });

  factory BusLineModel.fromJson(Map<String, dynamic> json) =>
      _$BusLineModelFromJson(json);

  Map<String, dynamic> toJson() => _$BusLineModelToJson(this);
}
