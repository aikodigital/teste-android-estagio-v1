// ignore_for_file: avoid_print

import 'dart:convert';
import 'package:http/http.dart' as http;

class SpTransService {
  final String authToken =
      "8ff22d2741a74fb3e65bb467e19d16a6341a652172e9cf1b620d61947df864e2";
  final String baseUrl = 'https://aiko-olhovivo-proxy.aikodigital.io';

  Future<void> authenticate() async {
    final response = await http.post(
      Uri.parse('$baseUrl/Login/Autenticar?token=$authToken'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
    );
    if (response.statusCode == 200 && response.body == 'true') {
      print('Autenticado com sucesso');
    } else {
      throw Exception('Falha na autenticação');
    }
  }

  Future<List<dynamic>> fetchPosicoes(String linha) async {
    try {
      await authenticate();
      final response = await http.get(
        Uri.parse('$baseUrl/Posicao/Linha?codigoLinha=$linha'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );
      if (response.statusCode == 200) {
        final Map<String, dynamic> data = jsonDecode(response.body);
        return data['vs'] ?? [];
      } else {
        throw Exception('Falha ao carregar posicoes: ${response.statusCode}');
      }
    } catch (e) {
      print(e);
      return [];
    }
  }

  Future<List<dynamic>> fetchParadas(String linha) async {
    try {
      await authenticate();
      final response = await http.get(
        Uri.parse('$baseUrl/Parada/BuscarParadasPorLinha?codigoLinha=$linha'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );
      if (response.statusCode == 200) {
        final List<dynamic> data = jsonDecode(response.body);
        return data;
      } else {
        throw Exception('Falha ao carregar paradas: ${response.statusCode}');
      }
    } catch (e) {
      print(e);
      return [];
    }
  }

  Future<Map<String, dynamic>> fetchLinhaInfo(String termo) async {
    try {
      await authenticate();
      final response = await http.get(
        Uri.parse('$baseUrl/Previsao/Linha?codigoLinha=$termo'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );
      if (response.statusCode == 200) {
        final Map<String, dynamic> data = jsonDecode(response.body);

        return data;
      } else {
        throw Exception(
            'Falha ao carregar dados da linha: ${response.statusCode}');
      }
    } catch (e) {
      print(e);
      return {};
    }
  }

  Future<Map<String, dynamic>> fetchPrevisao(String linha) async {
    try {
      await authenticate();
      final response = await http.get(
        Uri.parse('$baseUrl/Previsao/Linha?codigoLinha=$linha'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
      );
      if (response.statusCode == 200) {
        final Map<String, dynamic> data = jsonDecode(response.body);
        return data; // Retorne o mapa completo
      } else {
        throw Exception('Falha ao carregar previsões: ${response.statusCode}');
      }
    } catch (e) {
      print(e);
      return {};
    }
  }
}
