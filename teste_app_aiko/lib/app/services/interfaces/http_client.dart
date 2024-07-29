abstract interface class IHttpClient {
  Future<dynamic> get(String endPoint, {Map<String, dynamic>? queryParameters});

  Future<dynamic> post(String endPoint, dynamic data);

  Future<dynamic> uptade(String endPoint,
      {required String id, required dynamic data});

  Future<dynamic> delete(String endPoint, {required String id});
}
