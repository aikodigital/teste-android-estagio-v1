import 'package:flutter/material.dart';

class SearchTextFieldWidget extends StatelessWidget {
  final bool enabled;
  final String label;
  final Function(String) onChange;

  const SearchTextFieldWidget({
    super.key,
    required this.enabled,
    required this.label,
    required this.onChange,
  });

  @override
  Widget build(BuildContext context) {
    return Visibility(
      visible: enabled,
      child: Container(
        width: double.infinity,
        height: MediaQuery.of(context).size.height * 0.10,
        color: Theme.of(context).cardColor,
        child: Padding(
          padding: const EdgeInsets.symmetric(
            vertical: 8,
            horizontal: 20,
          ),
          child: TextField(
            onSubmitted: onChange,
            decoration: InputDecoration(
              icon: const Icon(Icons.search),
              label: Text(
                label,
              ),
            ),
          ),
        ),
      ),
    );
  }
}
