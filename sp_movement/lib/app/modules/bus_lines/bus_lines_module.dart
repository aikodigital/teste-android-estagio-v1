import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/bus_lines/stores/bus_lines_store.dart';
import 'package:sp_movement/app/modules/bus_lines/view/bus_lines_view.dart';

class BusLinesModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => BusLinesStore());
  }

  @override
  void routes(r) {
    r.child(
      '/',
      child: (context) => BusLinesView(),
    );
  }
}
