import 'package:sp_movement/app/modules/estimated_arrival/models/domain/localized_vehicles.dart';

class LocalizedLines {
  String code;
  int id;
  int direction;
  String destination;
  String origin;
  int vehicleCount;
  List<LocalizedVehicles> vehicles;

  LocalizedLines({
    required this.code,
    required this.id,
    required this.direction,
    required this.destination,
    required this.origin,
    required this.vehicleCount,
    required this.vehicles,
  });
}
