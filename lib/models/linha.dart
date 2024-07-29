import 'dart:convert';

import 'package:teste_android_estagio_v1/models/enums/modo_linha.dart';

class Linha {
  final int id;
  final bool modoCircular;
  final String primeiraParteLetreiro;
  final int segundaParteLetreiro;
  final ModoLinha modoLinha;
  final String terminalPrincipal;
  final String terminalSecundario;
  Linha({
    required this.id,
    required this.modoCircular,
    required this.primeiraParteLetreiro,
    required this.segundaParteLetreiro,
    required this.modoLinha,
    required this.terminalPrincipal,
    required this.terminalSecundario,
  });

  factory Linha.fromMap(Map<String, dynamic> map) {
    return Linha(
      id: map['cl'],
      modoCircular: map['lc'],
      primeiraParteLetreiro: map['lt'],
      segundaParteLetreiro: map['sl'],
      modoLinha: ModoLinha.fromInt(map['tl']),
      terminalPrincipal: map['tp'],
      terminalSecundario: map['ts'],
    );
  }

  factory Linha.fromJson(String source) =>
      Linha.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'Linha(cl: $id, lc: $modoCircular, lt: $primeiraParteLetreiro, sl: $segundaParteLetreiro, tl: $modoLinha, tp: $terminalPrincipal, ts: $terminalSecundario)';
  }

  @override
  bool operator ==(covariant Linha other) {
    if (identical(this, other)) return true;

    return other.id == id &&
        other.modoCircular == modoCircular &&
        other.primeiraParteLetreiro == primeiraParteLetreiro &&
        other.segundaParteLetreiro == segundaParteLetreiro &&
        other.modoLinha == modoLinha &&
        other.terminalPrincipal == terminalPrincipal &&
        other.terminalSecundario == terminalSecundario;
  }

  String get letreiroCompleto {
    return "$primeiraParteLetreiro - $segundaParteLetreiro";
  }

  String get rotaLinha {
    return "$terminalPrincipal - $terminalSecundario";
  }

  @override
  int get hashCode {
    return id.hashCode ^
        modoCircular.hashCode ^
        primeiraParteLetreiro.hashCode ^
        segundaParteLetreiro.hashCode ^
        modoLinha.hashCode ^
        terminalPrincipal.hashCode ^
        terminalSecundario.hashCode;
  }
}
