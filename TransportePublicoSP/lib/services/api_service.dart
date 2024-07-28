import 'dart:convert';
import 'package:csv/csv.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:http/http.dart' as http;

class ApiService {
  final String baseUrl = 'https://aiko-olhovivo-proxy.aikodigital.io';

  Future<List<List<dynamic>>> loadCSV(String filePath) async {
    final input = await rootBundle.loadString(filePath);
    return const CsvToListConverter().convert(input);
  }

  Future<List<Map<String, dynamic>>> searchStops(String query) async {
    List<Map<String, dynamic>> stops = [];
    try {
      List<List<dynamic>> stopsData = await loadCSV('assets/gtfs/stops.txt');
      for (var row in stopsData.skip(1)) {
        if (row.length >= 5) {
          String stopName = row[1].toString().toLowerCase();
          if (stopName.contains(query.toLowerCase())) {
            stops.add({
              'stop_id': row[0].toString(),
              'stop_name': row[1].toString(),
              'stop_lat': double.parse(row[3].toString()),
              'stop_lon': double.parse(row[4].toString()),
            });
          }
        }
      }
    } catch (e) {
      if (kDebugMode) {
        print("Error searching stops data: $e");
      }
    }
    return stops;
  }

  Future<List<Map<String, dynamic>>> fetchBusLines(String stopName) async {
    final cleanStopName =
        stopName.split(',')[0]; // Retira o número da rua, se houver
    final response = await http
        .get(Uri.parse('$baseUrl/linha/Buscar?termosBusca=$cleanStopName'));

    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item as Map<String, dynamic>).toList();
    } else {
      throw Exception('Failed to load bus lines');
    }
  }

  Future<List<Map<String, dynamic>>> fetchBusArrivalTimes(int lineCode) async {
    final response = await http.get(
      Uri.parse('$baseUrl/Previsao/Linha?codigoLinha=$lineCode'),
    );

    if (response.statusCode == 200) {
      Map<String, dynamic> data = jsonDecode(response.body);
      return data['ps']
          .map<Map<String, dynamic>>((item) => item as Map<String, dynamic>)
          .toList();
    } else {
      throw Exception('Failed to load bus arrival times');
    }
  }

  Future<List<Map<String, dynamic>>> searchLines(String query) async {
    final response =
        await http.get(Uri.parse('$baseUrl/Linha/Buscar?termosBusca=$query'));

    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item as Map<String, dynamic>).toList();
    } else {
      throw Exception('Failed to load lines');
    }
  }

  Future<List<Map<String, dynamic>>> fetchBusLinesStops(String stopId) async {
    final response = await http
        .get(Uri.parse('$baseUrl/Previsao/Parada?codigoParada=$stopId'));

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Map<String, dynamic>> busLines = [];

      if (data['p'] != null && data['p']['l'] != null) {
        for (var line in data['p']['l']) {
          busLines.add({
            'code': line['c'],
            'destination': line['lt0'],
            'origin': line['lt1'],
            'vehicles': line['vs'],
            'lineCode': line['cl'],
          });
        }
      }

      return busLines;
    } else {
      throw Exception('Failed to load bus lines');
    }
  }

  Future<List<Map<String, dynamic>>> fetchBusPositions(String lineCode) async {
    final response = await http
        .get(Uri.parse('$baseUrl/Posicao/Linha?codigoLinha=$lineCode'));

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Map<String, dynamic>> busPositions = [];

      if (data['vs'] != null) {
        for (var bus in data['vs']) {
          busPositions.add({
            'prefix': bus['p'],
            'accessible': bus['a'],
            'timestamp': bus['ta'],
            'latitude': bus['py'],
            'longitude': bus['px'],
          });
        }
      }

      return busPositions;
    } else {
      throw Exception('Failed to load bus positions');
    }
  }

  Future<List<Map<String, dynamic>>> fetchBusArrivalTimesForStop(
      String stopId) async {
    final response = await http
        .get(Uri.parse('$baseUrl/Previsao/Parada?codigoParada=$stopId'));

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      List<Map<String, dynamic>> arrivalTimes = [];

      if (data['p'] != null && data['p']['l'] != null) {
        for (var line in data['p']['l']) {
          for (var vehicle in line['vs']) {
            arrivalTimes.add({
              'lineCode': line['cl'],
              'lineName': line['lt'],
              'destination': line['lt0'],
              'arrivalTime': vehicle['t'],
              'accessible': vehicle['a'],
            });
          }
        }
      }

      return arrivalTimes;
    } else {
      throw Exception('Failed to load bus arrival times');
    }
  }

  Future<List<Map<String, dynamic>>> fetchBusLinesByDirection(
      String numberLine, int direction) async {
    final response = await http.get(
      Uri.parse(
          '$baseUrl/Linha/BuscarLinhaSentido?termosBusca=$numberLine&sentido=$direction'),
    );

    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((item) => item as Map<String, dynamic>).toList();
    } else {
      throw Exception('Failed to load bus lines by direction');
    }
  }
}
