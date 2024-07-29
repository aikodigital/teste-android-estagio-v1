import 'package:flutter/material.dart';

class Onibusprevisao extends StatelessWidget {
  final String numeroOnibus;
  final String horarioChegada;
  
  const Onibusprevisao({
    super.key,
    required this.numeroOnibus,
    required this.horarioChegada,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const SizedBox(height: 10,),
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 10),
          child: Row(
            children: [
              Container(
                height: 40,
                width: 95,
                decoration: BoxDecoration(
                  color: const Color(0xFF363636),
                  border: Border.all(
                    color: Colors.white,
                  ),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Column(
                  children: [
                    Padding(
                      padding: const EdgeInsets.fromLTRB(5.0, 5.0, 5.0, 2.0),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          const Icon(
                            Icons.directions_bus_rounded,
                            color: Colors.white,
                          ),
                          Text(
                            numeroOnibus,
                            style: const TextStyle(color: Colors.white),
                          ),
                        ],
                      ),
                    ),
                    Container(
                      height: 7,
                      width: 93,
                      decoration: const BoxDecoration(
                        color: Colors.blue,
                        borderRadius: BorderRadius.only(
                          bottomLeft: Radius.circular(100),
                          bottomRight: Radius.circular(100),
                        ),
                      ),
                    )
                  ],
                ),
              ),
              const SizedBox(
                width: 10,
              ),
              Expanded(
                child: RichText(
                  text: TextSpan(
                    style: const TextStyle(color: Color(0xFFA8A8A8)),
                    children: [
                      const TextSpan(text: 'Numero do onibus: '),
                      TextSpan(text: numeroOnibus),
                      const TextSpan(text: '\nHorário de chegada: '),
                      TextSpan(text: horarioChegada),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
        const SizedBox(
          height: 5,
        ),
        const Divider(
          thickness: 0.1,
          height: 0.1,
        ),
      ],
    );
  }
}
