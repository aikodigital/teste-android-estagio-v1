import 'package:flutter_test/flutter_test.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';
import 'package:sp_movement/app/modules/vehicle_position/repositories/vehicle_position_repository.dart';

void main() {
  test('deve retornar verdadeiro para autenticacao', () async {
    bool retorno = await AuthRepository.getAuthApp();
    expect(true, retorno);
  });

  test('deve retornar verdadeiro uma lista de posiçoes', () async {
    dynamic retorno = await VehiclePositionRepository.getPosition();
    expect(true, retorno.isNotEmpty);
  });


}
