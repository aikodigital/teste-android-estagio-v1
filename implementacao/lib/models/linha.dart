class Linha {
  int idLinha;
  bool linhaCircular;
  String letreiro;
  int sentidoLinha;
  String partida;
  String destino;

  Linha(
    this.idLinha,
    this.linhaCircular,
    this.letreiro,
    this.sentidoLinha,
    this.partida,
    this.destino,
  );

  factory Linha.fromJson(dynamic data) {
    return Linha(
        data['cl'] as int,
        data['lc'] as bool,
        "${data['lt'] as String}-${data['tl']}",
        data['sl'] as int,
        data['tp'] as String,
        data['ts'] as String);
  }
}
