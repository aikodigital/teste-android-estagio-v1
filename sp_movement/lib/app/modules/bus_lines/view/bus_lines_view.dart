import 'package:flutter/material.dart';
import 'package:flutter_mobx/flutter_mobx.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../stores/bus_lines_store.dart';

class BusLinesView extends StatefulWidget {
  const BusLinesView({super.key});

  @override
  State<BusLinesView> createState() => _BusLinesViewState();
}

class _BusLinesViewState extends State<BusLinesView> {
  var store = Modular.get<BusLinesStore>();


  @override
  Widget build(BuildContext context) {
    return Observer(
      builder: (_) => Scaffold(
        appBar: AppBar(
          title: Text('Linhas'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              Row(
                children: [
                  SizedBox( width: MediaQuery.of(context).size.width * 0.8,
                      child: TextFormField(
                        onChanged: (v) {
                          store.setTermSearch(v);
                        },
                        decoration: InputDecoration(
                          labelText: 'Pesquisar',
                        ),
                      )),
                  SizedBox(
                    width: MediaQuery.of(context).size.width * 0.2,
                    child: IconButton(
                      onPressed: () async {
                        FocusNode().unfocus();
                        await store.find();
                      },
                      icon: Icon(Icons.search),
                    ),
                  ),
                ],
              ),
              Row(
                children: [
                  SizedBox(
                      child: RadioMenuButton(value: 0, groupValue: store.codeDirection, onChanged: (v){store.setCodeDirection(v!);}, child: const Text('Todos'))),
                ],
              ),
              Row(
                children: [
                  SizedBox(
                      child: RadioMenuButton(value: 1, groupValue: store.codeDirection, onChanged: (v){store.setCodeDirection(v!);},
                          child:  Text('Teminal Principal para Secundario',))),
                ],
              ),
              Row(
                children: [
                  SizedBox(
                      child: RadioMenuButton(value: 2, groupValue: store.codeDirection, onChanged:  (v){store.setCodeDirection(v!);}, child: const Text('Terminal Secundario para Principal'))),
                ],
              ),
              SizedBox( height: MediaQuery.of(context).size.height * 0.8,
                child: store.busLineList.isEmpty? Center(child: Text('Nenhuma Linha Encontrada'),): ListView.builder(
                  itemCount: store.busLineList.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text('Nome ${store.busLineList[index].letterStartEnd} '),
                      subtitle: Text('Linha: ${store.busLineList[index].codeLine} '),
                    );
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
