import 'dart:convert';

import 'package:dio/dio.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:teste_app_aiko/app/models/lines_maker_model.dart';
import 'package:teste_app_aiko/app/repositories/bus_markers_repository.dart';
import 'package:teste_app_aiko/app/services/impl/dio_client.dart';

void main() {
  final client = DioSevice(Dio(BaseOptions(
      baseUrl: 'https://aiko-olhovivo-proxy.aikodigital.io/',
      queryParameters: {
        'token':
            '5fdcf2d7ce111ed9592d7053e4e83d7951558b933e43a264b519536b0e406c59'
      })));
  final busMarkersRepository = BusMarkersRepository(client);

  test(
    'Deve fazer o parse correto para objetos do Dart',
    () async {
      final jsonD = jsonDecode(testeData);
      final mapped = (jsonD['l'] as List).map(LinesMakerModel.fromMap).toList();
      // print(mapped);
      expect(mapped, hasLength(equals(1)));
      expect(mapped[0].veiculos, isNotEmpty);
      expect(mapped[0].veiculos, hasLength(equals(mapped[0].qtdVeiculos)));
    },
  );

  test(
    'Deve buscar a posição dos veículos e armazenar em destinationMakers',
    () async {
      await busMarkersRepository.fetchMarkers();
      expect(busMarkersRepository.markers, isNotEmpty);
    },
  );
}

String testeData = """

{
    "hr": "20:31",
    "l": [
        {
            "c": "6726-10",
            "cl": 1193,
            "sl": 1,
            "lt0": "JD. GAIVOTAS   ",
            "lt1": "TERM. GRAJAÚ",
            "qv": 5,
            "vs": [
                {
                    "p": 66042,
                    "a": true,
                    "ta": "2024-07-28T23:31:25Z",
                    "py": -23.7563975,
                    "px": -46.6678165,
                    "sv": null,
                    "is": null
                },
                {
                    "p": 66660,
                    "a": true,
                    "ta": "2024-07-28T23:31:39Z",
                    "py": -23.7425605,
                    "px": -46.669598,
                    "sv": null,
                    "is": null
                },
                {
                    "p": 66253,
                    "a": true,
                    "ta": "2024-07-28T23:31:09Z",
                    "py": -23.736596499999997,
                    "px": -46.6971045,
                    "sv": null,
                    "is": null
                },
                {
                    "p": 66141,
                    "a": true,
                    "ta": "2024-07-28T23:31:25Z",
                    "py": -23.736596499999997,
                    "px": -46.6971045,
                    "sv": null,
                    "is": null
                },
                {
                    "p": 66142,
                    "a": true,
                    "ta": "2024-07-28T23:31:42Z",
                    "py": -23.735712499999998,
                    "px": -46.69611125,
                    "sv": null,
                    "is": null
                }
            ]
        }
    ]
}
""";
