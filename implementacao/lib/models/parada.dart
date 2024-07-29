import 'package:implementacao/models/position.dart';

class Parada {
  final int idParada;
  final String nomeParada;
  final String endereco;
  final Position position;

  Parada(this.idParada, this.nomeParada, this.endereco, this.position);

  factory Parada.fromJson(dynamic data) {
    return Parada(
      data['cp'] as int,
      data['np'] as String,
      data['ed'] as String,
      Position(data['py'], data['px']),
    );
  }
}
