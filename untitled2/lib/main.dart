import 'dart:convert';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
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
              message: lExpand.c,
              child: Icon(Icons.bus_alert,
                  color: Colors.blueGrey[800], size: 30.0),
            )),
          );
        }).toList();
      }).toList();
    }).toList();

    final markersPrevisoes = (previsao?.p ?? []).expand((previsoesExpand) {
      return (previsoesExpand.l ?? []).expand((lExpand) {
        return (lExpand.vs ?? []).map((vsExpand) {
          return Marker(
            width: 80.0,
            height: 80.0,
            point: LatLng(previsoesExpand.py, previsoesExpand.px),
            builder: (ctx) => IconButton(
              icon: Icon(Icons.location_on, color: Colors.black),
              onPressed: () {
                showDialog(
                  context: context,
                  builder: (BuildContext context) {
                    return AlertDialog(
                      icon: Icon(Icons.access_time, color: Colors.black45),
                      title: Text(previsoesExpand.np),
                      content: Text('Horario da previsão da chegada: ${vsExpand.t}'),
                    );
                  },
                );
              },
            ),
          );
        });
      });
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
                    MarkerLayer(
                      markers: markersPrevisoes
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
                        return Icon(Icons.bus_alert,
                            color: Colors.blueGrey[800], size: 30.0);
                        },
                      )
                    ),
                  ],
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
                                alertFiltro(context, 1);
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
                              onPressed: () {
                                alertFiltro(context, 2);
                              },
                              child: Icon(Icons.stacked_line_chart,
                                  color: Colors.blueAccent,
                                  size: 40), // Cor do ícone
                            ),
                          ),
                          Tooltip(
                            message: "Pesquisar Sentido Linhas",
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.white, // Cor de fundo
                                shape: CircleBorder(), // Forma circular
                                padding: EdgeInsets.all(16), // Espaçamento interno
                              ),
                              onPressed: () {
                                alertSentidoLinhas(context);
                              },
                              child: Icon(Icons.compare_arrows,
                                  color: Colors.blue[900],
                                  size: 40), // Cor do ícone
                            ),
                          ),
                          Tooltip(
                            message: "Pesquisar Previsao Chegada",
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.white, // Cor de fundo
                                shape: CircleBorder(), // Forma circular
                                padding: EdgeInsets.all(16), // Espaçamento interno
                              ),
                              onPressed: () {
                                alertPrevisaoChegada(context);
                              },
                              child: Icon(Icons.access_time,
                                  color: Colors.amber[500],
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

  List<Parada> paradas = [];
  List<Linha> linhas = [];
  List<Veiculos> veiculos = [];
  PrevisaoChegada? previsao;
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

  void pushInfoLinhas(){
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) {
        return InfoLinhasPage(linhas: linhas);
      }),
    );
  }

  void alertFiltro(BuildContext context, int codigo){
    final TextEditingController controllerFiltro = TextEditingController();
    showDialog(
        context: context,
        builder: (BuildContext context){
          return AlertDialog(
            title: Text("Filtro"),
            content: Container(
              child: TextField(
                controller: controllerFiltro,
                decoration: InputDecoration(
                    label: Text('Filtro')
                ),
              ),
            ),
            actions: <Widget>[
              TextButton(child: Text('Cancelar'), onPressed: () => Navigator.of(context).pop()),
              TextButton(
                  child: Text('Buscar'),
                  onPressed: () {
                    if(controllerFiltro.text.isNotEmpty){
                      if(codigo == 1){
                        getParadas(controllerFiltro.text);
                        Navigator.of(context).pop();
                      }else if(codigo == 2){
                        getInfoLinhas(controllerFiltro.text);
                      }else{
                        print('Erro');
                      }
                    }else{
                      displayErro(context, "Insira algo na barra de pesquisa para filtrar");
                    }
                  }
              )
            ],
          );
        }
    );
  }

  void alertSentidoLinhas(BuildContext context) {
    final TextEditingController codigoController = TextEditingController();
    final TextEditingController sentidoController = TextEditingController();
    showDialog(
        context: context,
        builder: (BuildContext context){
          return AlertDialog(
            title: Text("Buscar Linha pelo Sentido"),
            content: Column(
              children: [
                TextField(
                  controller: codigoController,
                  decoration: InputDecoration(
                      label: Text('Codigo da Linha')
                  ),
                ),
                Text("1: Terminal Principal para Terminal Secundário \n 2: Terminal Secundário para Terminal Principal"),
                TextField(
                  controller: sentidoController,
                  decoration: InputDecoration(
                      label: Text('Sentido da Linha (1 - 2)')
                  ),
                  keyboardType: TextInputType.number,
                  inputFormatters: <TextInputFormatter>[
                    FilteringTextInputFormatter.allow(RegExp(r'[12]')),
                  ],
                ),
              ],
            ),
            actions: <Widget>[
              TextButton(child: Text('Cancelar'), onPressed: () => Navigator.of(context).pop()),
              TextButton(
                  child: Text('Buscar'),
                  onPressed: () {
                    getInfoSentidoLinhas(codigoController.text, sentidoController.text);
                  }
              )
            ],
          );
        }
    );
  }

  void alertPrevisaoChegada(BuildContext context) {
    final TextEditingController controllerCodigoParada = TextEditingController();
    final TextEditingController controllerCodigoLinha = TextEditingController();
    showDialog(
        context: context,
        builder: (BuildContext context){
          return AlertDialog(
            title: Text("Previsao Chegada"),
            content: Column(
              children: [
                TextField(
                  controller: controllerCodigoParada,
                  decoration: InputDecoration(
                      label: Text('Codigo da Parada')
                  ),
                ),
                TextField(
                  controller: controllerCodigoLinha,
                  decoration: InputDecoration(
                      label: Text('Codigo da Linha')
                  ),
                ),
              ],
            ),
            actions: <Widget>[
              TextButton(child: Text('Cancelar'), onPressed: () => Navigator.of(context).pop()),
              TextButton(
                  child: Text('Buscar'),
                  onPressed: () {
                    getPrevisaoChegada(controllerCodigoParada.text, controllerCodigoLinha.text);
                    Navigator.of(context).pop();
                  }
              )
            ],
          );
        }
    );
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
        pushInfoLinhas();
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

  Future<void> getInfoSentidoLinhas(String codigoLinha, String sentido) async {
    try {
      isLoading = true;
      final resposta = await http.get(Uri.parse(
          'https://aiko-olhovivo-proxy.aikodigital.io/Linha/BuscarLinhaSentido?termosBusca=${codigoLinha}&sentido=${sentido}'));
      if (resposta.statusCode == 200) {
        final jsonList = json.decode(resposta.body);
        linhas = Linha.listaDeLinhasFromJson(jsonList);
        setState(() {
          isLoading = false;
        });
        pushInfoLinhas();
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

  Future<void> getPrevisaoChegada(String codigoParada, String codigoLinha) async{
    try {
      isLoading = true;
      http.Response resposta;
      if(codigoParada.isEmpty){
        resposta = await http.get(Uri.parse(
            'https://aiko-olhovivo-proxy.aikodigital.io/Previsao/Linha?codigoLinha=${codigoLinha}'));
      }else if(codigoLinha.isEmpty){
        resposta = await http.get(Uri.parse(
        'https://aiko-olhovivo-proxy.aikodigital.io/Previsao/Parada?codigoParada=${codigoParada}'));
      }
      else {
        resposta = await http.get(Uri.parse(
            'https://aiko-olhovivo-proxy.aikodigital.io/Previsao?codigoParada=${codigoParada}&codigoLinha=${codigoLinha}'));
      }
      if (resposta.statusCode == 200) {
        final jsonMap = json.decode(resposta.body);
        print(resposta.body);
        setState(() {
          previsao = PrevisaoChegada.previsaoChegadaFromJson(jsonMap);
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
  }}

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
