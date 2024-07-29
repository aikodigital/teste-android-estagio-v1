// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility in the flutter_test package. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.
import 'package:flutter_test/flutter_test.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';
import 'package:sp_movement/app/modules/estimated_arrival/repository/bus_stops_repository.dart';

void main() {
  test('deve retornar verdadeiro para autenticacao', () async {
    bool retorno = await AuthRepository.getAuthApp();
    expect(true, retorno);
  });

  test('deve retornar verdadeiro uma lista de paradas', () async {
    dynamic retorno = await BusStopRepository.searchByStops('Afonso');
    expect(true, retorno.isNotEmpty);
  });

  test('deve retornar verdadeiro uma lista de paradas por linha', () async {
    dynamic retorno = await BusStopRepository.searchByCodeLine(516);
    expect(true, retorno.isNotEmpty);
  });

  test('deve retornar verdadeiro uma lista de paradas por corredor', () async {
    dynamic retorno = await BusStopRepository.SearchByCodeRunner(8);
    expect(true, retorno.isNotEmpty);
  });

  test('deve retornar verdadeiro uma lista de paradas', () async {
    dynamic retorno = await BusStopRepository.searchListStop();
    expect(true, retorno.isNotEmpty);
  });

}
