class Position {
  final double latitude;
  final double longitude;

  Position(this.latitude, this.longitude);

  factory Position.fromJson(dynamic data) {
    return Position(
      data['py'] as double,
      data['px'] as double,
    );
  }
}
