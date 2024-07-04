import 'dart:convert';
import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import '../models/bus_line_model.dart';
import '../models/bus_stop_model.dart';
import '../models/curridor_model.dart';

class OlhoVivoService extends ChangeNotifier {
  static const String _baseUrl = 'https://aiko-olhovivo-proxy.aikodigital.io';
  static const String _token =
      'f5a8b3da9b37e42782343896d95b2f2a94b4510dde3d6cf345e765d4802347cd';
  String? _sessionToken;

  Future<void> authenticate() async {
    final response = await http.post(
      Uri.parse('$_baseUrl/Login/Autenticar?token=$_token'),
    );
    if (response.statusCode == 200) {
      _sessionToken = response.body; // Verifica o que a API retorna como token
    } else {
      throw Exception('Failed to authenticate');
    }
  }

  Future<List<dynamic>> getBusPositions() async {
    if (_sessionToken == null) {
      print('No session token available. Authenticating...');
      await authenticate();
    }

    final response = await http.get(
      Uri.parse('$_baseUrl/Posicao'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    print('Response status code: ${response.statusCode}');
    print('Response body: ${response.body}');

    if (response.statusCode == 200) {
      final decodedResponse = json.decode(response.body);
      print('Decoded response: $decodedResponse');

      return decodedResponse['l'];
    } else {
      print('Failed to load bus positions: ${response.statusCode}');
      throw Exception('Failed to load bus positions');
    }
  }

  Future<List<BusLineModel>> searchBusLines(String query, int sentido) async {
    if (_sessionToken == null) {
      await authenticate();
    }
    final response = await http.get(
      Uri.parse(
          '$_baseUrl/Linha/BuscarLinhaSentido?termosBusca=$query&sentido=$sentido'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    if (response.statusCode == 200) {
      final List<dynamic> decodedResponse = json.decode(response.body);
      return decodedResponse
          .map((json) => BusLineModel.fromJson(json))
          .toList();
    } else {
      throw Exception('Failed to search bus lines');
    }
  }

  Future<List<dynamic>> searchStops(String query) async {
    if (_sessionToken == null) {
      await authenticate();
    }
    final response = await http.get(
      Uri.parse('$_baseUrl/Parada/Buscar?termosBusca=$query'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to search stops');
    }
  }

  Future<List<dynamic>> searchStopsByLine(int lineId) async {
    if (_sessionToken == null) {
      await authenticate();
    }
    final response = await http.get(
      Uri.parse('$_baseUrl/Parada/BuscarParadasPorLinha?codigoLinha=$lineId'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to search stops by line');
    }
  }

  Future<List<dynamic>> searchStopsByCorridor(int corridorId) async {
    if (_sessionToken == null) {
      await authenticate();
    }
    final response = await http.get(
      Uri.parse(
          '$_baseUrl/Parada/BuscarParadasPorCorredor?codigoCorredor=$corridorId'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    if (response.statusCode == 200) {
      return json.decode(response.body);
    } else {
      throw Exception('Failed to search stops by corridor');
    }
  }

  Future<List<BusArrivalModel>> getBusArrivals(int stopId) async {
    final response = await http
        .get(Uri.parse('$_baseUrl/Previsao/Parada?codigoParada=$stopId'));
    if (response.statusCode == 200) {
      final data = json.decode(response.body);
      final List arrivalsJson = data['p']['l'][0]['vs'];
      return arrivalsJson
          .map((json) => BusArrivalModel.fromJson(json))
          .toList();
    } else {
      throw Exception('Failed to load bus arrivals');
    }
  }

  Future<List<CurridorModel>> getCorridors() async {
    if (_sessionToken == null) {
      await authenticate();
    }

    final response = await http.get(
      Uri.parse('$_baseUrl/Corredor'),
      headers: {'Authorization': 'Bearer $_sessionToken'},
    );

    if (response.statusCode == 200) {
      final List<dynamic> corridorsJson = json.decode(response.body);
      return corridorsJson.map((json) => CurridorModel.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load corridors');
    }
  }
}
