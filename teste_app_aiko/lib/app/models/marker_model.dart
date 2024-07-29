import 'dart:convert';

class MarkerModel {
  late String id;
  final double lat;
  final double lng;
  final String? address;

  MarkerModel({
    required this.lat,
    required this.lng,
    this.address,
  }) {
    id = lat.toString() + lng.toString();
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'lat': lat,
      'lng': lng,
      'address': address,
    };
  }

  factory MarkerModel.fromMap(Map<String, dynamic> map) {
    return MarkerModel(
      lat: map['lat'] as double,
      lng: map['lng'] as double,
      address: map['address'] != null ? map['address'] as String : null,
    );
  }

  String toJson() => json.encode(toMap());

  factory MarkerModel.fromJson(String source) =>
      MarkerModel.fromMap(json.decode(source) as Map<String, dynamic>);
}
