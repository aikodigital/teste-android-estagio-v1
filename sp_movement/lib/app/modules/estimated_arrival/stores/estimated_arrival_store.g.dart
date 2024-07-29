// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'estimated_arrival_store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic, no_leading_underscores_for_local_identifiers

mixin _$EstimatedArrivalStore on _EstimatedArrivalStore, Store {
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

  @override
  String toString() {
    return '''

    ''';
  }
}
