class Parada {
  final int cp;
  final String np;
  final String ed;
  final double py;
  final double px;

  const Parada({
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
      py: json['py'].toDouble(),
      px: json['px'].toDouble(),
    );
  }

  List<Parada> listaDeParadasFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Parada.fromJson(json)).toList();
  }

}

class Linha {
  final int cl;
  final bool lc;
  final String lt;
  final int sl;
  final int tl;
  final String tp;
  final String ts;

  const Linha({
    required this.cl,
    required this.lc,
    required this.lt,
    required this.sl,
    required this.tl,
    required this.tp,
    required this.ts,
  });

  factory Linha.fromJson(Map<String, dynamic> json){
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

  static List<Linha> listaDeLinhasFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Linha.fromJson(json)).toList();
  }
}

class LV {
  final String c;
  final int cl;
  final int sl;
  final String lt0;
  final String lt1;
  final int qv;
  final List<VS> vs;

  const LV({
    required this.c,
    required this.cl,
    required this.sl,
    required this.lt0,
    required this.lt1,
    required this.qv,
    required this.vs,
  });

  factory LV.fromJson(Map<String, dynamic> json){
    return LV(
      c: json['c'],
      cl: json['cl'],
      sl: json['sl'],
      lt0: json['lt0'],
      lt1: json['lt1'],
      qv: json['qv'],
      vs: List<VS>.from(json['vs'].map((x) => VS.fromJson(x))),
    );
  }

  List<LV> listaDeLVFromJson(List<dynamic> jsonList){
    return jsonList.map((json) => LV.fromJson(json)).toList();
  }

}

class VS{
  final int p;
  final bool a;
  final String ta;
  final double py;
  final double px;

  const VS({
    required this.p,
    required this.a,
    required this.ta,
    required this.py,
    required this.px,
  });

  factory VS.fromJson(Map<String, dynamic> json){
    return VS(
      p: json['p'],
      a: json['a'],
      ta: json['ta'],
      py: json['py'].toDouble(),
      px: json['px'].toDouble(),
    );
  }

  List<VS> listaDeVSFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => VS.fromJson(json)).toList();
  }
}

class Veiculos {
  final String hr;
  final List<LV> l;

  const Veiculos({
    required this.hr,
    required this.l
  });

  factory Veiculos.fromJson(Map<String, dynamic> json){
    return Veiculos(
      hr: json['hr'],
      l: List<LV>.from(json['l'].map((x) => LV.fromJson(x))),
    );
  }

  List<Veiculos> listaDeVeiculosFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Veiculos.fromJson(json)).toList();
  }
}