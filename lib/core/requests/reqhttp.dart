import 'dart:convert';

import 'package:http/http.dart' as http;

class ReqHttp {
  static const String _baseurl = "https://api.olhovivo.sptrans.com.br/v2.1";
  static String _cookie = "";
  void setCookie(String rawCookie) {
    _cookie = rawCookie;
  }

  Future<http.Response> get(String url) async {
    return await http
        .get(Uri.parse("$_baseurl/$url"), headers: {"cookie": _cookie});
  }

  Future<http.Response> post(String url, {dynamic data}) async {
    return await http.post(Uri.parse("$_baseurl/$url"),
        body: data == null ? null : jsonEncode(data),
        headers: {"cookie": _cookie});
  }
}
