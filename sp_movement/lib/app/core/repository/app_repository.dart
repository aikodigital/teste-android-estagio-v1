import 'dart:io';

import 'package:cookie_jar/cookie_jar.dart';
import 'package:dio/dio.dart';
import 'package:dio_cookie_manager/dio_cookie_manager.dart';
import 'package:sp_movement/app/core/repository/app_exception.dart';

class AppRepository {
  static String baseUrl = "https://api.olhovivo.sptrans.com.br/v2.1";
  static String token = "69b774bebaa53d28467063bb4b25e52eaa95257a1f81c724f37cbfdb283280c9";

  static Dio? _dio;
  static CookieJar cookieJar = CookieJar();

  static Dio? getDio() {
    _dio ??= Dio();

    _dio!.interceptors.add(CookieManager(cookieJar));
    return _dio;
  }

  static dynamic returnResponse(Response response) {
    switch (response.statusCode) {
      case 200:
      case 201:
      case 400:
      case 422:
        return response;
      case 415:
        throw BadRequestException(response.toString());
      case 401:
      case 403:
        throw UnauthorisedException(response.data.toString());
      case 404:
        throw UnauthorisedException(response.data.toString());
      case 500:
        throw BadRequestException(response.toString());
      default:
        throw FetchDataException('Error occured while communication with server'
            ' with status code : ${response.statusCode}');
    }
  }

  static Options getOptionsHttpSSL(
      String type,
      String? authcode, {
        String? length,
      }) {
    return Options(
        sendTimeout: const Duration(seconds: 20),
        followRedirects: false,
        contentType: type,
        headers: {
          HttpHeaders.authorizationHeader: authcode,
          if (length != null) HttpHeaders.contentLengthHeader: length
        },
        validateStatus: (status) {
          return status! < 500;
        });
  }
}
