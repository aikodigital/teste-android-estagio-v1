import 'dart:convert';

import 'package:teste_android_estagio_v1/models/linha_veiculo.dart';
import 'package:teste_android_estagio_v1/models/veiculo.dart';
import 'package:teste_android_estagio_v1/models/veiculo_previsao_dto.dart';

class ParadaPrevisao {
  final int id;
  final String nome;
  final double latitude;
  final double longitude;
  final List<LinhaVeiculo> relacaoLinhas;
  ParadaPrevisao({
    required this.id,
    required this.nome,
    required this.latitude,
    required this.longitude,
    required this.relacaoLinhas,
  });

  factory ParadaPrevisao.fromMap(Map<String, dynamic> map) {
    return ParadaPrevisao(
      id: map['cp'],
      nome: map['np'],
      latitude: map['py'],
      longitude: map['px'],
      relacaoLinhas: map['l'] != null
          ? List<LinhaVeiculo>.from(map['l']
              .map((x) => LinhaVeiculo.fromMap(x as Map<String, dynamic>)))
          : [],
    );
  }

  List<VeiculoPrevisaoDTO> get veiculosPrevisao {
    List<VeiculoPrevisaoDTO> veiculos = [];
    for (var linha in relacaoLinhas) {
      linha.veiculos?.forEach((veiculo) {
        veiculos.add(VeiculoPrevisaoDTO(veiculo: veiculo, linhaVeiculo: linha));
      });
    }
    veiculos.sort((a, b) => a.previsaoHorario!.compareTo(b.previsaoHorario!));
    return veiculos;
  }

  factory ParadaPrevisao.fromJson(String source) =>
      ParadaPrevisao.fromMap(json.decode(source) as Map<String, dynamic>);

  @override
  String toString() {
    return 'ParadaPrevisao(cp: $id, np: $nome, py: $latitude, px: $longitude, l: $relacaoLinhas)';
  }
}
