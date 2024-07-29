import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:implementacao/models/bus.dart';
import 'package:implementacao/models/bus_position.dart';
import 'package:implementacao/models/previsao.dart';

import '../models/linha.dart';
import '../models/parada.dart';

class ApiOlhoVivo {
  final String requestLogin =
      "https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar?token={0c7f1b75dca038cc75e801e1c122657d25f3ca9cba11e39f69fa2ded11aa1309}";
  final String requestPosicao =
      "https://aiko-olhovivo-proxy.aikodigital.io/Posicao?token={0c7f1b75dca038cc75e801e1c122657d25f3ca9cba11e39f69fa2ded11aa1309}";
  final String requestLinhas =
      "https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=";
  final String requestParadas =
      "https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar?termosBusca=";
  final String requestPrevisao =
      "https://aiko-olhovivo-proxy.aikodigital.io/Previsao/Parada?codigoParada=";

  Future<bool> autenticar() async {
    final http.Response response = await http.post(Uri.parse(requestLogin));
    return response.body == true;
  }

  Future<List<Linha>> obterLinhas(String termosBusca) async {
    await autenticar();

    final http.Response response =
        await http.get(Uri.parse("$requestLinhas + $termosBusca"));
    final data = json.decode(response.body);

    List<Linha> linhas =
        List<Linha>.from(data.map((model) => Linha.fromJson(model)));
    return linhas;
  }

  Future<List<Parada>> obterParadas(String termosBusca) async {
    await autenticar();
    final http.Response response =
        await http.get(Uri.parse("$requestParadas + $termosBusca"));
    final data = json.decode(response.body);
    List<Parada> paradas =
        List<Parada>.from(data.map((model) => Parada.fromJson(model)));
    return paradas;
  }

  Future<List<BusPosition>> obterPosicoesVeiculos() async {
    await autenticar();
    final http.Response response = await http.get(Uri.parse(requestPosicao));
    final data = json.decode(response.body);
    List<BusPosition> busPosition = [];

    for (var item in data['l']) {
      var busList =
          List<Bus>.from(item['vs'].map((model) => Bus.fromJson(model)));
      busPosition.add(BusPosition.fromJson(item, data['hr'], busList));
    }
    return busPosition;
  }

  Future<List<Previsao>> obterPrevisaoChegadaOnibus(String termosBusca) async {
    await autenticar();
    final http.Response response =
        await http.get(Uri.parse("$requestPrevisao$termosBusca"));
    final data = json.decode(response.body)['p'];
    final int idParada = data['cp'];
    final String nomeParada = data['np'];

    List<Previsao> previsoes = [];

    for (var linha in data['l']) {
      for (var veiculo in linha['vs']) {
        previsoes.add(Previsao.fromJson(veiculo, idParada, nomeParada));
      }
    }
    return previsoes;
  }
}
