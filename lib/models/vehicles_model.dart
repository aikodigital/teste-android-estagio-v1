class VehiclesModel {
  final String label;
  final int lineCode;
  final int direction;
  final String labelOrigin;
  final String labelDestination;
  final String prefix;
  final String arrivalTime;
  final bool isAffordable;
  final double yPos;
  final double xPos;

  VehiclesModel({
    required this.label,
    required this.lineCode,
    required this.direction,
    required this.labelOrigin,
    required this.labelDestination,
    required this.prefix,
    required this.arrivalTime,
    required this.isAffordable,
    required this.yPos,
    required this.xPos,
  });

  VehiclesModel.fromMap(Map<String, dynamic> res)
      : label = res['c'],
        lineCode = res['cl'],
        direction = res['sl'],
        labelOrigin = res['lt0'],
        labelDestination = res['lt1'],
        prefix = res['p'],
        arrivalTime = res['t'],
        isAffordable = res['a'],
        yPos = res['py'],
        xPos = res['px'];
}
