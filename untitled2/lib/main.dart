import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter_map_marker_cluster/flutter_map_marker_cluster.dart';
import 'package:http/http.dart' as http;
import 'package:flutter_map/flutter_map.dart';
import 'package:latlong2/latlong.dart';

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

  final TextEditingController _controller = TextEditingController();

  List<Parada> paradas = [];
  List<Veiculos> veiculos = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    final url = "https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar?token=80336abd6c3edd8acb5eae4fc2ecbe05093c3da75991a8648e4e363f2ecd9f27";
    autorizar(url);
    getPosicoes();
  }

  void pesquisar(){
    getParadas(_controller.text);
  }

  Future<void> getParadas(String filtro) async {
    try {
      isLoading = true;
      final resposta = await http
          .get(Uri.parse('https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar?termosBusca='+filtro));

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

  Future<void> getPosicoes() async{
    try{
      isLoading = true;
      final resposta = await http
          .get(Uri.parse('https://aiko-olhovivo-proxy.aikodigital.io/Posicao'));
      if(resposta.statusCode == 200){
        final jsonList = json.decode(resposta.body);
        Veiculos vs = Veiculos.fromJson(jsonList);
        setState(() {
          veiculos = [vs];
          isLoading = false;
        });
      }else{
        throw Exception('Falha ao carregar dados: ${resposta.statusCode}');
      }
    }catch (e){
      print('Erro: $e');
      setState(() {
        isLoading = false;
      });
    }
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
              child: Icon(Icons.directions_car, color: Colors.blueGrey[800], size: 30.0),
            ),
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
            ),
            children: [
              TileLayer(
                urlTemplate: 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',
              ),
              MarkerLayer(
                markers: paradas.map((parada) {
                  return Marker(
                    width: 80.0,
                    height: 80.0,
                    point: LatLng(parada.py, parada.px),
                    builder: (ctx) => Container(
                    child: Icon(Icons.location_on, color: Colors.red[900], size: 40.0)
                  ));
                }).toList(),
              ),
              MarkerClusterLayerWidget(
                  options: MarkerClusterLayerOptions(
                    maxClusterRadius: 120,
                    size: Size(40, 40),
                    fitBoundsOptions: FitBoundsOptions(
                    padding: EdgeInsets.all(50),
                  ),
                  markers: markersVeiculos,
                  builder: (context, markers) {
                    return FloatingActionButton(
                      onPressed: () {},
                      child: Text('${markers.length}'),
                      );
                    },
                  )
              ),
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
                contentPadding: EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
              ),
            ),
          ),
          Positioned(
            bottom: 20,
            right: 20,
            child: FloatingActionButton(
              onPressed: pesquisar,
              foregroundColor: Colors.red[900],
              child: const Icon(Icons.location_on),
            ),
          ),
        ],
      ),
    );
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

class Parada {
  final int cp;
  final String np;
  final String ed;
  final double py;
  final double px;

  const Parada({
    required this.cp,
    required this.np,
    required this.ed,
    required this.py,
    required this.px,
  });

  factory Parada.fromJson(Map<String, dynamic> json) {
    return Parada(
      cp: json['cp'],
      np: json['np'],
      ed: json['ed'],
      py: json['py'].toDouble(),
      px: json['px'].toDouble(),
    );
  }

  List<Parada> listaDeParadasFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Parada.fromJson(json)).toList();
  }

}

class Linha {
  final int cl;
  final bool lc;
  final String lt;
  final int sl;
  final int tl;
  final String tp;
  final String ts;

  const Linha({
    required this.cl,
    required this.lc,
    required this.lt,
    required this.sl,
    required this.tl,
    required this.tp,
    required this.ts,
  });

  factory Linha.fromJson(Map<String, dynamic> json){
    return Linha(
      cl: json['cl'],
      lc: json['lc'],
      lt: json['lt'],
      sl: json['sl'],
      tl: json['tl'],
      tp: json['tp'],
      ts: json['ts'],
    );
  }

  List<Linha> listaDeLinhasFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Linha.fromJson(json)).toList();
  }
}

class LV {
  final String c;
  final int cl;
  final int sl;
  final String lt0;
  final String lt1;
  final int qv;
  final List<VS> vs;

  const LV({
    required this.c,
    required this.cl,
    required this.sl,
    required this.lt0,
    required this.lt1,
    required this.qv,
    required this.vs,
  });

  factory LV.fromJson(Map<String, dynamic> json){
    return LV(
      c: json['c'],
      cl: json['cl'],
      sl: json['sl'],
      lt0: json['lt0'],
      lt1: json['lt1'],
      qv: json['qv'],
      vs: List<VS>.from(json['vs'].map((x) => VS.fromJson(x))),
    );
  }

  List<LV> listaDeLVFromJson(List<dynamic> jsonList){
    return jsonList.map((json) => LV.fromJson(json)).toList();
  }

}

class VS{
  final int p;
  final bool a;
  final String ta;
  final double py;
  final double px;

  const VS({
    required this.p,
    required this.a,
    required this.ta,
    required this.py,
    required this.px,
  });

  factory VS.fromJson(Map<String, dynamic> json){
    return VS(
      p: json['p'],
      a: json['a'],
      ta: json['ta'],
      py: json['py'].toDouble(),
      px: json['px'].toDouble(),
    );
  }

  List<VS> listaDeVSFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => VS.fromJson(json)).toList();
  }
}

class Veiculos {
  final String hr;
  final List<LV> l;

  const Veiculos({
    required this.hr,
    required this.l
  });

  factory Veiculos.fromJson(Map<String, dynamic> json){
    return Veiculos(
      hr: json['hr'],
      l: List<LV>.from(json['l'].map((x) => LV.fromJson(x))),
    );
  }

  List<Veiculos> listaDeVeiculosFromJson(List<dynamic> jsonList) {
    return jsonList.map((json) => Veiculos.fromJson(json)).toList();
  }
}