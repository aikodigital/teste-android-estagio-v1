
import '../../../core/repository/app_repository.dart';
import '../models/bus_stops_model.dart';

class BusStopRepository {
  static String endpoint = "/Parada";

  static Future<List<BusStopsModel>> searchByStops(String termSearch ) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get('${AppRepository.baseUrl}$endpoint/Buscar?termosBusca=$termSearch');
    List<BusStopsModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
          list.add(BusStopsModel.fromJson(item));
        });
      }
      return list;
    }

  static Future<List<BusStopsModel>> searchByCodeLine(int codeLine ) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get('${AppRepository.baseUrl}$endpoint/BuscarParadasPorLinha?codigoLinha=$codeLine');
    List<BusStopsModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
        list.add(BusStopsModel.fromJson(item));
      });
    }
    return list;
  }
  static Future<List<BusStopsModel>> SearchByCodeRunner(int codeRunner ) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get('${AppRepository.baseUrl}$endpoint/BuscarParadasPorCorredor?codigoCorredor=$codeRunner');
    List<BusStopsModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
        list.add(BusStopsModel.fromJson(item));
      });
    }
    return list;
  }
  
  
  static Future<List<BusStopsModel>> searchListStop({String? term}) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get(
        '${AppRepository.baseUrl}$endpoint/Buscar?termosBusca=${term??''}');
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      List<BusStopsModel> list = [];
      for (var item in responseJson.data) {
        list.add(BusStopsModel.fromJson(item));
      }
      return list;
    }
    return [];
  }



}
