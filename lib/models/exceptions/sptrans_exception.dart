class SpTransException implements Exception {
  final String message;
  final StackTrace? stackTrace;
  SpTransException(this.message, {this.stackTrace});

  @override
  String toString() {
    return message;
  }
}
