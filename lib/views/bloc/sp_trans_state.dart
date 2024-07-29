part of 'sp_trans_bloc.dart';

@immutable
abstract class SpTransState {}

class SpTransInitial extends SpTransState {}

class SpTransStateErro extends SpTransState {
  final String mensagem;
  SpTransStateErro(this.mensagem);
}

class SpTransStateCarregando extends SpTransState {}

class SpTransStateLinhasCarregadas extends SpTransState {
  final List<LinhaVeiculo> linhas;
  SpTransStateLinhasCarregadas(this.linhas);
}

class SpTransStateLinhasFiltroCarregadas extends SpTransState {
  final List<Linha> linhas;
  SpTransStateLinhasFiltroCarregadas(this.linhas);
}

class SpTransStateParadasCarregadas extends SpTransState {
  final List<Parada> paradas;
  SpTransStateParadasCarregadas(this.paradas);
}
