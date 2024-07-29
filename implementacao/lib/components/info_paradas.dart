import 'package:flutter/material.dart';
import 'package:implementacao/components/previsao_chegada.dart';

class InfoParadas extends StatelessWidget {
  final String idParada;
  final String nomeParada;
  final List<String> numerosOnibus;
  final List<String> horariosChegada;

  const InfoParadas({
    super.key,
    required this.idParada,
    required this.nomeParada,
    required this.numerosOnibus,
    required this.horariosChegada,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(10.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Stack(
                children: [
                  Container(
                    height: 40,
                    width: 40,
                    decoration: BoxDecoration(
                      color: Colors.blue,
                      border: Border.all(
                        color: const Color.fromARGB(255, 191, 222, 247),
                      ),
                      borderRadius: BorderRadius.circular(5),
                    ),
                  ),
                  const Padding(
                    padding: EdgeInsets.fromLTRB(8.0, 8.0, 8.0, 8.0),
                    child: Icon(
                      Icons.directions_bus,
                      color: Colors.white,
                    ),
                  )
                ],
              ),
              const SizedBox(width: 10),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    RichText(
                      text: TextSpan(
                        style: const TextStyle(color: Color(0xFFA8A8A8)),
                        children: [
                          const TextSpan(
                            text: '\nPARADA ',
                            style: TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                          TextSpan(
                            text: nomeParada.isEmpty
                                ? 'Parada sem nome'
                                : nomeParada,
                            style: const TextStyle(
                              fontWeight: FontWeight.bold,
                              color: Colors.white,
                            ),
                          ),
                          const TextSpan(text: '\nId da parada: '),
                          TextSpan(text: idParada),
                        ],
                      ),
                    ),
                  ],
                ),
              ),
              IconButton(
                onPressed: () {
                  showModalBottomSheet(
                    showDragHandle: true,
                    backgroundColor: const Color(0xFF363636),
                    context: context,
                    builder: (BuildContext context) {
                      return PrevisaoChegada(
                        numerosOnibus: numerosOnibus,
                        horariosChegada: horariosChegada,
                      );
                    },
                  );
                },
                icon: const Icon(
                  Icons.bus_alert,
                  color: Colors.blue,
                ),
              ),
            ],
          ),
        ),
        const Divider(
          thickness: 0.1,
          height: 0.1,
        ),
      ],
    );
  }
}
