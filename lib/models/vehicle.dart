class Vehicle {
  String prefix; // [int]p DOCUMENTAÇÃO DIZ QUE É INT, MAS ESTÁ VINDO COMO STRING GET /Previsao/Parada?codigoParada={codigoParada}
  String arrivalTime; // [string]t
  bool isAccessible; // [bool]a
  String utcTimestamp; // [string]ta
  double latitude; // [double]py
  double longitude; // [double]px

  Vehicle({
    required this.prefix,
    required this.arrivalTime,
    required this.isAccessible,
    required this.utcTimestamp,
    required this.latitude,
    required this.longitude,
  });

  factory Vehicle.fromJson(Map<String, dynamic> json) {
    return Vehicle(
      prefix: json['p'] ?? 0,
      arrivalTime: json['t'] ?? '',
      isAccessible: json['a'] ?? false,
      utcTimestamp: json['ta'] ?? '',
      latitude: json['py']?.toDouble() ?? 0.0,
      longitude: json['px']?.toDouble() ?? 0.0,
    );
  }
}