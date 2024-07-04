import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';

import '../models/bus_line_model.dart';
import '../screens/map_screen.dart';
import '../services/olho_vivo_service.dart';

class SearchLineTab extends StatefulWidget {
  @override
  _SearchLineTabState createState() => _SearchLineTabState();
}

class _SearchLineTabState extends State<SearchLineTab> {
  List<BusLineModel> _busLines = [];
  GoogleMapController? _mapController;
  final TextEditingController _searchController = TextEditingController();
  bool _isLoading = false;
  int _selectedSentido = 1;

  @override
  void initState() {
    super.initState();
    _searchController.addListener(_onSearchChanged);
  }

  void _onSearchChanged() {
    if (_searchController.text.isNotEmpty) {
      _searchBusLines(_searchController.text, _selectedSentido);
    } else {
      setState(() {
        _busLines = [];
      });
    }
  }

  Future<void> _searchBusLines(String query, int sentido) async {
    setState(() {
      _isLoading = true;
    });
    try {
      final api = Provider.of<OlhoVivoService>(context, listen: false);
      final results = await api.searchBusLines(query, sentido);
      setState(() {
        _busLines = results;
      });
    } catch (e) {
      print('Error searching bus lines: $e');
      setState(() {
        _busLines = [];
      });
    } finally {
      setState(() {
        _isLoading = false;
      });
    }
  }

  void _showBusPositions(BusLineModel line) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => MapScreen(busLine: line),
      ),
    );
  }

  @override
  void dispose() {
    _searchController.removeListener(_onSearchChanged);
    _searchController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              children: [
                TextField(
                  controller: _searchController,
                  decoration: const InputDecoration(
                    labelText: 'Buscar linha de ônibus',
                    border: OutlineInputBorder(
                        borderRadius: BorderRadius.all(Radius.circular(20.0))),
                    prefixIcon: Icon(Icons.search),
                  ),
                ),
                const SizedBox(height: 10),
                Row(
                  children: [
                    const Text("Sentido:"),
                    const SizedBox(width: 10),
                    DropdownButton<int>(
                      value: _selectedSentido,
                      onChanged: (int? newValue) {
                        setState(() {
                          _selectedSentido = newValue!;
                        });
                        if (_searchController.text.isNotEmpty) {
                          _searchBusLines(
                              _searchController.text, _selectedSentido);
                        }
                      },
                      items: const [
                        DropdownMenuItem<int>(
                          value: 1,
                          child: Text("Term. Principal para Secundário"),
                        ),
                        DropdownMenuItem<int>(
                          value: 2,
                          child: Text("Term. Secundário para Principal"),
                        ),
                      ],
                    ),
                  ],
                ),
              ],
            ),
          ),
          _isLoading
              ? const Center(child: CircularProgressIndicator())
              : Expanded(
            child: ListView.builder(
              itemCount: _busLines.length,
              itemBuilder: (context, index) {
                final line = _busLines[index];
                return Column(
                  children: [
                    ListTile(
                      title: Text(
                        'Linha ${line.primaryLine}-${line.secondaryLine}',
                        style: TextStyle(
                            fontWeight: _selectedSentido == 1
                                ? FontWeight.bold
                                : FontWeight.normal),
                      ),
                      subtitle: Text(_selectedSentido == 1
                          ? '${line.startTerminal} - ${line.endTerminal}'
                          : '${line.endTerminal} - ${line.startTerminal}'),
                      onTap: () => _showBusPositions(line),
                    ),
                    if (index < _busLines.length - 1) const Divider(),
                  ],
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
