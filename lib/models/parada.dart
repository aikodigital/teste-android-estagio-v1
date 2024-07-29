import 'dart:convert';

class Parada {
  final int id;
  final String nomeParada;
  final String endereco;
  final double latitude;
  final double longitude;
  Parada({
    required this.id,
    required this.nomeParada,
    required this.endereco,
    required this.latitude,
    required this.longitude,
  });

  Parada copyWith({
    int? id,
    String? nomeParada,
    String? endereco,
    double? latitude,
    double? longitude,
  }) {
    return Parada(
      id: id ?? this.id,
      nomeParada: nomeParada ?? this.nomeParada,
      endereco: endereco ?? this.endereco,
      latitude: latitude ?? this.latitude,
      longitude: longitude ?? this.longitude,
    );
  }

  factory Parada.fromMap(Map<String, dynamic> map) {
    return Parada(
      id: map['cp'],
      nomeParada: map['np'],
      endereco: map['ed'] ?? map['np'],
      latitude: map['py'],
      longitude: map['px'],
    );
  }

  factory Parada.fromJson(String source) =>
      Parada.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'Parada(cp: $id, np: $nomeParada, ed: $endereco, py: $latitude, px: $longitude)';
  }

  @override
  bool operator ==(covariant Parada other) {
    if (identical(this, other)) return true;

    return other.id == id &&
        other.nomeParada == nomeParada &&
        other.endereco == endereco &&
        other.latitude == latitude &&
        other.longitude == longitude;
  }

  @override
  int get hashCode {
    return id.hashCode ^
        nomeParada.hashCode ^
        endereco.hashCode ^
        latitude.hashCode ^
        longitude.hashCode;
  }
}
