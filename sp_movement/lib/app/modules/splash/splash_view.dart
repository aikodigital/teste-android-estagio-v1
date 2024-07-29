import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:sp_movement/app/modules/splash/splash_store.dart';

class SplashView extends StatefulWidget {
  const SplashView({super.key});

  @override
  State<SplashView> createState() => _SplashViewState();
}

class _SplashViewState extends State<SplashView> {
  var store = Modular.get<SplashStore>();

  @override
  void initState() {
    store.authenticate().then((value) {
      Modular.to.pushReplacementNamed(Modular.initialRoute);
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: MediaQuery.of(context).size.width,
      height: MediaQuery.of(context).size.height,
      child: Center(
        child: Container(
          width: 200,
          height: 200,
          color: Colors.amber,
          // child: Image.asset('assets/images/logo.png'),
        ),
      ),
    );
  }
}
