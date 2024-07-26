import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:provider/provider.dart';

class HallList extends StatelessWidget {
  const HallList({super.key});

  @override
  Widget build(BuildContext context) {
    final dataProvider = Provider.of<ApiService>(context);

    return FutureBuilder(
      future: dataProvider.getHalls(),
      builder: (ctx, snp) {
        if (snp.hasData && snp.connectionState != ConnectionState.active) {
          return const Text('bom');
        }

        if (snp.hasError && snp.connectionState != ConnectionState.active) {
          return Center(
            child: Text(
              snp.error.toString(),
            ),
          );
        }

        return const Center(child: CircularProgressIndicator());
      },
    );
  }
}
