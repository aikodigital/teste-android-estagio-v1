import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/estimated_arrival/stores/estimated_arrival_store.dart';
import 'package:sp_movement/app/modules/estimated_arrival/view/estimated_arrival_view.dart';


class EstimatedArrivalModule extends Module {
  @override
  void binds(i) {
    i.addLazySingleton(() => EstimatedArrivalStore());
  }

  @override
  void routes(r) {
    r.child(
      '/',
      child: (context) => EstimatedArrivalView(),
    );
  }
}
