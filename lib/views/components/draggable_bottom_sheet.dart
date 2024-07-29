// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:teste_android_estagio_v1/models/linha.dart';
import 'package:teste_android_estagio_v1/views/bloc/sp_trans_bloc.dart';

class FiltroSheet extends StatefulWidget {
  const FiltroSheet({super.key});

  @override
  State<FiltroSheet> createState() => _FiltroSheetState();
}

class _FiltroSheetState extends State<FiltroSheet> {
  final _sheet = GlobalKey();
  final _controller = DraggableScrollableController();

  List<Linha> linhas = [];

  Timer? _timer;

  TextEditingController pesquisaController = TextEditingController();

  void onChangedPesquisa(String termo) {
    _timer?.cancel();
    _timer = Timer(const Duration(milliseconds: 500), () {
      context.read<SpTransBloc>().add(SpTransEventFiltroLinhasAlterado(termo));
    });
  }

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<SpTransBloc, SpTransState>(
      listener: (context, state) {
        if (state is SpTransStateLinhasFiltroCarregadas) {
          linhas = state.linhas;
        }
      },
      builder: (context, state) {
        return DraggableScrollableSheet(
          key: _sheet,
          initialChildSize: 0.4,
          maxChildSize: 0.5,
          minChildSize: 0.2,
          expand: true,
          controller: _controller,
          builder: (BuildContext context, ScrollController scrollController) {
            return DecoratedBox(
              decoration: const BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.only(
                  topLeft: Radius.circular(12),
                  topRight: Radius.circular(12),
                ),
              ),
              child: CustomScrollView(
                controller: scrollController,
                slivers: [
                  SliverList.list(
                    children: [
                      Align(
                        alignment: Alignment.topCenter,
                        child: Container(
                          width: 100,
                          margin: const EdgeInsets.symmetric(vertical: 10),
                          height: 10,
                          decoration: BoxDecoration(
                              color: const Color(0xFFD4D4D4),
                              borderRadius: BorderRadius.circular(32)),
                        ),
                      ),
                      const SizedBox(height: 10),
                      Container(
                        margin: const EdgeInsets.symmetric(horizontal: 10),
                        child: Material(
                          elevation: 2,
                          shadowColor: Colors.black26,
                          color: const Color(0xFFF6F6F6),
                          borderRadius: BorderRadius.circular(3),
                          child: TextField(
                            onChanged: onChangedPesquisa,
                            controller: pesquisaController,
                            textAlignVertical: TextAlignVertical.center,
                            decoration: const InputDecoration(
                              hintText: 'Pesquisar linhas',
                              prefixIcon: Icon(Icons.search),
                              contentPadding: EdgeInsets.symmetric(
                                  horizontal: 5, vertical: 0),
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                      ),
                      const SizedBox(height: 10),
                      Builder(builder: (context) {
                        if (linhas.isEmpty) {
                          if (pesquisaController.text.isEmpty) {
                            return const Center(
                              child: Text(
                                  "Nenhuma linha encontrada, digite algo para pesquisar."),
                            );
                          }
                          return const Center(
                            child: Text("Nenhuma linha encontrada."),
                          );
                        }
                        return Container(
                          padding: const EdgeInsets.symmetric(horizontal: 10),
                          width: double.infinity,
                          height: 130,
                          child: ListView.separated(
                            shrinkWrap: true,
                            itemCount: linhas.length,
                            scrollDirection: Axis.horizontal,
                            itemBuilder: (BuildContext context, int index) {
                              Linha linha = linhas[index];
                              return CardLinha(
                                linha: linha,
                              );
                            },
                            separatorBuilder:
                                (BuildContext context, int index) =>
                                    const SizedBox(
                              width: 5,
                            ),
                          ),
                        );
                      })
                    ],
                  ),
                ],
              ),
            );
          },
        );
      },
    );
  }
}

class CardLinha extends StatelessWidget {
  final Linha linha;
  const CardLinha({
    super.key,
    required this.linha,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 200,
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        color: const Color(0xFFE1ECFE),
        borderRadius: BorderRadius.circular(8),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 2,
            spreadRadius: 1,
          ),
        ],
      ),
      child: Column(
        children: [
          Row(
            children: [
              const Icon(Icons.flag, color: Colors.black54),
              Expanded(
                  child: Text(
                "Linha ${linha.id}",
                style: const TextStyle(
                    fontWeight: FontWeight.w600, color: Colors.black54),
              ))
            ],
          ),
          Row(
            children: [
              const Icon(Icons.directions_bus, color: Colors.black54),
              Expanded(
                  child: Text(
                linha.rotaLinha,
                style: const TextStyle(
                    fontWeight: FontWeight.w600, color: Colors.black54),
              ))
            ],
          ),
          Row(
            children: [
              const Icon(Icons.directions_bus, color: Colors.black54),
              Expanded(
                  child: Text(
                linha.letreiroCompleto,
                style: const TextStyle(
                    fontWeight: FontWeight.w600, color: Colors.black54),
              ))
            ],
          ),
        ],
      ),
    );
  }
}
