// ignore_for_file: library_private_types_in_public_api

import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:transportepublicosp/screens/gtfs_loader.dart';
import 'package:transportepublicosp/services/api_service.dart';
import 'package:transportepublicosp/screens/bus_position_screen.dart';

class NearbyBusLinesWidget extends StatefulWidget {
  final double userLat;
  final double userLon;

  const NearbyBusLinesWidget({
    super.key,
    required this.userLat,
    required this.userLon,
  });

  @override
  _NearbyBusLinesWidgetState createState() => _NearbyBusLinesWidgetState();
}

class _NearbyBusLinesWidgetState extends State<NearbyBusLinesWidget> {
  List<Map<String, dynamic>> _busLines = [];
  bool _isLoading = true;
  Timer? _timer;

  @override
  void initState() {
    super.initState();
    _fetchNearbyBusLines();
    _startTimer();
  }

  @override
  void dispose() {
    _timer?.cancel();
    super.dispose();
  }

  void _startTimer() {
    _timer = Timer.periodic(const Duration(seconds: 15), (timer) {
      _fetchNearbyBusLines();
    });
  }

  Future<void> _fetchNearbyBusLines() async {
    try {
      final apiService = ApiService();
      // Assume a radius of 100 meters for nearby stops
      double radius = 100.0;
      List<Map<String, dynamic>> stops = await GTFSLoader().loadStopsNearby(
        widget.userLat,
        widget.userLon,
        radius,
      );

      List<Map<String, dynamic>> newBusLines = [];
      for (var stop in stops) {
        List<Map<String, dynamic>> lines =
            await apiService.fetchBusLinesStops(stop['stop_id']);
        for (var line in lines) {
          line['vehicles'] = (line['vehicles'] as List)
              .map((vehicle) => vehicle as Map<String, dynamic>)
              .toList();
        }
        newBusLines.addAll(lines);
      }

      if (!ListEquality().equals(_busLines, newBusLines)) {
        setState(() {
          _busLines = newBusLines;
        });
      }

      setState(() {
        _isLoading = false;
      });
    } catch (e) {
      if (kDebugMode) {
        print('Error fetching nearby bus lines: $e');
      }
      setState(() {
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return _isLoading
        ? const Center(child: CircularProgressIndicator())
        : Container(
            padding: const EdgeInsets.all(8.0),
            decoration: BoxDecoration(
              color: Colors.white,
              borderRadius: BorderRadius.circular(12.0),
              boxShadow: const [
                BoxShadow(
                  color: Colors.black26,
                  blurRadius: 6.0,
                  offset: Offset(0, 2),
                ),
              ],
            ),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: _busLines.isNotEmpty
                  ? _busLines.take(2).map((busLine) {
                      return ListTile(
                        leading: const Icon(Icons.directions_bus),
                        title: Text(busLine['code']),
                        subtitle: Text(
                            '${busLine['origin']} - ${busLine['destination']}'),
                        trailing: Text(
                            'Próximo: ${busLine['vehicles'][0]['t']}'), // Exibe o horário previsto de chegada
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => BusPositionScreen(
                                lineCode: busLine['lineCode'].toString(),
                                busPositions:
                                    (busLine['vehicles'] as List<dynamic>)
                                        .map((e) => e as Map<String, dynamic>)
                                        .toList(),
                                busLine: busLine,
                              ),
                            ),
                          );
                        },
                      );
                    }).toList()
                  : [const Text('Nenhuma linha de ônibus próxima encontrada.')],
            ),
          );
  }
}

class ListEquality {
  bool equals(
      List<Map<String, dynamic>> list1, List<Map<String, dynamic>> list2) {
    if (list1.length != list2.length) return false;

    for (int i = 0; i < list1.length; i++) {
      if (!mapEquals(list1[i], list2[i])) {
        return false;
      }
    }

    return true;
  }

  bool mapEquals(Map<String, dynamic> map1, Map<String, dynamic> map2) {
    if (map1.length != map2.length) return false;

    for (String key in map1.keys) {
      if (map1[key] != map2[key]) {
        return false;
      }
    }

    return true;
  }
}
