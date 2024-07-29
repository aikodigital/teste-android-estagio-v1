import '../models/lines_maker_model.dart';
import '../services/interfaces/http_client.dart';

class PrevisaoChegadaRepository {
  final String _apiEndpoint = '/Previsao/Parada';
  final IHttpClient client;

  PrevisaoChegadaRepository(this.client);

  Future<List<LinesMakerModel>> fetchBusesByLine(int cod) async {
    final response =
        await client.get(_apiEndpoint, queryParameters: {'codigoParada': cod});
    if (response['p'] == null) return [];
    return (response['p']['l'] as List).map(LinesMakerModel.fromMap).toList();
  }
}
