// lib/widgets/filter_icon_widget.dart
// ignore_for_file: library_private_types_in_public_api

import 'package:flutter/material.dart';

class FilterIconWidget extends StatefulWidget {
  final int
      sentido; // 0 para todos, 1 para Terminal Principal, 2 para Terminal Secundário
  final ValueChanged<int> onFilterChanged;

  const FilterIconWidget({
    super.key,
    required this.sentido,
    required this.onFilterChanged,
  });

  @override
  _FilterIconWidgetState createState() => _FilterIconWidgetState();
}

class _FilterIconWidgetState extends State<FilterIconWidget> {
  void _showFilterOptions(BuildContext context) {
    int sentido = widget.sentido;

    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return StatefulBuilder(
          builder: (BuildContext context, StateSetter setState) {
            return Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'Filtros',
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),
                  RadioListTile<int>(
                    title: const Text('Todos'),
                    value: 0,
                    groupValue: sentido,
                    onChanged: (int? value) {
                      setState(() {
                        sentido = value ?? 0;
                      });
                      widget.onFilterChanged(value ?? 0);
                      Navigator.pop(context); 
                    },
                  ),
                  RadioListTile<int>(
                    title: const Text('Terminal Principal'),
                    value: 1,
                    groupValue: sentido,
                    onChanged: (int? value) {
                      setState(() {
                        sentido = value ?? 1;
                      });
                      widget.onFilterChanged(value ?? 1);
                      Navigator.pop(context); // Fechar o modal ao selecionar
                    },
                  ),
                  RadioListTile<int>(
                    title: const Text('Terminal Secundário'),
                    value: 2,
                    groupValue: sentido,
                    onChanged: (int? value) {
                      setState(() {
                        sentido = value ?? 2;
                      });
                      widget.onFilterChanged(value ?? 2);
                      Navigator.pop(context); 
                    },
                  ),
                ],
              ),
            );
          },
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return IconButton(
      icon: const Icon(Icons.filter_list),
      onPressed: () {
        _showFilterOptions(context);
      },
    );
  }
}
