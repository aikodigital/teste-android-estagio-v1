import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/providers/data_provider.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:provider/provider.dart';

import 'widgets/home_widget.dart';

void main() {
  runApp(const OlhoVivoSp());
}

class OlhoVivoSp extends StatelessWidget {
  const OlhoVivoSp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<DataProvider>(
      create: (ctx) {
        final dataProvider = DataProvider(
          apiService: ApiService(),
        );
        dataProvider.authenticate();

        return dataProvider;
      },
      child: MaterialApp(
        title: 'OlhoVivoSP',
        theme: ThemeData.from(
          colorScheme: ColorScheme.fromSeed(
            seedColor: Colors.amber,
          ),
        ),
        home: const Home(),
      ),
    );
  }
}
