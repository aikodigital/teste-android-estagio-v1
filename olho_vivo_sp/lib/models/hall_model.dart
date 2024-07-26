class HallModel {
  int code;
  String name;

  HallModel({required this.code, required this.name});

  HallModel.fromMap(Map<String, dynamic> res)
      : code = res['cc'],
        name = res['nc'];

  Map<String, Object> toMap() {
    return {
      'cc': code,
      'nc': name,
    };
  }
}
