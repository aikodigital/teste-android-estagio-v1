import 'dart:convert';

import 'package:http/http.dart';
import 'package:teste_android_estagio_v1/core/requests/reqhttp.dart';
import 'package:teste_android_estagio_v1/models/exceptions/sptrans_exception.dart';
import 'package:teste_android_estagio_v1/models/linha.dart';
import 'package:teste_android_estagio_v1/models/linha_veiculo.dart';
import 'package:teste_android_estagio_v1/models/parada.dart';
import 'package:teste_android_estagio_v1/models/parada_previsao.dart';

class SPTransService {
  final ReqHttp _reqHttp = ReqHttp();
  static const String _token =
      "ca8fa4bc128f1fe292a7748d3f1987bd86f4213f48dc674aa2ecd5e6061538fa";
  static bool _autenticado = false;

  /// Realiza a primeira requisição para autenticação <b>DEVE SER CHAMADA ANTES DE QUALQUER REQUISIÇÃO</b>
  Future<void> autenticar() async {
    if (_autenticado) return;
    Response response = await _reqHttp.post("Login/Autenticar?token=$_token");
    if (response.body == "true") {
      _autenticado = true;
      _reqHttp.setCookie(response.headers['set-cookie']!);
    } else {
      throw SpTransException("Erro ao autenticar");
    }
  }

  Future<List<Linha>> getLinhas(String termoBusca) async {
    try {
      termoBusca = "?termosBusca=$termoBusca";
      List<Linha> linhas = [];
      Response response = await _reqHttp.get("Linha/Buscar$termoBusca");
      List<dynamic> json = jsonDecode(response.body);
      for (var item in json) {
        linhas.add(Linha.fromMap(item));
      }
      return linhas;
    } catch (e, stack) {
      throw SpTransException("Erro ao buscar linhas", stackTrace: stack);
    }
  }

  Future<List<LinhaVeiculo>> getPosicoesVeiculos(
      {bool apenasPCD = false}) async {
    try {
      List<LinhaVeiculo> linhas = [];
      Response response = await _reqHttp.get("Posicao");
      List<dynamic> json = jsonDecode(response.body)["l"];
      for (var item in json) {
        LinhaVeiculo linha = LinhaVeiculo.fromMap(item);
        if (apenasPCD) {
          linha.veiculos!
              .removeWhere((veiculo) => veiculo.acessivelPCD == false);
        }
        linhas.add(linha);
      }
      return linhas;
    } catch (e, stack) {
      throw SpTransException("Erro ao buscar linhas com veículos",
          stackTrace: stack);
    }
  }

  Future<ParadaPrevisao?> getPrevisaoParada(int idParada) async {
    try {
      Response response =
          await _reqHttp.get("Previsao/Parada?codigoParada=$idParada");
      var jsonDecoded = jsonDecode(response.body);
      if (jsonDecoded["Message"] != null) {
        throw SpTransException(jsonDecoded["Message"]);
      }
      if (jsonDecoded["p"] == null) {
        return null;
      }
      ParadaPrevisao previsao = ParadaPrevisao.fromMap(jsonDecoded["p"]);
      return previsao;
    } catch (e, stack) {
      if (e is SpTransException) {
        rethrow;
      }
      throw SpTransException("Erro ao buscar linhas com veículos",
          stackTrace: stack);
    }
  }

  Future<List<Parada>> getParadas(String termoBusca) async {
    try {
      termoBusca = "?termosBusca=$termoBusca";
      List<Parada> paradas = [];
      Response response = await _reqHttp.get("Parada/Buscar$termoBusca");
      List<dynamic> json = jsonDecode(response.body);
      for (var item in json) {
        paradas.add(Parada.fromMap(item));
      }
      return paradas;
    } catch (e, stack) {
      throw SpTransException("Erro ao buscar paradas", stackTrace: stack);
    }
  }
}
