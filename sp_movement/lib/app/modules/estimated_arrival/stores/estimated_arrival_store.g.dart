// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'estimated_arrival_store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic, no_leading_underscores_for_local_identifiers

mixin _$EstimatedArrivalStore on _EstimatedArrivalStore, Store {
  late final _$stopModelAtom =
      Atom(name: '_EstimatedArrivalStore.stopModel', context: context);

  @override
  StopModel? get stopModel {
    _$stopModelAtom.reportRead();
    return super.stopModel;
  }

  @override
  set stopModel(StopModel? value) {
    _$stopModelAtom.reportWrite(value, super.stopModel, () {
      super.stopModel = value;
    });
  }

  late final _$busStopPointsAtom =
      Atom(name: '_EstimatedArrivalStore.busStopPoints', context: context);

  @override
  List<String> get busStopPoints {
    _$busStopPointsAtom.reportRead();
    return super.busStopPoints;
  }

  @override
  set busStopPoints(List<String> value) {
    _$busStopPointsAtom.reportWrite(value, super.busStopPoints, () {
      super.busStopPoints = value;
    });
  }

  late final _$fetchStopPointsAsyncAction =
      AsyncAction('_EstimatedArrivalStore.fetchStopPoints', context: context);

  @override
  Future<void> fetchStopPoints() {
    return _$fetchStopPointsAsyncAction.run(() => super.fetchStopPoints());
  }

  late final _$findEstimatedArrivalByStopPointAsyncAction = AsyncAction(
      '_EstimatedArrivalStore.findEstimatedArrivalByStopPoint',
      context: context);

  @override
  Future<void> findEstimatedArrivalByStopPoint(int stopId) {
    return _$findEstimatedArrivalByStopPointAsyncAction
        .run(() => super.findEstimatedArrivalByStopPoint(stopId));
  }

  late final _$_EstimatedArrivalStoreActionController =
      ActionController(name: '_EstimatedArrivalStore', context: context);

  @override
  void setStopModel(StopModel value) {
    final _$actionInfo = _$_EstimatedArrivalStoreActionController.startAction(
        name: '_EstimatedArrivalStore.setStopModel');
    try {
      return super.setStopModel(value);
    } finally {
      _$_EstimatedArrivalStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    return '''
stopModel: ${stopModel},
busStopPoints: ${busStopPoints}
    ''';
  }
}
