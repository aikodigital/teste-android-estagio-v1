class BusStop{
  int id;
  String name;
  String address;
  double latitude;
  double longitude;

  BusStop(
      {required this.id,
      required this.name,
      required this.address,
      required this.latitude, 
      required this.longitude});

  factory BusStop.fromJson(Map<String, dynamic> responseData) {
    return BusStop(
        id: responseData['cp'],
        name: responseData['np'],
        address: responseData['ed'],
        longitude: responseData['px'],
        latitude: responseData['py']);
  }
}