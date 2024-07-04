import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';

import '../tabs/curridors_tab.dart';
import '../tabs/search_line_tab.dart';
import '../tabs/map_tab.dart';
import '../tabs/search_stop_tab.dart';
import '../widgets/custom_drawer.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final Completer<GoogleMapController> _controller =
  Completer<GoogleMapController>();
  final _pageController = PageController();
  int page = 0;

  @override
  Widget build(BuildContext context) {
    return PageView(
      controller: _pageController,
      physics: const NeverScrollableScrollPhysics(),
      children: [
        Scaffold(
          appBar: AppBar(
            title: const Text("Linhas de Ônibus"),
            centerTitle: true,
            backgroundColor: Theme.of(context).primaryColor,
          ),
          drawer: CustomDrawer(pageController: _pageController),
          body: SearchLineTab(),
        ),
        Scaffold(
          appBar: AppBar(
            title: const Text('Veiculos no Mapa'),
            centerTitle: true,
            backgroundColor: Theme.of(context).primaryColor,
            actions: [
              IconButton(
                onPressed: () {
                  _pageController.jumpToPage(page);
                },
                icon: const Icon(
                  Icons.search,
                  size: 27,
                  color: Colors.black,
                ),
              ),
            ],
          ),
          body: MapTab(),
          drawer: CustomDrawer(
            pageController: _pageController,
          ),
        ),
        Scaffold(
          appBar: AppBar(
            title: const Text("Paradas"),
            centerTitle: true,
            backgroundColor: Theme.of(context).primaryColor,
          ),
          drawer: CustomDrawer(pageController: _pageController),
          body: SearchStopTab(),
        ),
        Scaffold(
          appBar: AppBar(
            title: const Text("Corredores"),
            centerTitle: true,
            backgroundColor: Theme.of(context).primaryColor,
          ),
          drawer: CustomDrawer(pageController: _pageController),
          body: CorridorTab(),
        ),
      ],
    );
  }
}
