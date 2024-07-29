part of 'sp_trans_bloc.dart';

@immutable
abstract class SpTransEvent {}

class SpTransEventTelaIniciada extends SpTransEvent {
  SpTransEventTelaIniciada();
}

class SpTransEventFiltroLinhasAlterado extends SpTransEvent {
  final String termo;
  SpTransEventFiltroLinhasAlterado(this.termo);
}

class SpTransEventFiltroParadasAlterado extends SpTransEvent {
  final String termo;
  SpTransEventFiltroParadasAlterado(this.termo);
}

class SpTransEventAtualizacaoVeiculosRequisitada extends SpTransEvent {
  SpTransEventAtualizacaoVeiculosRequisitada();
}
