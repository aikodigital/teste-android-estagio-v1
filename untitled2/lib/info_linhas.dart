import 'package:flutter/material.dart';
import 'models.dart';

class InfoLinhasPage extends StatelessWidget {
  final List<Linha> linhas;
  InfoLinhasPage({required this.linhas});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Informações das Linhas de Ônibus'),
      ),
      body: linhas.isNotEmpty
          ? ListView.builder(
              itemCount: linhas.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(linhas[index].lt),
                  subtitle: Text(
                      'Terminal Primario: ${linhas[index].tp} \n Terminal Secundario: ${linhas[index].ts}'),
                );
              },
            )
          : Center(
              child: Text('Nenhuma linha encontrada'),
            ),
    );
  }
}
