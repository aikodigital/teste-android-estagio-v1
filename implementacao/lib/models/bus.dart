import 'package:implementacao/models/position.dart';

class Bus {
  final int id;
  final bool ativo;
  final Position? position;

  Bus(this.id, this.ativo, {this.position});

  factory Bus.fromJson(dynamic data) {
    return Bus(
      data['p'] as int,
      data['a'] as bool,
      position: Position(data['py'], data['px'],)
    );
  }
}
