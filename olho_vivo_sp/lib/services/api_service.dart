import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:olho_vivo_sp/util/environment.dart';

import '../models/hall_model.dart';

class ApiService extends ChangeNotifier {
  String? _sessionToken;

  Future<Null> authenticate() async {
    try {
      final response = await http.post(
        Uri.parse(
          '${Environment.base_url}${Environment.auth_endpoint}?token=${Environment.token}',
        ),
      );

      if (response.statusCode >= HttpStatus.ok &&
          response.statusCode < HttpStatus.multipleChoices) {
        _sessionToken = response.body;
      } else {
        throw Exception(Environment.failed_auth_msg);
      }
    } catch (e) {
      rethrow;
    }
  }

  Future<List<HallModel>> getHalls() async {
    try {
      if (_sessionToken == null) {
        await authenticate();
      }

      final response = await http.get(
        Uri.parse(
          '${Environment.base_url}${Environment.halls_endpoint}',
        ),
        headers: {
          'Authorization': 'Bearer $_sessionToken',
        },
      );

      if (response.statusCode >= HttpStatus.ok &&
          response.statusCode < HttpStatus.multipleChoices) {
        List<dynamic> data = jsonDecode(response.body);

        return data
            .map(
              (hall) => HallModel.fromMap(hall),
            )
            .toList();
      } else {
        throw Exception(Environment.failed_get_halls_msg);
      }
    } catch (e) {
      rethrow;
    }
  }
}
