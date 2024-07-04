import 'package:flutter/material.dart';

import '../tiles/drawer_tile.dart';

class CustomDrawer extends StatelessWidget {
  const CustomDrawer({super.key, required this.pageController});

  final PageController pageController;

  @override
  Widget build(BuildContext context) {
    Widget _buildDrawerBack() => Container(
      decoration: const BoxDecoration(
        gradient: LinearGradient(
            colors: [Color.fromARGB(255, 203, 236, 241), Colors.white],
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter),
      ),
    );

    return Drawer(
      child: Stack(
        children: [
          _buildDrawerBack(),
          ListView(
            padding: const EdgeInsets.only(left: 32.0, top: 16.0),
            children: [
              Container(
                margin: const EdgeInsets.only(bottom: 0.0),
                padding: const EdgeInsets.fromLTRB(0.0, 16.0, 16.0, 8.0),
                height: 170.0,
                child: const Stack(
                  children: [
                    Positioned(
                      top: 8.0,
                      left: 0.0,
                      child: Text(
                        "Transporte\n Público SP",
                        style: TextStyle(
                            fontSize: 34.0, fontWeight: FontWeight.bold),
                      ),
                    ),
                  ],
                ),
              ),
              Divider(),
              DrawerTile(
                icon: Icons.search_outlined,
                text: "Pesquisar Linhas",
                controller: pageController,
                page: 0,
              ),
              DrawerTile(
                icon: Icons.location_on,
                text: "Veículos no mapa",
                controller: pageController,
                page: 1,
              ),
              DrawerTile(
                icon: Icons.bus_alert,
                text: "Paradas",
                controller: pageController,
                page: 2,
              ),
              DrawerTile(
                icon: Icons.alt_route,
                text: "Corredores",
                controller: pageController,
                page: 4,
              ),
            ],
          )
        ],
      ),
    );
  }
}
