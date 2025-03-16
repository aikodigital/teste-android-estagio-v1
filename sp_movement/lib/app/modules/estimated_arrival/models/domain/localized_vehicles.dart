class LocalizedVehicles{
  String vehiclePrefix;
  String scheduledArrivalTime;
  bool  accessible;
  String timeLocationCaptured;
  double  latitude;
  double  longitude;

  LocalizedVehicles({
    required this.vehiclePrefix,
    required this.scheduledArrivalTime,
    required this.accessible,
    required this.timeLocationCaptured,
    required this.latitude,
    required this.longitude,
  });
}