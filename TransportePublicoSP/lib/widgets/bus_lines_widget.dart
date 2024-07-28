import 'package:flutter/material.dart';
import 'package:transportepublicosp/screens/bus_position_screen.dart';

class BusLinesWidget extends StatelessWidget {
  final String stopName;
  final List<Map<String, dynamic>> busLines;

  const BusLinesWidget(
      {super.key, required this.stopName, required this.busLines});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Linhas de Ônibus - $stopName'),
      ),
      body: busLines.isEmpty
          ? Center(
              child: Container(
                height: 40,
                alignment: Alignment.center,
                child: const Text(
                  'Não há linhas no momento',
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ),
            )
          : ListView.builder(
              itemCount: busLines.length,
              itemBuilder: (context, index) {
                final line = busLines[index];
                return Padding(
                  padding: const EdgeInsets.symmetric(vertical: 4.0),
                  child: ExpansionTile(
                    title: Text('Linha: ${line['code']}'),
                    subtitle: Text('Destino: ${line['destination']}'),
                    children: [
                      ListTile(
                        title: Text('Origem: ${line['origin']}'),
                      ),
                      ...line['vehicles'].map<Widget>((vehicle) {
                        return ListTile(
                          title: Text('Veículo: ${vehicle['p']}'),
                          subtitle: Text('Chegada prevista: ${vehicle['t']}'),
                          trailing: Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              if (vehicle['a']) const Icon(Icons.accessible),
                              IconButton(
                                icon: const Icon(Icons.gps_fixed),
                                onPressed: () {
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                      builder: (context) => BusPositionScreen(
                                        lineCode: line['lineCode'].toString(),
                                        busPositions:
                                            (line['vehicles'] as List<dynamic>)
                                                .map((e) =>
                                                    e as Map<String, dynamic>)
                                                .toList(),
                                        busLine: line,
                                      ),
                                    ),
                                  );
                                },
                              ),
                            ],
                          ),
                        );
                      }).toList(),
                    ],
                  ),
                );
              },
            ),
    );
  }
}
