import 'package:flutter/material.dart';
import 'package:olho_vivo_sp/services/api_service.dart';
import 'package:olho_vivo_sp/widgets/hall_list_item.dart';
import 'package:provider/provider.dart';

class HallList extends StatelessWidget {
  const HallList({super.key});

  @override
  Widget build(BuildContext context) {
    Provider.of<ApiService>(
      context,
      listen: false,
    ).getHalls();

    return Consumer<ApiService>(
      builder: (context, apiService, child) => apiService.halls.isEmpty
          ? const Center(
              child: CircularProgressIndicator(),
            )
          : ListView.builder(
              padding: const EdgeInsets.only(
                left: 20,
                right: 20,
                top: 8,
              ),
              itemCount: apiService.halls.length,
              itemBuilder: (ctx, i) => HallListItem(
                hall: apiService.halls[i],
              ),
            ),
    );
  }
}
