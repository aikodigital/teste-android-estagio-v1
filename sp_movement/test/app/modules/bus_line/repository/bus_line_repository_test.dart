
import 'package:flutter_test/flutter_test.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';
import 'package:sp_movement/app/modules/bus_lines/repository/bus_line_repository.dart';



void main() {
  test('deve retornar verdadeiro para autenticacao', () async {
    bool retorno = await AuthRepository.getAuthApp();
    expect(true, retorno);
  });

  test('deve retornar verdadeiro uma lista de linhas', () async {
    dynamic retorno = await BusLineRepository.searchLine('8000');
    expect(true, retorno.isNotEmpty);
  });

  test('deve retornar verdadeiro uma lista de linhas por sentido', () async {
    dynamic retorno = await BusLineRepository.searchLineByDirect('8000', 1);
    expect(true, retorno.isNotEmpty);
  });

}
