import '../../../core/repository/app_repository.dart';
import '../models/stop_model.dart';

class EstimatedArrivalRepository {
  static String endpoint = "/Previsao";

  static Future<StopModel?> searchEstimatedArrival(int codeStop) async {
    dynamic response = await AppRepository.getDio()!
        .get('${AppRepository.baseUrl}$endpoint/Parada?codigoParada=$codeStop');
    if (response != null) {
      dynamic responseJson = AppRepository.returnResponse(response);

      if(responseJson.data['p'] != null)
        return StopModel.fromJson(responseJson.data['p']);
    }
    return null;
  }
}
