import 'package:http/http.dart' as http;
import 'package:olho_vivo_sp/util/environment.dart';

import '../models/hall_model.dart';

class ApiService {
  String? _sessionToken;

  Future<Null> authenticate() async {
    final response = await http.post(
      Uri.parse(
        '${Environment.base_url}/${Environment.auth_endpoint}?token=${Environment.token}',
      ),
    );

    if (response.statusCode == 200) {
      _sessionToken = response.body;
    } else {
      throw Exception(Environment.failed_auth_msg);
    }
  }
}
