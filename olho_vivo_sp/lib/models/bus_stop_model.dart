class BusStopModel {
  final int code;
  final String name;
  final String address;
  final double yPos;
  final double xPos;

  BusStopModel({
    required this.code,
    required this.name,
    required this.address,
    required this.yPos,
    required this.xPos,
  });

  BusStopModel.fromMap(Map<String, dynamic> res)
      : code = res['cp'],
        name = res['np'],
        address = res['ep'],
        yPos = res['yp'],
        xPos = res['xp'];
}
