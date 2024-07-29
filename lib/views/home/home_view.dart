import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_map/flutter_map.dart';
import 'package:flutter_map_supercluster/flutter_map_supercluster.dart';
import 'package:latlong2/latlong.dart';
import 'package:teste_android_estagio_v1/utils/extensions.dart';
import 'package:teste_android_estagio_v1/views/bloc/sp_trans_bloc.dart';
import 'package:teste_android_estagio_v1/views/components/detalhes_ponto_dialog.dart';

import '../components/draggable_bottom_sheet.dart';

class HomeView extends StatefulWidget {
  const HomeView({super.key});

  @override
  State<HomeView> createState() => _HomeViewState();
}

class _HomeViewState extends State<HomeView> {
  final SpTransBloc _spTransBloc = SpTransBloc();

  void initBloc() {
    _spTransBloc.add(SpTransEventTelaIniciada());
  }

  Timer? _refreshTimer;

  void initRefreshTimer() {
    _refreshTimer = Timer.periodic(
        const Duration(seconds: 30),
        (timer) =>
            _spTransBloc.add(SpTransEventAtualizacaoVeiculosRequisitada()));
  }

  void addParadas(SpTransStateParadasCarregadas state) {
    List<Marker> marcadoresParadas = [];
    marcadoresParadas.clear();
    marcadoresParadas.addAll(state.paradas
        .map((parada) => Marker(
            key: Key("parada_${parada.id}"),
            width: 25,
            rotate: true,
            height: 25,
            point: LatLng(parada.latitude, parada.longitude),
            builder: (context) => Image.asset("assets/bmp/bus-stop.png")))
        .toList());
    _paradasClusterController.replaceAll(marcadoresParadas);
  }

  void addVeiculos(SpTransStateLinhasCarregadas state) {
    List<Marker> marcadoresVeiculos = [];
    marcadoresVeiculos.clear();
    marcadoresVeiculos.addAll(state.linhas
        .map((linha) => linha.veiculos!
            .map((veiculo) => Marker(
                key: Key("veiculo_${veiculo.prefixo}"),
                width: 40,
                height: 40,
                rotate: true,
                point: LatLng(veiculo.latitude, veiculo.longitude),
                builder: (context) => Image.asset("assets/bmp/bus.png")))
            .toList())
        .expand((element) => element)
        .toList());
    _veiculosClusterController.replaceAll(marcadoresVeiculos);
  }

  @override
  void initState() {
    initBloc();
    initRefreshTimer();
    super.initState();
  }

  @override
  void dispose() {
    _refreshTimer?.cancel();
    super.dispose();
  }

  void detalhesParada(Marker? marker) {
    if (marker != null) {
      final id =
          int.parse(marker.key.toString().replaceAll(RegExp(r'[^0-9.]'), ""));
      showDialog(
        context: context,
        builder: (context) => DetalhesPontoDialog(idParada: id),
      );
    }
  }

  final SuperclusterMutableController _veiculosClusterController =
      SuperclusterMutableController();
  final SuperclusterMutableController _paradasClusterController =
      SuperclusterMutableController();

  final String apiKey = "32e58cad-9b0e-4cbf-9b4e-94fc206f34ac";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: BlocProvider(
        create: (context) => _spTransBloc,
        child: BlocListener<SpTransBloc, SpTransState>(
          listener: (context, state) {
            if (state is SpTransStateErro) {
              ScaffoldMessenger.of(context).showSnackBarErro(state.mensagem);
            }
          },
          child: LayoutBuilder(
            builder: (BuildContext context, BoxConstraints constraints) {
              return Column(
                children: [
                  Expanded(
                    child: BlocConsumer<SpTransBloc, SpTransState>(
                      listener: (BuildContext context, SpTransState state) {
                        if (state is SpTransStateParadasCarregadas) {
                          addParadas(state);
                        }
                        if (state is SpTransStateLinhasCarregadas) {
                          addVeiculos(state);
                        }
                      },
                      builder: (context, state) {
                        return Stack(
                          alignment: Alignment.center,
                          children: [
                            FlutterMap(
                                options: MapOptions(
                                    maxZoom: 20,
                                    center:
                                        const LatLng(-23.548146, -46.642464)),
                                children: [
                                  TileLayer(
                                    maxNativeZoom: 20,
                                    maxZoom: 20,
                                    urlTemplate:
                                        'https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png?api_key={api_key}',
                                    additionalOptions: {"api_key": apiKey},
                                    // retinaMode: false,
                                  ),
                                  SuperclusterLayer.mutable(
                                    initialMarkers: const [],
                                    indexBuilder: IndexBuilders
                                        .computeWithOriginalMarkers,
                                    controller: _veiculosClusterController,
                                    calculateAggregatedClusterData: true,
                                    maxClusterRadius: 300,
                                    loadingOverlayBuilder: (context) {
                                      return Container();
                                    },
                                    minimumClusterSize: 3,
                                    clusterWidgetSize: const Size(40, 40),
                                    anchor: AnchorPos.align(AnchorAlign.center),
                                    builder: (context, position, markerCount,
                                        extraClusterData) {
                                      return Image.asset(
                                        "assets/bmp/bus-cluster.png",
                                        width: 60,
                                        height: 60,
                                      );
                                    },
                                  ),
                                  SuperclusterLayer.mutable(
                                    initialMarkers: const [],
                                    indexBuilder: IndexBuilders
                                        .computeWithOriginalMarkers,
                                    controller: _paradasClusterController,
                                    calculateAggregatedClusterData: true,
                                    minimumClusterSize: 3,
                                    maxClusterRadius: 100,
                                    loadingOverlayBuilder: (context) {
                                      return Container();
                                    },
                                    clusterWidgetSize: const Size(40, 40),
                                    anchor: AnchorPos.align(AnchorAlign.center),
                                    onMarkerTap: detalhesParada,
                                    builder: (context, position, markerCount,
                                        extraClusterData) {
                                      return Image.asset(
                                        "assets/bmp/bus-stop-cluster.png",
                                        width: 60,
                                        height: 60,
                                      );
                                    },
                                  ),
                                ]),
                            Visibility(
                                visible: state is SpTransStateCarregando,
                                child: const CircularProgressIndicator()),
                            Positioned(
                                bottom: 0,
                                child: ConstrainedBox(
                                    constraints: BoxConstraints(
                                        maxHeight: constraints.maxHeight,
                                        maxWidth: constraints.maxWidth),
                                    child: const FiltroSheet()))
                          ],
                        );
                      },
                    ),
                  )
                ],
              );
            },
          ),
        ),
      ),
    );
  }
}
