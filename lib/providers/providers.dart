// import 'package:flutter/material.dart';
// import '../models/models.dart';
// import '../services/api_service.dart';

// class LinhaProvider with ChangeNotifier {
//   List<Linha> _linhas = [];

//   List<Linha> get linhas => _linhas;

//   Future<void> fetchLinhas(String termosBusca) async {
//     final apiService = ApiService();
//     final linhasData = await apiService.getLinhas(termosBusca);
//     _linhas = linhasData.map((linha) => Linha.fromJson(linha)).toList();
//     notifyListeners();
//   }
// }

// class ParadaProvider with ChangeNotifier {
//   List<Parada> _paradas = [];

//   List<Parada> get paradas => _paradas;

//   Future<void> fetchParadas(String termosBusca) async {
//     final apiService = ApiService();
//     final paradasData = await apiService.getParadas(termosBusca);
//     _paradas = paradasData.map((parada) => Parada.fromJson(parada)).toList();
//     notifyListeners();
//   }
// }

// class VeiculoProvider with ChangeNotifier {
//   List<Veiculo> _veiculos = [];

//   List<Veiculo> get veiculos => _veiculos;

//   Future<void> fetchVeiculos() async {
//     final apiService = ApiService();
//     final veiculosData = await apiService.getPosicaoVeiculos();
//     _veiculos = veiculosData['vs'].map((veiculo) => Veiculo.fromJson(veiculo)).toList();
//     notifyListeners();
//   }
// }