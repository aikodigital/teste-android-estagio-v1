import 'package:sp_movement/app/modules/bus_route/models/domain/vehicle.dart';

class VehicleLocation{
  String fullSign;
  int   lineIdCode;
  int   operationDirection;
  String  lineDestionSign;
  String  lineOriginSign;
  int     numberVehicleLocated;
  List<Vehicle> listVehicles;

  VehicleLocation({
    required this.fullSign,
    required this.lineIdCode,
    required this.lineDestionSign,
    required this.operationDirection,
    required this.lineOriginSign,
    required this.numberVehicleLocated,
    required this.listVehicles,
});

}