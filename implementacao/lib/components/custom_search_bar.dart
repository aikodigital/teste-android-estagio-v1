import 'package:flutter/material.dart';

class CustomSearchBar extends StatefulWidget {
  final bool isExpanded;
  final VoidCallback onExpand;
  final VoidCallback onCollapse;
  final TextEditingController searchController;
  final FocusNode searchFocusNode;
  final ValueChanged<String> onSubmitted;

  const CustomSearchBar({
    required this.isExpanded,
    required this.onExpand,
    required this.onCollapse,
    required this.searchController,
    required this.searchFocusNode,
    required this.onSubmitted,
    super.key,
  });

  @override
  State<CustomSearchBar> createState() => _CustomSearchBarState();
}

class _CustomSearchBarState extends State<CustomSearchBar> {
  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: TextField(
            controller: widget.searchController,
            focusNode: widget.searchFocusNode,
            onSubmitted: widget.onSubmitted,
            style: const TextStyle(
              fontFamily: 'Instagram',
              color: Color(0xFFA8A8A8),
            ),
            decoration: const InputDecoration(
              prefixIcon: Icon(
                Icons.search,
                color: Color(0xFFA8A8A8),
              ),
              hintText: 'Pesquisar',
              hintStyle: TextStyle(
                color: Color(0xFFA8A8A8),
              ),
              filled: true,
              fillColor: Color(0xFF363636),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.all(Radius.circular(15)),
                borderSide: BorderSide.none,
              ),
            ),
          ),
        ),
        if (widget.isExpanded)
          TextButton(
            onPressed: () {
              widget.onCollapse();
            },
            child: const Text(
              'Cancelar',
              style: TextStyle(fontFamily: 'Instagram', fontSize: 18),
            ),
          ),
      ],
    );
  }
}