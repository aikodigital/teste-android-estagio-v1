import 'package:flutter/material.dart';
import 'package:implementacao/components/info_linha.dart';
import 'package:implementacao/models/bus_position.dart';
import 'package:implementacao/services/api.dart';

import '../components/custom_search_bar.dart';

class InfoLinhasPage extends StatefulWidget {
  const InfoLinhasPage({super.key});

  @override
  State<InfoLinhasPage> createState() => _InfoLinhasPageState();
}

class _InfoLinhasPageState extends State<InfoLinhasPage> {
  final ApiOlhoVivo api = ApiOlhoVivo();
  final TextEditingController searchController = TextEditingController();
  final FocusNode searchFocusNode = FocusNode();

  List<BusPosition> busPositions = [];
  bool isLoading = true;
  bool isExpanded = false;

  @override
  void initState() {
    super.initState();
    _getBusLinesInfo();
    searchFocusNode.addListener(() {
      if (searchFocusNode.hasFocus) {
        _expandSearchBar();
      } else {
        _collapseSearchBar();
      }
    });
  }

  @override
  void dispose() {
    searchFocusNode.dispose();
    super.dispose();
  }

  void _expandSearchBar() {
    setState(() {
      isExpanded = true;
    });
  }

  void _collapseSearchBar() {
    setState(() {
      isExpanded = false;
    });
  }

  Future<void> _getBusLinesInfo({String? searchText}) async {
    setState(() {
      isLoading = true; 
    });

    List<BusPosition> positions = await api.obterPosicoesVeiculos();
    
    if (searchText != null && searchText.isNotEmpty) {
      positions = positions.where((position) {
        return position.linhaOnibus.letreiro.contains(searchText);
      }).toList();
    }

    setState(() {
      busPositions = positions;
      isLoading = false; 
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            CustomSearchBar(
              isExpanded: isExpanded,
              onExpand: _expandSearchBar,
              onCollapse: _collapseSearchBar,
              searchController: searchController,
              searchFocusNode: searchFocusNode,
              onSubmitted: (value) {
                _getBusLinesInfo(searchText: value);
              },
            ),
            const SizedBox(
              height: 5,
            )
          ],
        ),
      ),
      body: Container(
        color: Colors.black,
        child: isLoading
            ? const Center(
                child: CircularProgressIndicator(
                  valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
                ),
              )
            : isExpanded
                ? const Center(
                    child: Text(
                      'Digite o número da linha para buscar informações.',
                      style: TextStyle(color: Colors.white),
                    ),
                  )
                : Column(
                    children: [
                      const Divider(
                        thickness: 0.1,
                        height: 0.1,
                      ),
                      Expanded(
                        child: ListView.builder(
                          itemCount: busPositions.length,
                          itemBuilder: (context, index) {
                            var busPosition = busPositions[index];
                            return InfoLinha(
                              idLinha:
                                  busPosition.linhaOnibus.idLinha.toString(),
                              linhaCircular:
                                  busPosition.linhaOnibus.linhaCircular,
                              numeroLinha: busPosition.linhaOnibus.letreiro,
                              sentidoLinha:
                                  busPosition.linhaOnibus.sentidoLinha,
                              partida: busPosition.linhaOnibus.partida,
                              destino: busPosition.linhaOnibus.destino,
                            );
                          },
                        ),
                      ),
                    ],
                  ),
      ),
    );
  }
}