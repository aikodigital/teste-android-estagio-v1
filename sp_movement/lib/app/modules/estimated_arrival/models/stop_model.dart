import 'package:sp_movement/app/modules/estimated_arrival/models/domain/localized_lines.dart';
import 'package:sp_movement/app/modules/estimated_arrival/models/domain/stop.dart';

import 'localized_lines_model.dart';

class StopModel extends Stop{
  StopModel({
    code,
    name,
    latitude,
    longitude,
    localizedLines,
  }): super(
    code: code,
    name: name,
    latitude: latitude,
    longitude: longitude,
    localizedLines: localizedLines,
  );

  factory StopModel.fromJson(Map<String, dynamic> json){
    List<LocalizedLinesModel> itens = [];
    if(json['l'] !=null ){
      json['l'].forEach((v){
        itens.add(LocalizedLinesModel.fromJson(v));
      });
    }

    return StopModel(
      code: json['cp'],
      name: json['np'],
      latitude: json['py'],
      longitude: json['px'],
      localizedLines: itens,
    );
  }
}