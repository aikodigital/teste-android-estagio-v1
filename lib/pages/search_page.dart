import 'package:flutter/material.dart';
import 'package:mova_sp/controllers/map_controller.dart';
import 'package:mova_sp/models/bus_line.dart';
import 'package:mova_sp/pages/line_detail.dart';

class SearchPage extends StatefulWidget {
  const SearchPage({super.key});

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  final TextEditingController _searchController = TextEditingController();
  List<BusLine> _filteredBusLines = [];

  @override
  void initState() {
    super.initState();
    _filteredBusLines = MapController.to.busLines;
    _searchController.addListener(_filterBusLines);
  }

  @override
  void dispose() {
    _searchController.removeListener(_filterBusLines);
    _searchController.dispose();
    super.dispose();
  }

  void _filterBusLines() {
    String query = _searchController.text.toLowerCase();
    setState(() {
      _filteredBusLines = MapController.to.busLines.where((line) {
        String busLabel = line.lineDirection == 1
            ? "${line.busLabelFromPrincipalToSecundaryTerminal} / ${line.busLabelFromSecundaryToPrincipalTerminal}"
            : "${line.busLabelFromSecundaryToPrincipalTerminal} / ${line.busLabelFromPrincipalToSecundaryTerminal}";
        String baseOrAttendance = line.secondLabel == 10
            ? "Base: ${line.firstLabel} ${line.secondLabel}"
            : "Atendimento: ${line.firstLabel} ${line.secondLabel}";

        return busLabel.toLowerCase().contains(query) ||
            baseOrAttendance.toLowerCase().contains(query);
      }).toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Pesquisar linhas'),
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              controller: _searchController,
              decoration: const InputDecoration(
                labelText: 'Pesquisar linha',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: _filteredBusLines.length,
              itemBuilder: (context, index) => Card(
                child: ListTile(
                  title: Text(_filteredBusLines[index].lineDirection == 1
                      ? "Linha: ${_filteredBusLines[index].busLabelFromPrincipalToSecundaryTerminal} / ${_filteredBusLines[index].busLabelFromSecundaryToPrincipalTerminal}"
                      : "Linha: ${_filteredBusLines[index].busLabelFromSecundaryToPrincipalTerminal} / ${_filteredBusLines[index].busLabelFromPrincipalToSecundaryTerminal}"),
                  subtitle: Text(_filteredBusLines[index].secondLabel == 10
                      ? "Base: ${_filteredBusLines[index].firstLabel} ${_filteredBusLines[index].secondLabel}"
                      : "Atendimento: ${_filteredBusLines[index].firstLabel} ${_filteredBusLines[index].secondLabel}"),
                  dense: true,
                  leading: const Icon(Icons.bus_alert_outlined),
                  onTap: () => {
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => LineDetail(busLine: _filteredBusLines[index]),
                      ),
                    )
                  },
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
