import 'package:teste_app_aiko/app/models/bus_marker_model.dart';

class LinesMakerModel {
  final int cod;
  final int sentido;
  final String letreiroDestino;
  final String letreiroOrigem;
  final int qtdVeiculos;
  final List<BusMarkerModel> veiculos;
  LinesMakerModel({
    required this.cod,
    required this.sentido,
    required this.letreiroDestino,
    required this.letreiroOrigem,
    required this.qtdVeiculos,
    required this.veiculos,
  });

  factory LinesMakerModel.fromMap(map) {
    return LinesMakerModel(
      cod: map['cl'] as int,
      sentido: map['sl'] as int,
      letreiroDestino: map['lt0'] as String,
      letreiroOrigem: map['lt1'] as String,
      qtdVeiculos: map['qv'] as int,
      veiculos: List<BusMarkerModel>.from(
        (map['vs'] as List).map<BusMarkerModel>(BusMarkerModel.fromMap),
      ),
    );
  }

  @override
  String toString() {
    return 'LinesMakerModel(cod: $cod, sentido: $sentido, letreiroDestino: $letreiroDestino, letreiroOrigem: $letreiroOrigem, qtdVeiculos: $qtdVeiculos, veiculos: $veiculos)';
  }
}
