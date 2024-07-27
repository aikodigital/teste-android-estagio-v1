import 'vehicles_model.dart';

class ArrivalForecastModel {
  final String label;
  final int lineCode;
  final int direction;
  final String labelDestination;
  final String labelOrigin;
  final List<VehiclesModel> vehicles;

  ArrivalForecastModel({
    required this.label,
    required this.lineCode,
    required this.direction,
    required this.labelDestination,
    required this.labelOrigin,
    required this.vehicles,
  });

  ArrivalForecastModel.fromMap(Map<String, dynamic> res)
      : label = res['c'],
        lineCode = res['cl'],
        direction = res['sl'],
        labelDestination = res['lt0'],
        labelOrigin = res['lt1'],
        vehicles = (res['vs'] as List)
            .map(
              (vehicle) => VehiclesModel.fromMap(
                vehicle,
              ),
            )
            .toList();
}
