import 'package:flutter_modular/flutter_modular.dart';
import 'package:mobx/mobx.dart';
import '../auth/auth_store.dart';

part "splash_store.g.dart";

class SplashStore = _SplashStore with _$SplashStore;

abstract class _SplashStore with Store {
  // _HomeStore() {
  //   authenticate();
  // }

  Future<void> authenticate() async {
    final authStore = Modular.get<AuthStore>();
    await authStore.authenticate();
  }
}
