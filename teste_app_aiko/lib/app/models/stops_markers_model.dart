import 'package:google_maps_flutter/google_maps_flutter.dart';

import 'package:teste_app_aiko/app/models/lines_maker_model.dart';

class StopsMarkerModel {
  final int cod;
  final String nome;
  final String endereco;
  final LatLng localizacao;
  final List<LinesMakerModel>? lines;
  StopsMarkerModel({
    required this.cod,
    required this.nome,
    required this.endereco,
    required this.localizacao,
    this.lines,
  });

  factory StopsMarkerModel.fromMap(dynamic map) {
    return StopsMarkerModel(
        cod: map['cp'] as int,
        nome: map['np'] != null ? map['np'] as String : '',
        endereco: map['ed'] != null ? map['ed'] as String : '',
        localizacao: LatLng(map['py'], map['px']),
        lines: map['l'] != null
            ? (map['l'] as List).map(LinesMakerModel.fromMap).toList()
            : null);
  }

  @override
  String toString() {
    return 'StopsMarkerModel(cod: $cod, nome: $nome, endereco: $endereco, localizacao: $localizacao)';
  }

  StopsMarkerModel copyWith({
    int? cod,
    String? nome,
    String? endereco,
    LatLng? localizacao,
    List<LinesMakerModel>? lines,
  }) {
    return StopsMarkerModel(
      cod: cod ?? this.cod,
      nome: nome ?? this.nome,
      endereco: endereco ?? this.endereco,
      localizacao: localizacao ?? this.localizacao,
      lines: lines ?? this.lines,
    );
  }
}
