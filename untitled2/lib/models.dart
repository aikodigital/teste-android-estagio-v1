import 'dart:io';

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

// class ParadasPrevisao {
//   final int cp;
//   final String np;
//   final double py;
//   final double px;
//   final List<LinhasPrevisao> l;
//
//   const ParadasPrevisao({
//     required this.cp,
//     required this.np,
//     required this.py,
//     required this.px,
//     required this.l,
//   });
//
//   factory ParadasPrevisao.fromJson(Map<String, dynamic> json){
//     return ParadasPrevisao(
//       cp: json['cp'],
//       np: json['np'],
//       py: json['py'],
//       px: json['px'],
//       l: List<LinhasPrevisao>.from(json['l'].map((x) => LinhasPrevisao.fromJson(x))),
//     );
//   }
//
//   List<ParadasPrevisao> listaDePSChegadaFromJson(List<dynamic> jsonList){
//     return jsonList.map((json) => ParadasPrevisao.fromJson(json)).toList();
//   }
//
// }
//
// class LinhasPrevisao{
//   final String c;
//   final int cl;
//   final int sl;
//   final String lt0;
//   final String lt1;
//   final int qv;
//   final List<VeiculoPrevisao> vs;
//
//   LinhasPrevisao({required this.c, required this.cl, required this.sl, required this.lt0, required this.lt1, required this.qv, required this.vs});
//
//   factory LinhasPrevisao.fromJson(Map<String, dynamic> json) {
//     //var list = json['vs'] as List;
//     return LinhasPrevisao(
//       c: json['c'],
//       cl: json['cl'],
//       sl: json['sl'],
//       lt0: json['lt0'],
//       lt1: json['lt1'],
//       qv: json['qv'],
//       vs: json['vs'].map((i) => VeiculoPrevisao.fromJson(i)).toList(),
//     );
//   }
// }
//
// class VeiculoPrevisao{
//   final String p;
//   final String t;
//   final bool a;
//   final String ta;
//   final double py;
//   final double px;
//   final dynamic sv;
//   final dynamic is_;
//
//   const VeiculoPrevisao({
//     required this.p,
//     required this.t,
//     required this.a,
//     required this.ta,
//     required this.py,
//     required this.px,
//     this.sv,
//     this.is_
//   });
//
//   factory VeiculoPrevisao.fromJson(Map<String, dynamic> json){
//     return VeiculoPrevisao(
//       p: json['p'],
//       t: json['t'],
//       a: json['a'],
//       ta: json['ta'],
//       py: json['py'].toDouble(),
//       px: json['px'].toDouble(),
//       sv: json['sv'],
//       is_: json['is'],
//     );
//   }
//
//   List<VeiculoPrevisao> listaDeVSChegadaFromJson(List<dynamic> jsonList) {
//     return jsonList.map((json) => VeiculoPrevisao.fromJson(json)).toList();
//   }
// }
//
// class PrevisaoChegada {
//   final String hr;
//   final List<ParadasPrevisao> ps;
//
//   const PrevisaoChegada({
//     required this.hr,
//     required this.ps
//   });
//
//   factory PrevisaoChegada.fromJson(Map<String, dynamic> json){
//     return PrevisaoChegada(
//       hr: json['hr'],
//       ps: List<ParadasPrevisao>.from(json['ps'].map((x) => ParadasPrevisao.fromJson(x))),
//     );
//   }
//
//   static List<PrevisaoChegada> listaDePrevisaoChegadaFromJson(List<dynamic> jsonList) {
//     return jsonList.map((json) => PrevisaoChegada.fromJson(json)).toList();
//   }
// }

class ParadasPrevisao {
  final int cp;
  final String np;
  final double py;
  final double px;
  final List<LinhasPrevisao> l;

  const ParadasPrevisao({
    required this.cp,
    required this.np,
    required this.py,
    required this.px,
    required this.l,
  });

  factory ParadasPrevisao.fromJson(Map<String, dynamic> json) {
    return ParadasPrevisao(
      cp: json['cp'] ?? 0,
      np: json['np'] ?? '',
      py: json['py']?.toDouble() ?? 0.0,
      px: json['px']?.toDouble() ?? 0.0,
      l: List<LinhasPrevisao>.from(
          (json['l'] as List<dynamic>? ?? []).map((x) => LinhasPrevisao.fromJson(x))
      ),
    );
  }

  static List<ParadasPrevisao> listaDePSChegadaFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => ParadasPrevisao.fromJson(json)).toList();
  }
}

class LinhasPrevisao {
  final String c;
  final int cl;
  final int sl;
  final String lt0;
  final String lt1;
  final int qv;
  final List<VeiculoPrevisao> vs;

  LinhasPrevisao({
    required this.c,
    required this.cl,
    required this.sl,
    required this.lt0,
    required this.lt1,
    required this.qv,
    required this.vs,
  });

  factory LinhasPrevisao.fromJson(Map<String, dynamic> json) {
    return LinhasPrevisao(
      c: json['c'] ?? '',
      cl: json['cl'] ?? 0,
      sl: json['sl'] ?? 0,
      lt0: json['lt0'] ?? '',
      lt1: json['lt1'] ?? '',
      qv: json['qv'] ?? 0,
      vs: List<VeiculoPrevisao>.from(
          (json['vs'] as List<dynamic>? ?? []).map((i) => VeiculoPrevisao.fromJson(i))
      ),
    );
  }
}

class VeiculoPrevisao {
  final String p;
  final String t;
  final bool a;
  final String ta;
  final double py;
  final double px;
  final dynamic sv;
  final dynamic is_;

  const VeiculoPrevisao({
    required this.p,
    required this.t,
    required this.a,
    required this.ta,
    required this.py,
    required this.px,
    this.sv,
    this.is_,
  });

  factory VeiculoPrevisao.fromJson(Map<String, dynamic> json) {
    return VeiculoPrevisao(
      p: json['p'] ?? '',
      t: json['t'] ?? '',
      a: json['a'] ?? false,
      ta: json['ta'] ?? '',
      py: json['py']?.toDouble() ?? 0.0,
      px: json['px']?.toDouble() ?? 0.0,
      sv: json['sv'],
      is_: json['is'],
    );
  }

  static List<VeiculoPrevisao> listaDeVSChegadaFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => VeiculoPrevisao.fromJson(json)).toList();
  }
}

class PrevisaoChegada {
  final String hr;
  final List<ParadasPrevisao> p;

  const PrevisaoChegada({
    required this.hr,
    required this.p,
  });

  factory PrevisaoChegada.fromJson(Map<String, dynamic> json) {
    return PrevisaoChegada(
      hr: json['hr'] ?? '',
      p: [ParadasPrevisao.fromJson(json['p'])],
    );
  }
  
  static PrevisaoChegada previsaoChegadaFromJson(Map<String, dynamic> jsonMap){
    if (jsonMap is Map<String, dynamic>) {
      return PrevisaoChegada.fromJson(jsonMap);
    } else {
      print(ContentType.json);
      print(jsonMap);
      throw Exception('Formato de JSON inesperado');
    }
  }

  static List<PrevisaoChegada> listaDePrevisaoChegadaFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) {
      if (json is Map<String, dynamic>) {
        return PrevisaoChegada.fromJson(json);
      } else {
        throw Exception('Formato de JSON inesperado');
      }
    }).toList();
  }
}