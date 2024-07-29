import 'package:sp_movement/app/modules/estimated_arrival/models/localized_vehicle_model.dart';
import 'package:sp_movement/app/modules/vehicle_position/models/vehicle_model.dart';

import 'domain/localized_lines.dart';

class LocalizedLinesModel extends LocalizedLines {
  LocalizedLinesModel({
    code,
    id,
    direction,
    destination,
    origin,
    vehicleCount,
    vehicles,

  }) : super(
    code: code,
    id: id,
    direction: direction,
    destination: destination,
    origin: origin,
    vehicleCount: vehicleCount,
    vehicles: vehicles,
  );

  factory LocalizedLinesModel.fromJson(Map<String, dynamic> json) {
    List<LocalizedVehicleModel> itens = [];
    if (json['vs'] != null) {
      json['vs'].forEach((v) {
        itens.add(LocalizedVehicleModel.fromJson(v));
      });
    }

    return LocalizedLinesModel(
      code: json['c'],
      id: json['cl'],
      direction: json['sl'],
      destination: json['lt0'],
      origin: json['lt1'],
      vehicleCount: json['qv'],
      vehicles: itens,
    );
  }
}