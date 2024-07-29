import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/auth/auth_store.dart';

class AppWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
      Modular.setInitialRoute('/splash');
    return MaterialApp.router(
      routeInformationParser: Modular.routeInformationParser,
      routerDelegate: Modular.routerDelegate,
      title: 'Public Transportation App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
    );
  }

}

