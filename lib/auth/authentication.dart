import 'package:dio/dio.dart';
import 'package:flutter_config/flutter_config.dart';
import 'package:get/get.dart';

class Authentication extends GetxController {
  var url = "http://api.olhovivo.sptrans.com.br/v2.1";
  var token = FlutterConfig.get('SPTRANS_API_KEY');

  late final Dio _dio;

  var userIsAuthenticated = false.obs;

  static Authentication get to => Get.find<Authentication>();

  @override
  void onInit() {
    super.onInit();
    authenticate();
  }

  Authentication() {
    _dio = Dio(
      BaseOptions(
        connectTimeout: const Duration(milliseconds: 5000),
      ),
    );
  }

  authenticate() async {
    try {
      var response = await _dio.post(
        '$url/Login/Autenticar?token=$token',
      );
       if (response.statusCode == 200) {
        userIsAuthenticated.value = response.data;
      }
    } on DioException {
      //Get.snackbar('Erro', 'Erro ao realizar a autenticação');
    }
  }
}
