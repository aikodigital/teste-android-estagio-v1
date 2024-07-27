
import '../../../core/repository/app_repository.dart';
import '../models/bus_stops_model.dart';

class BusStopRepository {
  static String endpoint = "/Parada";

  static Future<List<BusStopsModel>> searchStops(String termSearch ) async {
    final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    print('Cookies após autenticação: $cookies');
    dynamic response = await AppRepository.getDio()!.get('${AppRepository.baseUrl}$endpoint/Buscar?termosBusca=$termSearch');
    print(response.data);
    List<BusStopsModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
          list.add(BusStopsModel.fromJson(item));
        });
      }
      return list;
    }
}
