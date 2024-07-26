import 'package:dio/dio.dart';
import 'package:sp_movement/app/core/repository/app_repository.dart';
import 'package:sp_movement/app/modules/bus_route/models/vehiclelocation_model.dart';

class BusRouteRepository {
  static String endpoint = "/Posicao";

  static Future<List<VehicleLocationModel>> getPosition() async {
    dynamic response =
        await AppRepository.getDio()!.get(AppRepository.baseUrl + endpoint,
            options: AppRepository.getOptionsHttpSSL(
              Headers.jsonContentType,
              AppRepository.token,
            ));
    print(response.data);
    List<VehicleLocationModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data['l'].forEach((v) {
        list.add(VehicleLocationModel.fromJson(v));
      });
      return list;
    } else {
      return [];
    }
  }

  static void getRoute() {}

  static void getGarage() {}
}
