enum ModoLinha {
  base([10]),
  atendimento([21, 23, 32, 41]),
  invalido([0]);

  factory ModoLinha.fromInt(int number) {
    return ModoLinha.values.firstWhere(
        (e) => e.numerosLetreiro.contains(number),
        orElse: () => ModoLinha.invalido);
  }

  final List<int> numerosLetreiro;

  const ModoLinha(this.numerosLetreiro);
}
