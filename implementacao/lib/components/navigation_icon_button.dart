import 'package:flutter/material.dart';

class NavigationIconButton extends StatelessWidget {
  final IconData icon;
  final bool isSelected;
  final VoidCallback onTap;
  final String text;

  const NavigationIconButton({
    super.key,
    required this.icon,
    required this.isSelected,
    required this.onTap,
    required this.text,
  });

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: GestureDetector(
        onTap: onTap,
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            Icon(
              icon,
              size: 30,
              color: isSelected ? const Color.fromARGB(255, 0, 140, 255) : Colors.white,
            ),
            Text(
              text,
              style: TextStyle(
                color: isSelected ? const Color.fromARGB(255, 0, 140, 255) : Colors.white,
                fontSize: 12,
              ),
            ),
          ],
        ),
      ),
    );
  }
}