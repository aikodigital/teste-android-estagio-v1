import 'package:teste_android_estagio_v1/models/linha_veiculo.dart';
import 'package:teste_android_estagio_v1/models/veiculo.dart';

import 'enums/sentido_linha.dart';

class VeiculoPrevisaoDTO {
  late int idLinha;
  late String letreiroCompleto;
  late SentidoLinha sentidoLinha;
  late String destino;
  late String origem;
  late int prefixo;
  late bool acessivelPCD;
  late DateTime momentoLocalizacao;
  late DateTime? previsaoHorario;
  late double latitude;
  late double longitude;

  VeiculoPrevisaoDTO(
      {required Veiculo veiculo, required LinhaVeiculo linhaVeiculo}) {
    idLinha = linhaVeiculo.id;
    letreiroCompleto = linhaVeiculo.letreiroCompleto;
    sentidoLinha = linhaVeiculo.sentidoLinha;
    destino = linhaVeiculo.destino;
    origem = linhaVeiculo.origem;
    acessivelPCD = veiculo.acessivelPCD;
    momentoLocalizacao = veiculo.momentoLocalizacao;
    previsaoHorario = veiculo.previsaoHorario;
    latitude = veiculo.latitude;
    longitude = veiculo.longitude;
    prefixo = veiculo.prefixo;
  }

  String get previsaoRelativa {
    if (previsaoHorario == null) {
      return "Sem previsão";
    }
    Duration diferenca = previsaoHorario!.difference(DateTime.now());
    if (diferenca.inMinutes < 1) {
      return "Em instantes";
    }
    if (diferenca.inMinutes < 60) {
      return "em ${diferenca.inMinutes} min";
    }
    return "em ${diferenca.inHours}h ${diferenca.inMinutes.remainder(60)} min";
  }
}
