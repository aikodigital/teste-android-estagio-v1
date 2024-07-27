abstract class Environment {
  static const String failed_auth_msg = 'Falha ao realizar autenticação.';
  static const String failed_get_halls_msg = 'Falha ao obter corredores.';

  static const String base_url = 'https://aiko-olhovivo-proxy.aikodigital.io';
  static const String token =
      'aa4bbfd91dd0321d0f886554e9bcccfbbcc00341d85b3f5bff4b563ce93efc30';
  static const String auth_endpoint = '/Login/Autenticar';
  static const String halls_endpoint = '/Corredor';
  static const String bus_stops_endpoint = '/Parada/Buscar';
  static const String bus_stops_by_hall_endpoint =
      '/Parada/BuscarParadasPorCorredor';
}
