enum SentidoLinha {
  /// Sentido terminal principal para terminal secundário
  principalSecundario,

  /// Sentido terminal secundário para terminal principal
  secundarioPrincipal,

  /// Sentido inválido
  invalido;

  factory SentidoLinha.fromInt(int? number) {
    switch (number) {
      case 1:
        return SentidoLinha.principalSecundario;
      case 2:
        return SentidoLinha.secundarioPrincipal;
      default:
        return SentidoLinha.invalido;
    }
  }
}
