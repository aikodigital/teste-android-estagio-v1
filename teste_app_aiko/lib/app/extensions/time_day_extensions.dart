import 'package:flutter/material.dart';

extension TimeDayExtension on TimeOfDay {
  TimeOfDay parseTimeFromString(String data) {
    final hour = int.parse(data.substring(0, 2));
    final minute = int.parse(data.substring(3));
    return TimeOfDay(hour: hour, minute: minute);
  }
}
