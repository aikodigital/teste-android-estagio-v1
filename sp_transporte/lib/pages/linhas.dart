// ignore_for_file: use_super_parameters, prefer_const_constructors, avoid_print

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:sp_transporte/services/services.dart';
import 'package:sp_transporte/utils/provider.dart';
import 'package:sp_transporte/widgets/search_lines.dart';

class LinesInfo extends StatefulWidget {
  const LinesInfo({Key? key}) : super(key: key);

  @override
  State<LinesInfo> createState() => _LinesInfoState();
}

class _LinesInfoState extends State<LinesInfo> {
  final SpTransService _spTransService = SpTransService();
  Map<String, dynamic> _linesInfo = {};
  String _codigoLinha = '';

  void _loadLinesInfo() async {
    try {
      final linesInfo = await _spTransService.fetchLinhaInfo(_codigoLinha);
      setState(() {
        _linesInfo = linesInfo;
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
          'Informação de Linha',
          style: TextStyle(color: Colors.white),
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Pesquise e selecione a linha na qual você deseja exibir informações.',
              style: TextStyle(fontSize: 16.0),
            ),
            SizedBox(height: 20),
            SearchLines(
              onSuggestionSelected: (p0) {
                setState(() {
                  _codigoLinha = linhaProvider.linha;
                  _loadLinesInfo();
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
    if (_linesInfo.isEmpty) {
      return [Center(child: Text('...'))];
    } else {
      return _linesInfo['ps'].map<Widget>((stop) {
        return Card(
          child: ListTile(
            title: Text('${stop['np']}'),
            subtitle: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text('Horário de Referência: ${_linesInfo['hr']}'),
                Text('Latitude: ${stop['py']}'),
                Text('Longitude: ${stop['px']}'),
              ],
            ),
          ),
        );
      }).toList();
    }
  }
}
