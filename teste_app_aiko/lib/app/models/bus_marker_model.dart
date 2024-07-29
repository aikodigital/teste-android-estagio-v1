import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

class BusMarkerModel {
  final int cod;
  final bool acessivelPCD;
  final TimeOfDay? tempoChegada;
  final DateTime horarioUltimoLocal;
  final LatLng localizacao;
  BusMarkerModel({
    required this.cod,
    required this.acessivelPCD,
    this.tempoChegada,
    required this.horarioUltimoLocal,
    required this.localizacao,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'cod': cod,
      'acessivelPCD': acessivelPCD,
      'horarioUltimoLocal': horarioUltimoLocal.millisecondsSinceEpoch,
      'lat': localizacao.latitude,
      'lng': localizacao.longitude
    };
  }

  factory BusMarkerModel.fromMap(dynamic map) {
    final String? timeA = map['t'];

    TimeOfDay parseTimeFromString(String data) {
      final hour = int.parse(data.substring(0, 2));
      final minute = int.parse(data.substring(3));
      return TimeOfDay(hour: hour, minute: minute);
    }

    return BusMarkerModel(
      cod: int.tryParse(map['p']) ?? 0,
      // cod: 0,
      acessivelPCD: map['a'] as bool,
      tempoChegada: timeA != null ? parseTimeFromString(timeA) : null,
      horarioUltimoLocal: DateTime.parse(map['ta']),
      localizacao: LatLng(map['py'], map['px']),
    );
  }

  @override
  String toString() {
    return 'BusMarkerModel(cod: $cod, acessivelPCD: $acessivelPCD, horarioUltimoLocal: $horarioUltimoLocal, localizacao: $localizacao)';
  }
}
