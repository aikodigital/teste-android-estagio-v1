// This is a basic Flutter widget test.
//
// To perform an interaction with a widget in your test, use the WidgetTester
// utility in the flutter_test package. For example, you can send tap and scroll
// gestures. You can also use WidgetTester to find child widgets in the widget
// tree, read text, and verify that the values of widget properties are correct.
import 'package:flutter_test/flutter_test.dart';
import 'package:sp_movement/app/modules/auth/auth_repository.dart';
import 'package:sp_movement/app/modules/bus_route/repositories/bus_route_repository.dart';
import 'package:sp_movement/app/modules/bus_stops/repository/bus_stops_repository.dart';

void main() {
  test('deve retornar verdadeiro para autenticacao', () async {
    bool retorno = await AuthRepository.getAuthApp();
    expect(true, retorno);
  });

  test('deve retornar verdadeiro uma lista de posiçoes', () async {
    dynamic retorno = await BusRouteRepository.getPosition();
    expect(true, retorno.isNotEmpty);
  });

  test('deve retornar verdadeiro uma lista de paradas', () async {
    dynamic retorno = await BusStopRepository.searchStops('Afonso');
    expect(true, retorno.isNotEmpty);
  });

}
