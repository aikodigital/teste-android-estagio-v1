import 'package:flutter/material.dart';
import 'package:teste_android_estagio_v1/models/parada_previsao.dart';
import 'package:teste_android_estagio_v1/models/veiculo.dart';
import 'package:teste_android_estagio_v1/models/veiculo_previsao_dto.dart';
import 'package:teste_android_estagio_v1/services/sptrans_service.dart';

class DetalhesPontoDialog extends StatefulWidget {
  final int idParada;
  const DetalhesPontoDialog({super.key, required this.idParada});

  @override
  State<DetalhesPontoDialog> createState() => _DetalhesPontoDialogState();
}

class _DetalhesPontoDialogState extends State<DetalhesPontoDialog> {
  final SPTransService _spTransService = SPTransService();

  Future<void> getParadaPrevisao() async {
    try {
      parada = await _spTransService.getPrevisaoParada(widget.idParada);
      loaded = true;
      if (!mounted) return;
      setState(() {});
    } catch (e) {
      loaded = true;
      errorMessage = e.toString();
      setState(() {});
    }
  }

  ParadaPrevisao? parada;

  bool loaded = false;
  bool apenasPCD = false;
  String? errorMessage;

  @override
  void initState() {
    getParadaPrevisao();
    super.initState();
  }

  List<VeiculoPrevisaoDTO> get veiculos {
    List<VeiculoPrevisaoDTO> veiculos = parada!.veiculosPrevisao;
    if (apenasPCD) {
      veiculos.removeWhere((veiculo) => veiculo.acessivelPCD == false);
    }
    return veiculos;
  }

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Container(
        width: 400,
        height: 300,
        padding: const EdgeInsets.all(10),
        child: Material(
          borderRadius: BorderRadius.circular(5),
          child: Column(
            children: [
              Text("Parada nº ${widget.idParada}"),
              Expanded(
                child: Padding(
                  padding: const EdgeInsets.all(10),
                  child: Builder(
                    builder: (context) {
                      if (!loaded) {
                        return const Center(
                          child: SizedBox(
                            width: 40,
                            height: 40,
                            child: CircularProgressIndicator(),
                          ),
                        );
                      }
                      if (errorMessage != null) {
                        return Text(errorMessage!);
                      }
                      if (parada == null) {
                        return const Text(
                            "Não existe previsão para esta parada");
                      }
                      return Column(
                        children: [
                          Row(
                            children: [
                              const Icon(Icons.flag),
                              Expanded(child: Text(parada!.nome)),
                            ],
                          ),
                          Row(
                            children: [
                              const Icon(Icons.accessible),
                              const Text("Apenas PCD"),
                              const SizedBox(
                                width: 10,
                              ),
                              Switch.adaptive(
                                value: apenasPCD,
                                onChanged: (value) {
                                  setState(() {
                                    apenasPCD = value;
                                  });
                                },
                              ),
                            ],
                          ),
                          Expanded(
                            child: ListView.separated(
                              itemCount: veiculos.length,
                              shrinkWrap: true,
                              separatorBuilder:
                                  (BuildContext context, int index) {
                                return const SizedBox(
                                  height: 5,
                                );
                              },
                              itemBuilder: (BuildContext context, int index) {
                                VeiculoPrevisaoDTO veiculo = veiculos[index];
                                return Container(
                                  width: double.infinity,
                                  padding: const EdgeInsets.all(10),
                                  decoration: BoxDecoration(
                                      color: Colors.white,
                                      boxShadow: [
                                        BoxShadow(
                                          color: Colors.grey.withOpacity(0.5),
                                          spreadRadius: 1,
                                          blurRadius: 1,
                                          offset: const Offset(0, 1),
                                        ),
                                      ],
                                      borderRadius: BorderRadius.circular(3)),
                                  child: Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    mainAxisSize: MainAxisSize.min,
                                    children: [
                                      Row(
                                        children: [
                                          const Icon(Icons.directions_bus),
                                          Expanded(
                                              child: Text(
                                                  veiculo.letreiroCompleto)),
                                          Visibility(
                                              visible: veiculo.acessivelPCD,
                                              child: const Row(
                                                children: [
                                                  Text("Acessível para PCD"),
                                                  Icon(Icons.accessible),
                                                ],
                                              )),
                                        ],
                                      ),
                                      const SizedBox(
                                        height: 5,
                                      ),
                                      Text(
                                          "${veiculo.origem} ➡ ${veiculo.destino}"),
                                      Row(
                                        children: [
                                          const Icon(Icons.access_time),
                                          const SizedBox(
                                            width: 5,
                                          ),
                                          Text(veiculo.previsaoRelativa),
                                        ],
                                      )
                                    ],
                                  ),
                                );
                              },
                            ),
                          )
                        ],
                      );
                    },
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
