import 'package:flutter/material.dart';

class BusStopListItemInfo extends StatelessWidget {
  final AsyncSnapshot<Object?> snp;

  const BusStopListItemInfo({super.key, required this.snp});

  @override
  Widget build(BuildContext context) {
    if (snp.hasData && snp.connectionState == ConnectionState.done) {
      print(snp.data.toString());
      return Center(
        child: Text(snp.data.toString()),
      );
    }

    if (snp.hasError && snp.connectionState == ConnectionState.done) {
      return Center(
        child: Text(snp.error.toString()),
      );
    }

    return const Center(
      child: CircularProgressIndicator(),
    );
  }
}
