import 'package:mobx/mobx.dart';
import 'package:sp_movement/app/modules/bus_lines/models/bus_line_model.dart';
import 'package:sp_movement/app/modules/bus_lines/repository/bus_line_repository.dart';

part 'bus_lines_store.g.dart';

class BusLinesStore = _BusLinesStore with _$BusLinesStore;

abstract class _BusLinesStore with Store {
  @observable
  String termSeach = '';

  @action
  void setTermSearch(String value) => termSeach = value;
  @observable
  int codeDirection = 0;

  @action
  void setCodeDirection(int value) => codeDirection = value;
  @observable
  List<BusLineModel> busLineList = [];

  @action
  Future<void> find() async {
    if (codeDirection == 0) {
      busLineList = await BusLineRepository.searchLine(termSeach);
    } else {
      busLineList =
          await BusLineRepository.searchLineByDirect(termSeach, codeDirection);
    }
  }
}
