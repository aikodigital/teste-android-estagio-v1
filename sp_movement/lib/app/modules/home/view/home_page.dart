import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:persistent_bottom_nav_bar/persistent_bottom_nav_bar.dart';
import 'package:sp_movement/app/modules/bus_lines/view/bus_lines_view.dart';
import 'package:sp_movement/app/modules/vehicle_position/%20view/vehicle_position_view.dart';
import 'package:sp_movement/app/modules/vehicle_position/stores/vehicle_position_store.dart';
import 'package:sp_movement/app/modules/vehicle_position/vehicle_position_module.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  late PersistentTabController _controller;
  // var store = Modular.get<VehiclePositionStore>();

  @override
  void initState() {
    super.initState();
    _controller = PersistentTabController(initialIndex: 0);
  }

  List<Widget> _buildScreens() {
    return [
      VehiclePositionsView(), // HomeModule
      BusLinesView(), // HomeModule
      VehiclePositionsView(), // HomeModule
    ];
  }

  List<PersistentBottomNavBarItem> _navBarsItems() {
    return [
      PersistentBottomNavBarItem(
        icon: Icon(Icons.home),
        title: ("Posição"),
        activeColorPrimary: Colors.blue,
        inactiveColorPrimary: Colors.grey,
      ),
      PersistentBottomNavBarItem(
        icon: Icon(Icons.settings),
        title: ("Lista"),
        activeColorPrimary: Colors.blue,
        inactiveColorPrimary: Colors.grey,
      ),
      PersistentBottomNavBarItem(
        icon: Icon(Icons.settings),
        title: ("Previsão"),
        activeColorPrimary: Colors.blue,
        inactiveColorPrimary: Colors.grey,
      ),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return PersistentTabView(
      context,
      controller: _controller,
      screens: _buildScreens(),
      items: _navBarsItems(),
      confineToSafeArea: true,
      backgroundColor: Colors.white, // Default is Colors.white.
      handleAndroidBackButtonPress: true, // Default is true.
      resizeToAvoidBottomInset: true, // This needs to be true if you want to move up the screen when keyboard appears. Default is true.
      stateManagement: true, // Default is true.
      hideNavigationBarWhenKeyboardAppears: true, // Recommended to set 'true' to hide the navigation bar when keyboard appears. Default is true.
      decoration: NavBarDecoration(
        borderRadius: BorderRadius.circular(10.0),
        colorBehindNavBar: Colors.white,
      ),
      popBehaviorOnSelectedNavBarItemPress: PopBehavior.all,
      animationSettings: const  NavBarAnimationSettings(
        navBarItemAnimation: ItemAnimationSettings(
          duration: Duration(milliseconds: 400),
          curve: Curves.ease,
        ),
        ),
      navBarHeight: kBottomNavigationBarHeight,
      navBarStyle: NavBarStyle.style1, // Choose the nav bar style with this property.
    );
  }
}