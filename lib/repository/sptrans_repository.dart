import 'package:dio/dio.dart';
import 'package:flutter_config/flutter_config.dart';
import 'package:get/get.dart';
import 'package:mova_sp/auth/authentication.dart';
import 'package:mova_sp/models/bus_line.dart';
import 'package:mova_sp/models/bus_line_detail.dart';
import 'package:mova_sp/models/bus_stop.dart';
import 'package:mova_sp/models/bus_stop_detail.dart';

class SpTransRepository {
  var url = "https://aiko-olhovivo-proxy.aikodigital.io";
  var token = FlutterConfig.get('SPTRANS_API_KEY');
  late final Dio _dio;

  SpTransRepository() {
    _dio = Dio(BaseOptions(
      connectTimeout: const Duration(milliseconds: 5000),
    ));
  }

  static SpTransRepository get to => Get.find<SpTransRepository>();

  Future<List<BusLine>> getAllBusLine() async {
    try {
      Authentication.to.authenticate();
      var urlRequest = "$url/Linha/Buscar?termosBusca=all&token=$token";
      var response = await _dio.get(urlRequest);
      var list = response.data as List;
      return list.map((json) => BusLine.fromJson(json)).toList();
    } on DioException catch (e) {
      Get.snackbar('Erro do Servidor', e.response!.data.toString());
    } catch (e) {
      Get.snackbar('Erro', e.toString());
    }
    return [];
  }

  Future<List<BusStop>> getAllBusStop() async {
    Authentication.to.authenticate();
    try {
      var urlRequest = "$url/Parada/Buscar?termosBusca=&token=$token";
      var response = await _dio.get(urlRequest);
      var list = response.data as List;
      return list.map((json) => BusStop.fromJson(json)).toList();
    } on DioException catch (e) {
      Get.snackbar('Erro do Servidor', e.response!.data.toString());
    } catch (e) {
      Get.snackbar('Erro', e.toString());
    }
    return [];
  }

  Future<List<BusStopDetail>> getBusStopDetail(int busStopCode) async {
    Authentication.to.authenticate();
    try {
      var urlRequest =
          "$url/Previsao/Parada?codigoParada=$busStopCode&token=$token";
      var response = await _dio.get(urlRequest);
      var data = response.data;
      var linesJson = data['p']['l'] as List<dynamic>? ?? [];
      return linesJson
          .map((json) => BusStopDetail.fromJson(json as Map<String, dynamic>))
          .toList();
    } on DioException catch (e) {
      Get.snackbar('Erro do Servidor', e.response!.data.toString());
    } catch (e) {
      Get.snackbar('Erro', e.toString());
    }
    return [];
  }

  Future<List<BusLineDetail>> getArrivalTimeByBusLine(int busStopCode) async {
    Authentication.to.authenticate();
    try {
      var urlRequest =
          "$url/Previsao/Linha?codigoLinha=$busStopCode&token=$token";
      var response = await _dio.get(urlRequest);
      var data = response.data;
      var lineJson = data['ps'] as List<dynamic>? ?? [];
      return lineJson
          .map((json) => BusLineDetail.fromJson(json as Map<String, dynamic>))
          .toList();
    } on DioException catch (e) {
      Get.snackbar('Erro do Servidor', e.response!.data.toString());
    } catch (e) {
      Get.snackbar('Erro', e.toString());
    }
    return [];
  }
}
