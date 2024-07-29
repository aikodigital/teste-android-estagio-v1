import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/splash/splash_store.dart';
import 'package:sp_movement/app/modules/splash/splash_view.dart';

class SplashModule extends Module {
  @override
  void binds(i) {
    i.addSingleton(SplashStore.new);
  }

  @override
  void routes(r) {
    r.child(Modular.initialRoute, child: (_) => const SplashView());
  }
}
