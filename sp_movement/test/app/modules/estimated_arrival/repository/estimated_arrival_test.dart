import 'package:flutter_test/flutter_test.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';
import 'package:sp_movement/app/modules/estimated_arrival/repository/estimated_arrival_repository.dart';

void main() {
  test('deve retornar verdadeiro para autenticacao', () async {
    bool retorno = await AuthRepository.getAuthApp();
    expect(true, retorno);
  });

  test('deve retornar verdadeiro uma lista de previsão de chegada', () async {
    dynamic retorno = await EstimatedArrivalRepository.searchEstimatedArrival(340015748);
    expect(true, retorno != null);
  });

}
