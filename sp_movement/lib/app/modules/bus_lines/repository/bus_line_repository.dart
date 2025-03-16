import 'package:sp_movement/app/modules/bus_lines/models/bus_line_model.dart';
import '../../../core/repository/app_repository.dart';

class BusLineRepository {
  static String endpoint = "/Linha";

  static Future<List<BusLineModel>> searchLine(String termsSearch) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get(
        '${AppRepository.baseUrl}$endpoint/Buscar?termosBusca=$termsSearch');
    List<BusLineModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
        list.add(BusLineModel.fromJson(item));
      });
    }
    return list;
  }

  static Future<List<BusLineModel>> searchLineByDirect(
      String termsSearch, int direct) async {
    dynamic response = await AppRepository.getDio()!.get(
        '${AppRepository.baseUrl}$endpoint/BuscarLinhaSentido?termosBusca=$termsSearch&sentido=$direct');
    List<BusLineModel> list = [];
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);
      responseJson.data.forEach((item) {
        list.add(BusLineModel.fromJson(item));
      });
    }
    return list;
  }
}
