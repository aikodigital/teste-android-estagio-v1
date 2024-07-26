import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';

class BottomNavigationBarComponent extends StatefulWidget {
  @override
  _BottomNavigationBarComponentState createState() => _BottomNavigationBarComponentState();
}

class _BottomNavigationBarComponentState extends State<BottomNavigationBar> {
  int _selectedIndex = 1;
  static const List<Widget> _widgetOptions = <Widget>[
    Text('Vehicle Positions'),
    Text('Bus Routes'),
    Text('Bus Stops'),
    Text('Arrival Predictions'),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      switch (index) {
        case 0:
          Modular.to.navigate('/');
          break;
        case 1:
          Modular.to.navigate('/bus-routes');
          break;
        case 2:
          Modular.to.navigate('/bus-stops');
          break;
        case 3:
          Modular.to.navigate('/arrival-predictions');
          break;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Public Transportation App'),
      ),
      body: Center(
        child: _widgetOptions.elementAt(_selectedIndex),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.directions_bus),
            label: 'Vehicle Positions',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.line_style),
            label: 'Bus Routes',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.location_on),
            label: 'Bus Stops',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.schedule),
            label: 'Arrival Predictions',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ),
    );
  }
}
