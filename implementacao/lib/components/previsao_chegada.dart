import 'package:flutter/material.dart';
import 'package:implementacao/components/onibus_previsao.dart';

class PrevisaoChegada extends StatelessWidget {
  final List<String> numerosOnibus;
  final List<String> horariosChegada;

  const PrevisaoChegada({
    super.key,
    required this.numerosOnibus,
    required this.horariosChegada,
  });

  @override
  Widget build(BuildContext context) {
    return DraggableScrollableSheet(
      initialChildSize: 1.0,
      minChildSize: 0.25,
      maxChildSize: 1.0,
      expand: true,
      builder: (context, scrollController) {
        return SizedBox(
          width: MediaQuery.of(context).size.width,
          child: ListView.builder(
            itemCount: numerosOnibus.length,
            itemBuilder: (context, index) {
              return Onibusprevisao(
                numeroOnibus: numerosOnibus[index],
                horarioChegada: horariosChegada[index],
              );
            },
          ),
        );
      },
    );
  }
}
