import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:csv/csv.dart';
import 'dart:math' as math;

class GTFSLoader {
  Future<List<List<dynamic>>> loadCSV(String filePath) async {
    final input = await rootBundle.loadString(filePath);
    return const CsvToListConverter().convert(input);
  }

  Future<List<Map<String, dynamic>>> loadStopsInRegion(
      double minLat, double maxLat, double minLon, double maxLon) async {
    List<Map<String, dynamic>> stops = [];
    try {
      List<List<dynamic>> stopsData = await loadCSV('assets/gtfs/stops.txt');
      for (var row in stopsData.skip(1)) {
        if (row.length >= 5) {
          double lat = double.parse(row[3].toString());
          double lon = double.parse(row[4].toString());
          if (lat >= minLat &&
              lat <= maxLat &&
              lon >= minLon &&
              lon <= maxLon) {
            stops.add({
              'stop_id': row[0].toString(),
              'stop_name': row[1].toString(),
              'stop_lat': lat,
              'stop_lon': lon,
            });
          }
        }
      }
    } catch (e) {
      if (kDebugMode) {
        print("Error loading stops data: $e");
      }
    }
    return stops;
  }

  double _calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    const double radiusOfEarth = 6371000;
    double lat1Rad = lat1 * math.pi / 180;
    double lon1Rad = lon1 * math.pi / 180;
    double lat2Rad = lat2 * math.pi / 180;
    double lon2Rad = lon2 * math.pi / 180;

    double dLat = lat2Rad - lat1Rad;
    double dLon = lon2Rad - lon1Rad;

    double a = math.sin(dLat / 2) * math.sin(dLat / 2) +
        math.cos(lat1Rad) * math.cos(lat2Rad) *
            math.sin(dLon / 2) * math.sin(dLon / 2);

    double c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a));

    return radiusOfEarth * c;
  }

  Future<List<Map<String, dynamic>>> loadStopsNearby(
      double userLat, double userLon, double radius) async {
    List<Map<String, dynamic>> stops = [];
    try {
      List<List<dynamic>> stopsData = await loadCSV('assets/gtfs/stops.txt');
      for (var row in stopsData.skip(1)) {
        // Skip the header row
        if (row.length >= 5) {
          double lat = double.parse(row[3].toString());
          double lon = double.parse(row[4].toString());
          double distance = _calculateDistance(userLat, userLon, lat, lon);
          if (distance <= radius) {
            stops.add({
              'stop_id': row[0].toString(),
              'stop_name': row[1].toString(),
              'stop_lat': lat,
              'stop_lon': lon,
            });
          }
        }
      }
    } catch (e) {
      if (kDebugMode) {
        print("Error loading stops data: $e");
      }
    }
    return stops;
  }
}
