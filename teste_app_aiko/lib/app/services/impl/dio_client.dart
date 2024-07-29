import 'package:dio/dio.dart';
import 'package:flutter/material.dart';

import '../interfaces/http_client.dart';

class DioSevice implements IHttpClient {
  final Dio dio;

  DioSevice(this.dio);

  @override
  Future get(String endPoint, {Map<String, dynamic>? queryParameters}) async {
    try {
      final response =
          await dio.get(endPoint, queryParameters: queryParameters);
      return response.data;
    } on DioException catch (e) {
      throw e.response?.statusCode ?? 404;
    }
  }

  @override
  Future post(String endPoint, dynamic data) async {
    try {
      final response = await dio.post(endPoint, data: data);
      return response.data;
    } on DioException catch (e) {
      debugPrint(e.response?.statusCode.toString());
      throw e.response?.statusCode ?? 404;
    }
  }

  @override
  Future uptade(String endPoint,
      {required String id, required dynamic data}) async {
    try {
      final response = await dio.put('$endPoint/$id', data: data);
      return response.data;
    } on DioException catch (e) {
      debugPrint(e.response?.statusCode.toString());
      throw e.response?.statusCode ?? 404;
    }
  }

  @override
  Future delete(String endPoint, {required String id}) async {
    try {
      final response = await dio.delete('$endPoint/$id');
      return response.data;
    } on DioException catch (e) {
      debugPrint(e.response?.statusCode.toString());
      throw e.response?.statusCode ?? 404;
    }
  }
}
