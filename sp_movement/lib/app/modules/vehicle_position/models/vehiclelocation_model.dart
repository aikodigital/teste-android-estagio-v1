import 'package:sp_movement/app/modules/vehicle_position/models/domain/vehiclelocation.dart';
import 'package:sp_movement/app/modules/vehicle_position/models/vehicle_model.dart';

class VehicleLocationModel extends VehicleLocation{
  VehicleLocationModel({
    fullSign,
    lineIdCode,
    operationDirection,
    lineDestionSign,
    lineOriginSign,
    numberVehicleLocated,
    listVehicles,
  }): super(
    fullSign:fullSign,
    lineIdCode: lineIdCode,
    operationDirection : operationDirection,
    lineDestionSign: lineDestionSign,
    lineOriginSign: lineOriginSign,
    numberVehicleLocated: numberVehicleLocated,
    listVehicles : listVehicles,
  );

  factory VehicleLocationModel.fromJson(Map<String, dynamic> json){
    List<VehicleModel> itens = [];
    if(json['vs'] !=null ){
      json['vs'].forEach((v){
        itens.add(VehicleModel.fromJson(v));
      });
    }

    return VehicleLocationModel(
      fullSign: json['c'],
      lineIdCode: json['cl'],
      operationDirection: json['sl'],
      lineDestionSign: json['lt0'],
      lineOriginSign: json['lt1'],
      numberVehicleLocated: json['qv'],
      listVehicles: itens,
    );
  }

}