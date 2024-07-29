import 'package:flutter/material.dart';

class InfoLinha extends StatelessWidget {
  final String idLinha;
  final bool linhaCircular;
  final String numeroLinha;
  final int sentidoLinha;
  final String partida;
  final String destino;

  const InfoLinha({
    super.key,
    required this.idLinha,
    required this.linhaCircular,
    required this.numeroLinha,
    required this.sentidoLinha,
    required this.partida,
    required this.destino,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const SizedBox(
          height: 5,
        ),
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
                            Icons.linear_scale,
                            color: Colors.white,
                          ),
                          Text(
                            numeroLinha,
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
                      const TextSpan(text: 'Código identificador da linha: '),
                      TextSpan(text: idLinha),
                      const TextSpan(text: '\nOpera em modo circular: '),
                      TextSpan(text: linhaCircular ? 'sim' : 'não'),
                      const TextSpan(text: '\nSentido da Linha: '),
                      TextSpan(
                        text: sentidoLinha == 1
                            ? 'Terminal Principal para Terminal Secundário'
                            : 'Terminal Secundário para Terminal Principal',
                      ),
                      const TextSpan(text: '\nPartida: '),
                      TextSpan(text: partida),
                      const TextSpan(text: ' | Destino: '),
                      TextSpan(text: destino),
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
