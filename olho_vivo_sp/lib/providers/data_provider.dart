import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';

class DataProvider extends ChangeNotifier {
  final ApiService apiService;

  DataProvider({required this.apiService});

  Future<Null> authenticate() async => await apiService.authenticate();

  Future<List<HallModel>> getHalls() async => await apiService.getHalls();
}
