import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';

part 'auth_store.g.dart';

class AuthStore = _AuthStore with _$AuthStore;

abstract class _AuthStore with Store {
  @action
  Future<void> authenticate() async {
    await AuthRepository.getAuthApp();
  }
}
