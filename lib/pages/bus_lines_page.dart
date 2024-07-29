import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class BusLinesPage extends StatefulWidget {
  const BusLinesPage({super.key});

  @override
  _BusLinesPageState createState() => _BusLinesPageState();
}

class _BusLinesPageState extends State<BusLinesPage> {
  final Map<String, String> _lineNames = {
    '10': 'Terminal Santa Cruz ↔ Terminal Alvorada EXPRESSO',
    '11': 'Terminal Santa Cruz ↔ Terminal Alvorada',
    '12': 'Pingo D\'Água ↔ Terminal Alvorada Expresso',
    '13': 'Mato Alto ↔ Terminal Alvorada Expresso',
    '14': 'Paciência/Santa Eugênia ↔ Salvador Allende Semi-direto',
    '17': 'Terminal Campo Grande ↔ Terminal Santa Cruz Parador',
    '18': 'Pontal ↔ Terminal Jardim Oceânico Expresso',
    '19': 'Pingo D\'água ↔ Salvador Allende Expresso',
    '20': 'Terminal Santa Cruz ↔ Salvador Allende Expresso',
    '22': 'Terminal Jardim Oceânico ↔ Terminal Alvorada',
    '25': 'Mato Alto ↔ Terminal Alvorada Parador',
    '31': 'Terminal Alvorada ↔ Vicente de Carvalho Semi-direto',
    '35': 'Terminal Paulo da Portela ↔ Terminal Alvorada',
    '38': 'Terminal Alvorada ↔ Aeroporto Tom Jobim/Galeão Parador',
    '40': 'Terminal Paulo da Portela ↔ Terminal Alvorada Expresso',
    '41': 'Terminal Paulo da Portela ↔ Terminal Recreio Expresso',
    '42': 'Madureira (Manaceia) ↔ Aeroporto Tom Jobim/Galeão Parador',
    '43': 'Santa Efigênia ↔ Terminal Aroldo Melodia (Fundão) Expresso',
    '46': 'Penha ↔ Terminal Alvorada Expresso',
    '50': 'Terminal Centro Olímpico ↔ Terminal Jardim Oceânico Parador',
    '51': 'Terminal Recreio ↔ Terminal Deodoro Parador',
    '52': 'Terminal Deodoro ↔ Terminal Alvorada',
    '53': 'Terminal Sulacap ↔ Terminal Alvorada Expresso',
    '60': 'Terminal Deodoro ↔ Terminal Gentileza',
    '61': 'Terminal Deodoro ↔ Terminal Gentileza Expresso',
    '67': 'Terminal Campo Grande ↔ Terminal Deodoro Parador',
    '80': 'Penha ↔ Terminal Gentileza Parador',
    '90': 'Terminal Aroldo Melodia (Fundão) ↔ Terminal Gentileza',
  };

  Future<List<dynamic>> _fetchBusLines() async {
    try {
      final response = await http.get(Uri.parse('https://dados.mobilidade.rio/gps/brt'));
      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        if (data is Map<String, dynamic> && data.containsKey('veiculos')) {
          return data['veiculos']; 
        } else {
          throw Exception('Estrutura de dados inesperada');
        }
      } else {
        throw Exception('Falha ao carregar linhas de ônibus');
      }
    } catch (e) {
      print('Erro ao buscar linhas de ônibus: $e');
      return [];
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
      title: Center(
        child: const Text('Linhas de Ônibus'),
      ),
    ),
      body: FutureBuilder<List<dynamic>>(
        future: _fetchBusLines(),
        builder: (context, AsyncSnapshot<List<dynamic>> snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return const Center(child: Text('Erro ao carregar as linhas'));
          } else {
            final busLines = snapshot.data ?? [];
            return ListView.builder(
              itemCount: busLines.length,
              itemBuilder: (context, index) {
                final line = busLines[index];
                final name = _lineNames[line['linha'].toString()] ?? 'Desconhecida'; // Corrigido
                return ListTile(
                  title: Text('$name (${line['linha']})'),
                  subtitle: Text('Sentido: ${line['sentido']}'),
                );
              },
            );
          }
        },
      ),
    );
  }
}
