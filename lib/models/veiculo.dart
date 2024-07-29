import 'dart:convert';

class Veiculo {
  final int prefixo;
  final bool acessivelPCD;
  final DateTime momentoLocalizacao;

  /// Previsão de horário de chegada em relação a um ponto de parada, não é nulo quando buscado da rota
  final DateTime? previsaoHorario;
  final double latitude;
  final double longitude;
  Veiculo({
    required this.prefixo,
    required this.acessivelPCD,
    required this.momentoLocalizacao,
    required this.previsaoHorario,
    required this.latitude,
    required this.longitude,
  });

  Veiculo copyWith({
    int? prefixo,
    bool? acessivelPCD,
    DateTime? momentoLocalizacao,
    DateTime? previsaoHorario,
    double? latitude,
    double? longitude,
  }) {
    return Veiculo(
      prefixo: prefixo ?? this.prefixo,
      acessivelPCD: acessivelPCD ?? this.acessivelPCD,
      momentoLocalizacao: momentoLocalizacao ?? this.momentoLocalizacao,
      previsaoHorario: previsaoHorario ?? this.previsaoHorario,
      latitude: latitude ?? this.latitude,
      longitude: longitude ?? this.longitude,
    );
  }

  factory Veiculo.fromMap(Map<String, dynamic> map) {
    return Veiculo(
      prefixo: int.parse(map['p'].toString()),
      acessivelPCD: map['a'],
      momentoLocalizacao: DateTime.parse(map['ta']),
      previsaoHorario: _parseTempoChegada(map['t']),
      latitude: map['py'],
      longitude: map['px'],
    );
  }

  static DateTime? _parseTempoChegada(String? tempoChegada) {
    if (tempoChegada == null || tempoChegada.isEmpty) {
      return null;
    }
    DateTime now = DateTime.now();
    List<String> tempoChegadaSplit = tempoChegada.split(':');
    return DateTime(now.year, now.month, now.day,
        int.parse(tempoChegadaSplit[0]), int.parse(tempoChegadaSplit[1]));
  }

  factory Veiculo.fromJson(String source) =>
      Veiculo.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'V(p: $prefixo, a: $acessivelPCD, ta: $momentoLocalizacao, py: $latitude, px: $longitude)';
  }

  @override
  bool operator ==(covariant Veiculo other) {
    if (identical(this, other)) return true;

    return other.prefixo == prefixo &&
        other.acessivelPCD == acessivelPCD &&
        other.momentoLocalizacao == momentoLocalizacao &&
        other.latitude == latitude &&
        other.longitude == longitude;
  }

  @override
  int get hashCode {
    return prefixo.hashCode ^
        acessivelPCD.hashCode ^
        momentoLocalizacao.hashCode ^
        latitude.hashCode ^
        longitude.hashCode;
  }
}
