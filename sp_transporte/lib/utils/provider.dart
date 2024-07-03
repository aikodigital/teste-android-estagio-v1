import 'package:flutter/material.dart';

class LinhaProvider extends ChangeNotifier {
  String _linha = '1';

  String get linha => _linha;

  void setLinha(String value) {
    _linha = value;
    notifyListeners();
    print(_linha);
  }
}
