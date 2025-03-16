// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'bus_lines_store.dart';

// **************************************************************************
// StoreGenerator
// **************************************************************************

// ignore_for_file: non_constant_identifier_names, unnecessary_brace_in_string_interps, unnecessary_lambdas, prefer_expression_function_bodies, lines_longer_than_80_chars, avoid_as, avoid_annotating_with_dynamic, no_leading_underscores_for_local_identifiers

mixin _$BusLinesStore on _BusLinesStore, Store {
  late final _$termSeachAtom =
      Atom(name: '_BusLinesStore.termSeach', context: context);

  @override
  String get termSeach {
    _$termSeachAtom.reportRead();
    return super.termSeach;
  }

  @override
  set termSeach(String value) {
    _$termSeachAtom.reportWrite(value, super.termSeach, () {
      super.termSeach = value;
    });
  }

  late final _$codeDirectionAtom =
      Atom(name: '_BusLinesStore.codeDirection', context: context);

  @override
  int get codeDirection {
    _$codeDirectionAtom.reportRead();
    return super.codeDirection;
  }

  @override
  set codeDirection(int value) {
    _$codeDirectionAtom.reportWrite(value, super.codeDirection, () {
      super.codeDirection = value;
    });
  }

  late final _$busLineListAtom =
      Atom(name: '_BusLinesStore.busLineList', context: context);

  @override
  List<BusLineModel> get busLineList {
    _$busLineListAtom.reportRead();
    return super.busLineList;
  }

  @override
  set busLineList(List<BusLineModel> value) {
    _$busLineListAtom.reportWrite(value, super.busLineList, () {
      super.busLineList = value;
    });
  }

  late final _$findAsyncAction =
      AsyncAction('_BusLinesStore.find', context: context);

  @override
  Future<void> find() {
    return _$findAsyncAction.run(() => super.find());
  }

  late final _$_BusLinesStoreActionController =
      ActionController(name: '_BusLinesStore', context: context);

  @override
  void setTermSearch(String value) {
    final _$actionInfo = _$_BusLinesStoreActionController.startAction(
        name: '_BusLinesStore.setTermSearch');
    try {
      return super.setTermSearch(value);
    } finally {
      _$_BusLinesStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  void setCodeDirection(int value) {
    final _$actionInfo = _$_BusLinesStoreActionController.startAction(
        name: '_BusLinesStore.setCodeDirection');
    try {
      return super.setCodeDirection(value);
    } finally {
      _$_BusLinesStoreActionController.endAction(_$actionInfo);
    }
  }

  @override
  String toString() {
    return '''
termSeach: ${termSeach},
codeDirection: ${codeDirection},
busLineList: ${busLineList}
    ''';
  }
}
