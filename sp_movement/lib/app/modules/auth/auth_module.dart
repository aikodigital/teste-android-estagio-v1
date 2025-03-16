import 'package:flutter_modular/flutter_modular.dart';
import 'auth_store.dart';

class AuthModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => AuthStore());
  }

  @override
  void routes(r) {
  }
}
