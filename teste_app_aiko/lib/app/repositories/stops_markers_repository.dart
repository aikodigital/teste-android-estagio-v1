import '../models/stops_markers_model.dart';
import '../services/interfaces/http_client.dart';

class StopsMarkersRepository {
  final String _apiEndpoint = '/Parada/Buscar';
  final IHttpClient client;
  final List<StopsMarkerModel> _markers = [];

  StopsMarkersRepository(this.client);

  List<StopsMarkerModel> get markers => [..._markers];

  Future fetchMarkers() async {
    if (_markers.isNotEmpty) return;
    final response =
        await client.get(_apiEndpoint, queryParameters: {'termosBusca': '%'});
    _markers.addAll((response as List).map(StopsMarkerModel.fromMap));
  }

  /* void addDestinationMarker(MarkerModel marker) =>
      _destinationMarkers.add(marker);

  void addDestinationMarkers(List<MarkerModel> markers) =>
      _destinationMarkers.addAll(markers); */
}
