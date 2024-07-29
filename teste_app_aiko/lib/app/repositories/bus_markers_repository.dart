import '../models/lines_maker_model.dart';
import '../services/interfaces/http_client.dart';

class BusMarkersRepository {
  final String _apiEndpoint = '/Posicao';
  final IHttpClient client;
  final List<LinesMakerModel> _markers = [];

  BusMarkersRepository(this.client);

  List<LinesMakerModel> get markers => [..._markers];

  Future fetchMarkers() async {
    final response = await client.get(_apiEndpoint);
    _markers.addAll((response['l'] as List).map(LinesMakerModel.fromMap));
  }
}
