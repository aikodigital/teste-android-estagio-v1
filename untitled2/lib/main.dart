import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_map_marker_cluster/flutter_map_marker_cluster.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';
import 'models.dart';
import 'info_linhas.dart';

void main() {
  runApp(MaterialApp(
    home: Home(),
  ));
}

class Home extends StatefulWidget {
  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  @override
  void initState() {
    super.initState();
    final url =
        "https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar?token=80336abd6c3edd8acb5eae4fc2ecbe05093c3da75991a8648e4e363f2ecd9f27";
    autorizar(url);
    getPosicoes();
  }

  @override
  Widget build(BuildContext context) {
    final markersVeiculos = veiculos.expand((veiculosExpand) {
      return veiculosExpand.l.expand((lExpand) {
        return lExpand.vs.map((vs) {
          return Marker(
            width: 80.0,
            height: 80.0,
            point: LatLng(vs.py, vs.px),
            builder: (ctx) => Container(
                child: Tooltip(
              message: lExpand.lt0,
              child: Icon(Icons.directions_car,
                  color: Colors.blueGrey[800], size: 30.0),
            )),
          );
        }).toList();
      }).toList();
    }).toList();

    return Scaffold(
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : Stack(
              children: [
                FlutterMap(
                  options: MapOptions(
                    center: LatLng(-23.55052, -46.633308),
                    zoom: 10.2,
                    maxZoom: 18,
                  ),
                  children: [
                    TileLayer(
                      urlTemplate:
                          'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
                      retinaMode: false,
                      maxZoom: 19,
                    ),
                    MarkerLayer(
                      markers: paradas.map((parada) {
                        return Marker(
                            width: 80.0,
                            height: 80.0,
                            point: LatLng(parada.py, parada.px),
                            builder: (ctx) => Container(
                                child: Icon(Icons.location_on,
                                    color: Colors.red[900], size: 40.0)));
                      }).toList(),
                    ),
                    MarkerClusterLayerWidget(
                        options: MarkerClusterLayerOptions(
                      disableClusteringAtZoom: 17,
                      maxClusterRadius: 120,
                      size: Size(40, 40),
                      fitBoundsOptions: FitBoundsOptions(
                        padding: EdgeInsets.all(50),
                      ),
                      markers: markersVeiculos,
                      builder: (context, markers) {
                        // return FloatingActionButton(
                        //   onPressed: () {},
                        //   backgroundColor: Colors.blueGrey[800],
                        //   foregroundColor: Colors.white,
                        //   child: Text('${markers.length}'),
                        //   );
                        return Icon(Icons.directions_car,
                            color: Colors.blueGrey[800], size: 30.0);
                      },
                    )),
                  ],
                ),
                Container(
                  margin: EdgeInsets.all(20.0),
                  child: TextField(
                    controller: _controller,
                    decoration: InputDecoration(
                      filled: true,
                      fillColor: Color(0xFFF2F2F2),
                      labelText: 'Pesquisa',
                      border: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(12.0),
                        borderSide: BorderSide.none,
                      ),
                      prefixIcon: Icon(Icons.search),
                      contentPadding: EdgeInsets.symmetric(
                          vertical: 16.0, horizontal: 20.0),
                    ),
                  ),
                ),
                Align(
                  alignment: Alignment.centerRight,
                  child: Padding(
                    padding: const EdgeInsets.all(16.0),
                    child: Column(
                        crossAxisAlignment: CrossAxisAlignment.end,
                        mainAxisAlignment: MainAxisAlignment.end,
                        children: [
                          Tooltip(
                            message: "Pesquisar Paradas",
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.white, // Cor de fundo
                                shape: CircleBorder(), // Forma circular
                                padding: EdgeInsets.all(16), // Espaçamento interno
                              ),
                              onPressed: (){
                                if(_controller.text.isNotEmpty){
                                  pesquisarParadas();
                                }else{
                                  displayErro(context, "Insira algo na barra de pesquisa para filtrar");
                                }
                              },
                              child: Icon(Icons.location_on,
                                  color: Colors.red[900], size: 40), // Cor do ícone
                            ),
                          ),
                          Tooltip(
                            message: "Pesquisar Info Linhas",
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.white, // Cor de fundo
                                shape: CircleBorder(), // Forma circular
                                padding: EdgeInsets.all(16), // Espaçamento interno
                              ),
                              onPressed: () async {
                                if(_controller.text.isNotEmpty){
                                  await getInfoLinhas(_controller.text);
                                  Navigator.push(
                                    context,
                                    MaterialPageRoute(builder: (context) {
                                      return InfoLinhasPage(linhas: linhas);
                                    }),
                                  );
                                }else{
                                  displayErro(context, "Insira algo na barra de pesquisa para filtrar");
                                }
                              },
                              child: Icon(Icons.stacked_line_chart,
                                  color: Colors.blueAccent,
                                  size: 40), // Cor do ícone
                            ),
                          ),
                        ]),
                  ),
                ),
              ],
            ),
    );
  }

  final TextEditingController _controller = TextEditingController();

  List<Parada> paradas = [];
  List<Linha> linhas = [];
  List<Veiculos> veiculos = [];
  bool isLoading = true;

  void displayErro(BuildContext context, String message){
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Erro'),
          content: Text(message),
          actions: <Widget>[
            TextButton(
              child: Text('OK'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void pesquisarParadas() {
    getParadas(_controller.text);
  }

  Future<void> getInfoLinhas(String filtro) async {
    try {
      isLoading = true;
      final resposta = await http.get(Uri.parse(
          'https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=${filtro}'));
      if (resposta.statusCode == 200) {
        final jsonList = json.decode(resposta.body);
        linhas = Linha.listaDeLinhasFromJson(jsonList);
        setState(() {
          isLoading = false;
        });
      } else {
        throw Exception('Falha ao carregar dados: ${resposta.statusCode}');
      }
    } catch (e) {
      print('Error: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> getParadas(String filtro) async {
    try {
      isLoading = true;
      final resposta = await http.get(Uri.parse(
          'https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar?termosBusca=' +
              filtro));
      if (resposta.statusCode == 200) {
        final List<dynamic> jsonList = json.decode(resposta.body);
        setState(() {
          paradas = jsonList.map((json) => Parada.fromJson(json)).toList();
          isLoading = false;
        });
      } else {
        throw Exception('Falha ao carregar dados: ${resposta.statusCode}');
      }
    } catch (e) {
      print('Erro: $e');
      setState(() {
        isLoading = false;
      });
    }
  }

  Future<void> getPosicoes() async {
    try {
      isLoading = true;
      final resposta = await http
          .get(Uri.parse('https://aiko-olhovivo-proxy.aikodigital.io/Posicao'));
      if (resposta.statusCode == 200) {
        final jsonList = json.decode(resposta.body);
        Veiculos vs = Veiculos.fromJson(jsonList);
        setState(() {
          veiculos = [vs];
          isLoading = false;
        });
      } else {
        throw Exception('Falha ao carregar dados: ${resposta.statusCode}');
      }
    } catch (e) {
      print('Erro: $e');
      setState(() {
        isLoading = false;
      });
    }
  }
}

Future<void> autorizar(String url) async {
  try {
    final resposta = await http.post(Uri.parse(url));

    if (resposta.statusCode == 200) {
      print('Autorização bem-sucedida');
      final respostaJson = jsonDecode(resposta.body);
      print(respostaJson);
    } else {
      print('Falha na autorização: ${resposta.statusCode}');
    }
  } catch (e) {
    print('Erro: $e');
  }
}
