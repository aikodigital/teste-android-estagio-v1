// ignore_for_file: use_super_parameters, prefer_const_constructors, avoid_print

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:sp_transporte/services/services.dart';
import 'package:sp_transporte/utils/provider.dart';
import 'package:sp_transporte/widgets/search_lines.dart';

class ArrivalForecast extends StatefulWidget {
  const ArrivalForecast({Key? key}) : super(key: key);

  @override
  State<ArrivalForecast> createState() => _ArrivalForecastState();
}

class _ArrivalForecastState extends State<ArrivalForecast> {
  final SpTransService _spTransService = SpTransService();
  Map<String, dynamic> _arrivalForecast = {};
  String _codigoLinha = '';

  void _loadArrivalForecast() async {
    try {
      final arrivalForecast = await _spTransService.fetchPrevisao(_codigoLinha);
      setState(() {
        _arrivalForecast = arrivalForecast;
      });
    } catch (e) {
      print(e);
    }
  }

  @override
  Widget build(BuildContext context) {
    final linhaProvider = Provider.of<LinhaProvider>(context);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.primary,
        title: Text(
          'Previsão de chegada',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Pesquise e selecione a linha na qual você deseja exibir a previsão de chegada.',
              style: TextStyle(fontSize: 16.0),
            ),
            SizedBox(height: 20),
            SearchLines(
              onSuggestionSelected: (p0) {
                setState(() {
                  _codigoLinha = linhaProvider.linha;
                  _loadArrivalForecast();
                });
              },
            ),
            SizedBox(height: 20),
            Expanded(
              child: ListView(
                children: _buildStopCards(),
              ),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _buildStopCards() {
    if (_arrivalForecast.isEmpty) {
      return [Center(child: Text('...'))];
    } else {
      return _arrivalForecast['ps'].map<Widget>((stop) {
        return Card(
          child: ListTile(
            title: Text('${stop['np']}'),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('Código da Parada: ${stop['cp']}'),
                ..._buildVehicleList(stop['vs']),
              ],
            ),
          ),
        );
      }).toList();
    }
  }

  List<Widget> _buildVehicleList(List<dynamic> vehicles) {
    if (vehicles.isEmpty) {
      return [Text('Nenhum veículo disponível.')];
    } else {
      return vehicles.map<Widget>((vehicle) {
        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Prefixo do Veículo: ${vehicle['p']}'),
            Text('Hora Prevista de Chegada: ${vehicle['t']}'),
            Text('Acessível: ${vehicle['a'] ? 'Sim' : 'Não'}'),
          ],
        );
      }).toList();
    }
  }
}
