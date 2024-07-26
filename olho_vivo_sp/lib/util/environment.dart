abstract class Environment {
  static const failed_auth_msg = 'Falha ao realizar autenticação.';
  static const failed_get_halls_msg = 'Falha ao obter corredores.';

  static const base_url = 'https://aiko-olhovivo-proxy.aikodigital.io';
  static const token =
      'aa4bbfd91dd0321d0f886554e9bcccfbbcc00341d85b3f5bff4b563ce93efc30';
  static const auth_endpoint = '/Login/Autenticar';
  static const halls_endpoint = '/Corredor';
}
