import 'package:dio/dio.dart';

class AppRepository {
  static String baseUrl = "http://api.olhovivo.sptrans.com.br/v2.1";
  static String token =
      "69b774bebaa53d28467063bb4b25e52eaa95257a1f81c724f37cbfdb283280c9";

  static Dio? _dio = null;

  static Dio? getDio() {
    if (_dio == null) _dio = Dio();
    return _dio;
  }
}
