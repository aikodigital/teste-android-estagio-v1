import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:teste_android_estagio_v1/models/exceptions/sptrans_exception.dart';
import 'package:teste_android_estagio_v1/models/filtro_pesquisa.dart';
import 'package:teste_android_estagio_v1/models/linha.dart';
import 'package:teste_android_estagio_v1/models/linha_veiculo.dart';
import 'package:teste_android_estagio_v1/models/parada.dart';

import '../../services/sptrans_service.dart';

part 'sp_trans_event.dart';
part 'sp_trans_state.dart';

class SpTransBloc extends Bloc<SpTransEvent, SpTransState> {
  final SPTransService _spTransService = SPTransService();
  final FiltroPesquisa _filtroPesquisa = FiltroPesquisa();

  List<LinhaVeiculo> _linhas = [];

  SpTransBloc() : super(SpTransInitial()) {
    on<SpTransEventTelaIniciada>((event, emit) async {
      emit(SpTransStateCarregando());
      await _spTransService.autenticar();
      await _pesquisarPosicoes(emit);
      await _pesquisarParadas(emit);
      await _pesquisarLinhasFiltrada(emit);
    });

    on<SpTransEventFiltroParadasAlterado>((event, emit) async {
      emit(SpTransStateCarregando());
      _filtroPesquisa.termoParada = event.termo;
      await _pesquisarParadas(emit);
    });

    on<SpTransEventAtualizacaoVeiculosRequisitada>((event, emit) async {
      await _pesquisarPosicoes(emit);
    });

    on<SpTransEventFiltroLinhasAlterado>(
      (event, emit) async {
        _filtroPesquisa.termoLinha = event.termo;
        await _pesquisarLinhasFiltrada(emit);
      },
    );
  }

  Future<void> _pesquisarLinhasFiltrada(Emitter emit) async {
    try {
      List<Linha> linhas =
          await _spTransService.getLinhas(_filtroPesquisa.termoLinha);
      emit(SpTransStateLinhasFiltroCarregadas(linhas));
    } on SpTransException catch (e) {
      emit(SpTransStateErro(e.message));
    } on Exception catch (e) {
      emit(SpTransStateErro("Erro inesperado ao buscar linhas: $e"));
    }
  }

  Future<void> _pesquisarPosicoes(Emitter emit) async {
    try {
      _linhas = await _spTransService.getPosicoesVeiculos(
          apenasPCD: _filtroPesquisa.apenasPCD);
      emit(SpTransStateLinhasCarregadas(_linhas));
    } on SpTransException catch (e) {
      emit(SpTransStateErro(e.message));
    } on Exception catch (e) {
      emit(SpTransStateErro("Erro inesperado ao buscar linhas: $e"));
    }
  }

  Future<void> _pesquisarParadas(Emitter emit) async {
    try {
      List<Parada> paradas =
          await _spTransService.getParadas(_filtroPesquisa.termoParada);
      emit(SpTransStateParadasCarregadas(paradas));
    } on SpTransException catch (e) {
      emit(SpTransStateErro(e.message));
    } on Exception catch (e) {
      emit(SpTransStateErro("Erro inesperado ao buscar paradas: $e"));
    }
  }
}
