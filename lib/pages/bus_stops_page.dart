import 'package:flutter/material.dart';
import 'package:flutter/services.dart'; // Import necessário para rootBundle
import 'dart:convert';

class BusStopsPage extends StatefulWidget {
  const BusStopsPage({super.key});

  @override
  _BusStopsPageState createState() => _BusStopsPageState();
}

class _BusStopsPageState extends State<BusStopsPage> {
  Future<List<dynamic>> _loadStopsData() async {
    try {
      final String response = await rootBundle.loadString('assets/brt_stations.json');
      final List<dynamic> data = jsonDecode(response);
      return data;
    } catch (e) {
      print('Erro ao carregar dados das paradas: $e');
      return [];
    }
  }

  void _showStopDetails(BuildContext context, String stopName, double latitude, double longitude) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(stopName),
        content: Text('Coordenadas:\nLatitude: $latitude\nLongitude: $longitude'),
        actions: [
          TextButton(
            onPressed: () {
              // Aqui você pode implementar a lógica para abrir o mapa
              Navigator.of(context).pop();
            },
            child: const Text('Ver no Mapa'),
          ),
          TextButton(
            onPressed: () {
              Navigator.of(context).pop();
            },
            child: const Text('Fechar'),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Center (child: const Text('Paradas de Ônibus'),
        ),
      ),
      body: FutureBuilder<List<dynamic>>(
        future: _loadStopsData(),
        builder: (context, AsyncSnapshot<List<dynamic>> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return const Center(child: Text('Erro ao carregar paradas'));
          } else {
            final stopsData = snapshot.data ?? [];
            return ListView.builder(
              itemCount: stopsData.length,
              itemBuilder: (context, index) {
                final stop = stopsData[index];
                final stopName = stop['nome'] ?? 'Nome não disponível';
                final latitude = stop['coordenada']?[1] ?? 0.0; // Corrigido para acessar a coordenada correta
                final longitude = stop['coordenada']?[0] ?? 0.0; // Corrigido para acessar a coordenada correta

                return ListTile(
                  title: Text(stopName),
                  trailing: TextButton(
                    child: const Text('Ver no Mapa'),
                    onPressed: () {
                      _showStopDetails(context, stopName, latitude, longitude);
                    },
                  ),
                );
              },
            );
          }
        },
      ),
    );
  }
}
