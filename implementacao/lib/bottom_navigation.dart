import 'package:flutter/material.dart';
import 'package:implementacao/components/navigation_icon_button.dart';

class Bottomnavigationbar extends StatelessWidget {
  final int pageIndex;
  final Function(int) onTabSelected;

  const Bottomnavigationbar({
    super.key,
    required this.pageIndex,
    required this.onTabSelected,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        const Divider(
          height: 0.1,
          thickness: 0.1,
        ),
        BottomAppBar(
          color: const Color(0xFF363636),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              NavigationIconButton(
                isSelected: pageIndex == 0,
                icon: pageIndex == 0 ? Icons.map : Icons.map_outlined,
                onTap: () => onTabSelected(0), 
                text: 'Mapa',
              ),
              NavigationIconButton(
                isSelected: pageIndex == 1,
                icon: pageIndex == 1 ? Icons.location_on : Icons.location_on_outlined,
                onTap: () => onTabSelected(1),
                text: 'Paradas',
              ),
              NavigationIconButton(
                isSelected: pageIndex == 2,
                icon: pageIndex == 2 ? Icons.linear_scale : Icons.linear_scale_outlined,
                onTap: () => onTabSelected(2),
                text: 'Linhas',
              ),
            ],
          ),
        ),
      ],
    );
  }
}
