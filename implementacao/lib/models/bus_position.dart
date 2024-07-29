import 'package:implementacao/models/bus.dart';
import 'package:implementacao/models/linha.dart';

class BusPosition {
  final String horario;
  final Linha linhaOnibus;
  final int quantidadeVeiculos;
  List<Bus> listaOnibus;

  BusPosition(this.horario, this.linhaOnibus, this.quantidadeVeiculos, this.listaOnibus);

  factory BusPosition.fromJson(dynamic data, String datetime, List<Bus> busList) {
    return BusPosition(
      datetime,
      Linha(
        data['cl'],
        true,
        data['c'],
        data['sl'],
        data['lt1'],
        data['lt0'],
      ),
      data['qv'],
      busList,
    );
  }
}
