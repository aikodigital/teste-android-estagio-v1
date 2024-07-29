import 'package:flutter/material.dart';

extension ScaffoldMessagens on ScaffoldMessengerState {
  void showSnackBarErro(String mensagem) {
    showSnackBar(SnackBar(
        backgroundColor: Colors.red,
        content: Text(
          mensagem,
          style: const TextStyle(color: Colors.white),
        )));
  }

  void showSnackBarSucesso(String mensagem) {
    showSnackBar(SnackBar(
        backgroundColor: Colors.green,
        content: Text(
          mensagem,
          style: const TextStyle(color: Colors.white),
        )));
  }

  void showSnackBarAviso(String mensagem) {
    showSnackBar(SnackBar(
        backgroundColor: const Color(0xFFFDA33C),
        content: Text(
          mensagem,
          style: const TextStyle(color: Colors.black),
        )));
  }
}
