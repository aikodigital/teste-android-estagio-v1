class Linha {
  final int cl;
  final bool lc;
  final String lt;
  final int sl;
  final int tl;
  final String tp;
  final String ts;

  Linha({
    required this.cl,
    required this.lc,
    required this.lt,
    required this.sl,
    required this.tl,
    required this.tp,
    required this.ts,
  });

  factory Linha.fromJson(Map<String, dynamic> json) {
    return Linha(
      cl: json['cl'],
      lc: json['lc'],
      lt: json['lt'],
      sl: json['sl'],
      tl: json['tl'],
      tp: json['tp'],
      ts: json['ts'],
    );
  }
}

class Parada {
  final int cp;
  final String np;
  final String ed;
  final double py;
  final double px;

  Parada({
    required this.cp,
    required this.np,
    required this.ed,
    required this.py,
    required this.px,
  });

  factory Parada.fromJson(Map<String, dynamic> json) {
    return Parada(
      cp: json['cp'],
      np: json['np'],
      ed: json['ed'],
      py: json['py'],
      px: json['px'],
    );
  }
}

class Veiculo {
  final int p;
  final bool a;
  final String ta;
  final double py;
  final double px;

  Veiculo({
    required this.p,
    required this.a,
    required this.ta,
    required this.py,
    required this.px,
  });

  factory Veiculo.fromJson(Map<String, dynamic> json) {
    return Veiculo(
      p: json['p'],
      a: json['a'],
      ta: json['ta'],
      py: json['py'],
      px: json['px'],
    );
  }
}
