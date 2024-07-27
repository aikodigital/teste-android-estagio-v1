import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/models/hall_model.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/hall_list_item.dart';
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
          List<HallModel> halls = snp.data!;

          halls.sort((h1, h2) {
            if (h1.code > h2.code) {
              return 1;
            } else if (h1.code < h2.code) {
              return -1;
            }

            return 0;
          });

          return ListView.builder(
            padding: const EdgeInsets.only(
              left: 20,
              right: 20,
              top: 8,
            ),
            itemCount: halls.length,
            itemBuilder: (ctx, i) => HallListItem(
              hall: halls[i],
            ),
          );
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
