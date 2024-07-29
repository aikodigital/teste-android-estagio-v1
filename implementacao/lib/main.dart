import 'package:flutter/material.dart';
import 'package:implementacao/bottom_navigation.dart';
import 'package:implementacao/pages/home_page.dart';
import 'package:implementacao/pages/info_linhas_page.dart';
import 'package:implementacao/pages/info_paradas_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: OlhoVivo(),
    );
  }
}

class OlhoVivo extends StatefulWidget {
  const OlhoVivo({super.key});

  @override
  State<StatefulWidget> createState() => _OlhoVivo();
}

class _OlhoVivo extends State<OlhoVivo> {
  int _pageIndex = 0;
  final PageController _pageController = PageController();

  void _onTabSelected(int index) {
    setState(() {
      _pageIndex = index;
    });
    _pageController.jumpToPage(index);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: PageView(
        controller: _pageController,
        physics: const NeverScrollableScrollPhysics(),
        onPageChanged: (index) {
          setState(() {
            _pageIndex = index;
          });
        },
        // ignore: prefer_const_literals_to_create_immutables
        children: <Widget>[
          const HomePage(),
          const InfoParadasPage(),
          // ignore: prefer_const_constructors
          InfoLinhasPage(),
        ],
      ),
      bottomNavigationBar: Bottomnavigationbar (
        pageIndex: _pageIndex,
        onTabSelected: _onTabSelected,
      ),
    );
  }
}