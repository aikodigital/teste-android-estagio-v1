import 'localized_lines.dart';

class Stop {
  int code;
  String name;
  double latitude;
  double longitude;
  List<LocalizedLines> localizedLines;

  Stop({
    required this.code,
    required this.name,
    required this.latitude,
    required this.longitude,
    required this.localizedLines,
  });
}
