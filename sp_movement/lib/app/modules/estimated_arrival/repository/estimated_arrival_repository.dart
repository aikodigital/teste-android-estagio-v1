import '../../../core/repository/app_repository.dart';
import '../models/stop_model.dart';

class EstimatedArrivalRepository {
  static String endpoint = "/Previsao";

  static Future<StopModel?> searchEstimatedArrival(
      int codeStop, int codeLine) async {
    // final cookies = await AppRepository.cookieJar.loadForRequest(Uri.parse(AppRepository.baseUrl));
    dynamic response = await AppRepository.getDio()!.get(
        '${AppRepository.baseUrl}$endpoint?codigoParada=$codeStop&codigoLinha=$codeLine');
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);

        return StopModel.fromJson(responseJson.data['p']);
    }
    return null;
  }
}
