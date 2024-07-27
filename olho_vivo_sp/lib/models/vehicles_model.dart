class VehiclesModel {
  final String prefix;
  final String arrivalTime;
  final bool isAffordable;
  final double yPos;
  final double xPos;

  VehiclesModel({
    required this.prefix,
    required this.arrivalTime,
    required this.isAffordable,
    required this.yPos,
    required this.xPos,
  });

  VehiclesModel.fromMap(Map<String, dynamic> res)
      : prefix = res['p'],
        arrivalTime = res['t'],
        isAffordable = res['a'],
        yPos = res['py'],
        xPos = res['px'];
}
