import 'package:sp_movement/app/core/repository/app_repository.dart';

class AuthRepository {
  static String endpoint = "/Login/Autenticar";

  static Future<bool> getAuthApp() async {
    dynamic response = await AppRepository.getDio()!.post(
      '${AppRepository.baseUrl}$endpoint?token=${AppRepository.token}',
      data: null,
    );
    print(response.data);
    return response.data as bool;
  }
}
