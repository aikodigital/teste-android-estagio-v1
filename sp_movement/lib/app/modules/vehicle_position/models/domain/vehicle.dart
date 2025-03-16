class Vehicle {
  int vehiclePrefix;
  bool isAccessibleForDisabilities;
  String timeLocationCaptured;
  double latitude;
  double longitude;

  Vehicle({
    required this.vehiclePrefix,
    required this.isAccessibleForDisabilities,
    required this.timeLocationCaptured,
    required this.latitude,
    required this.longitude,
  });
}
