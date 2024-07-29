import 'dart:convert';

import 'package:teste_android_estagio_v1/models/enums/sentido_linha.dart';

import 'veiculo.dart';

class LinhaVeiculo {
  final int id;
  final String letreiroCompleto;
  final SentidoLinha sentidoLinha;
  final String destino;
  final String origem;
  final List<Veiculo>? veiculos;
  LinhaVeiculo({
    required this.id,
    required this.letreiroCompleto,
    required this.sentidoLinha,
    required this.destino,
    required this.origem,
    this.veiculos,
  });

  LinhaVeiculo copyWith({
    int? id,
    SentidoLinha? sentidoLinha,
    String? terminalPrincipal,
    String? letreiroCompleto,
    String? terminalSecundario,
    List<Veiculo>? veiculos,
  }) {
    return LinhaVeiculo(
      id: id ?? this.id,
      letreiroCompleto: letreiroCompleto ?? this.letreiroCompleto,
      sentidoLinha: sentidoLinha ?? this.sentidoLinha,
      destino: terminalPrincipal ?? this.destino,
      origem: terminalSecundario ?? this.origem,
      veiculos: veiculos ?? this.veiculos,
    );
  }

  factory LinhaVeiculo.fromMap(Map<String, dynamic> map) {
    return LinhaVeiculo(
      id: map['cl'],
      letreiroCompleto: map['c'],
      sentidoLinha: SentidoLinha.fromInt(map['sl']),
      destino: map['lt0'],
      origem: map['lt1'],
      veiculos: map['vs'] != null
          ? List<Veiculo>.from(map['vs'].map((x) => Veiculo.fromMap(x)))
          : null,
    );
  }

  factory LinhaVeiculo.fromJson(String source) =>
      LinhaVeiculo.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  bool operator ==(covariant LinhaVeiculo other) {
    if (identical(this, other)) return true;

    return other.id == id &&
        other.sentidoLinha == sentidoLinha &&
        other.destino == destino &&
        other.origem == origem;
  }

  @override
  int get hashCode {
    return id.hashCode ^
        sentidoLinha.hashCode ^
        destino.hashCode ^
        origem.hashCode;
  }

  String get rota {
    if (sentidoLinha == SentidoLinha.invalido) {
      return "Sentido inválido";
    }
    if (sentidoLinha == SentidoLinha.principalSecundario) {
      return "$destino - $origem";
    } else {
      return "$origem - $destino";
    }
  }
}
