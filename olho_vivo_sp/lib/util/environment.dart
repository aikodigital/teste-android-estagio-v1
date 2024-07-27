abstract class Environment {
  static const String failed_auth_msg = 'Falha ao realizar autenticação.';
  static const String failed_get_halls_msg = 'Falha ao obter corredores.';
  static const String failed_get_arrival_forecast =
      'Falha ao obter previsões de chegada.';
  static const String failed_get_bus_stops =
      'Falha ao obter paradas de ônibus.';

  static const String base_url = 'https://aiko-olhovivo-proxy.aikodigital.io';
  static const String token =
      'aa4bbfd91dd0321d0f886554e9bcccfbbcc00341d85b3f5bff4b563ce93efc30';
  static const String auth_endpoint = '/Login/Autenticar';
  static const String halls_endpoint = '/Corredor';
  static const String bus_stops_endpoint = '/Parada/Buscar';
  static const String bus_stops_by_hall_endpoint =
      '/Parada/BuscarParadasPorCorredor';
  static const String arrival_forecast_by_bus_stop_endpoint =
      '/Previsao/Parada';
}
