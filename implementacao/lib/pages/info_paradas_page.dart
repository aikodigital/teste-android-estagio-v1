import 'package:flutter/material.dart';
import 'package:implementacao/components/info_paradas.dart';
import 'package:implementacao/models/previsao.dart';
import 'package:implementacao/services/api.dart';

class InfoParadasPage extends StatefulWidget {
  const InfoParadasPage({super.key});

  @override 
  State<InfoParadasPage> createState() => _InfoParadasPageState();
}

class _InfoParadasPageState extends State<InfoParadasPage> {
  final ApiOlhoVivo api = ApiOlhoVivo();
  List<Previsao> previsaoOnibus = [];
  String searchText = '';
  bool isLoading = false;

  @override
  void initState() {
    super.initState();
    
  }

  Future<void> _getBusStopsInfo() async {
    setState(() {
      isLoading = true; 
    });

    List<Previsao> prev = await api.obterPrevisaoChegadaOnibus(searchText);
    Map<String, Previsao> groupedPrevisoes = {};

    for (var p in prev) {
      String key = p.idParada.toString();

      if (!groupedPrevisoes.containsKey(key)) {
        groupedPrevisoes[key] = p;
      } else {
        groupedPrevisoes[key]
            ?.adicionarPrevisao(p.numerosOnibus.first, p.horariosChegada.first);
      }
    }

    setState(() {
      previsaoOnibus = groupedPrevisoes.values.toList();
      isLoading = false; 
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.black,
        title: Column(
          children: [
            Row(
              children: [
                Expanded(
                  child: SizedBox(
                    height: 50,
                    child: TextField(
                      onChanged: (value) {
                        setState(() {
                          searchText = value;
                        });
                      },
                      style: const TextStyle(
                        fontFamily: 'Instagram',
                        color: Color(0xFFA8A8A8),
                      ),
                      decoration: const InputDecoration(
                        prefixIcon: Icon(
                          Icons.search,
                          color: Color(0xFFA8A8A8),
                        ),
                        hintText: 'Pesquisar',
                        hintStyle: TextStyle(
                          color: Color(0xFFA8A8A8),
                        ),
                        filled: true,
                        fillColor: Color(0xFF363636),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.all(Radius.circular(15)),
                          borderSide: BorderSide.none,
                        ),
                      ),
                      onSubmitted: (value) {
                        _getBusStopsInfo(); 
                      },
                    ),
                  ),
                ),
              ],
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
                  color: Colors.white,
                ),
              )
            : searchText.isEmpty
                ? const Center(
                    child: Text(
                      'Digite o código da parada acima \n para buscar informações.',
                      textAlign: TextAlign.center,
                      style: TextStyle(color: Colors.white,),
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
                          itemCount: previsaoOnibus.length,
                          itemBuilder: (context, index) {
                            var previsaoOnibuss = previsaoOnibus[index];
                            return InfoParadas(
                              idParada: previsaoOnibuss.idParada.toString(),
                              nomeParada: previsaoOnibuss.nomeParada,
                              numerosOnibus: previsaoOnibuss.numerosOnibus,
                              horariosChegada: previsaoOnibuss.horariosChegada,
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