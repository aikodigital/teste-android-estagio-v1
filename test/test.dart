// // import 'package:flutter/material.dart';
// // import 'package:flutter_map/flutter_map.dart';
// // import 'package:latlong2/latlong.dart';
// // import 'package:geocoding/geocoding.dart';
// // import 'second_page.dart';

// // void main() {
// //   runApp(MyApp());
// // }

// // class MyApp extends StatelessWidget {
// //   @override
// //   Widget build(BuildContext context) {
// //     return MaterialApp(
// //       title: 'Flutter Map Demo',
// //       theme: ThemeData(
// //         primarySwatch: Colors.blue,
// //         visualDensity: VisualDensity.adaptivePlatformDensity,
// //         textTheme: TextTheme(
// //           displayLarge: TextStyle(fontSize: 72.0, fontWeight: FontWeight.bold),
// //           headlineSmall: TextStyle(fontSize: 36.0, fontStyle: FontStyle.italic),
// //           bodyMedium: TextStyle(fontSize: 14.0, fontFamily: 'Hind'),
// //         ),
// //       ),
// //       home: MapScreen(),
// //     );
// //   }
// // }

// // class MapScreen extends StatefulWidget {
// //   @override
// //   _MapScreenState createState() => _MapScreenState();
// // }

// // class _MapScreenState extends State<MapScreen> {
// //   final TextEditingController _searchController = TextEditingController();
// //   final MapController _mapController = MapController();
// //   LatLng _currentLatLng = LatLng(-8.05428, -34.8813);

// //   Future<void> _searchLocation() async {
// //     try {
// //       List<Location> locations = await locationFromAddress(_searchController.text);
// //       if (locations.isNotEmpty) {
// //         setState(() {
// //           _currentLatLng = LatLng(locations.first.latitude, locations.first.longitude);
// //           _mapController.move(_currentLatLng, 15.0);
// //         });
// //       }
// //     } catch (e) {
// //       print("Erro ao procurar localização: $e");
// //     }
// //   }

// //   void navigateToSecondPage(BuildContext context) {
// //     Navigator.push(
// //       context,
// //       PageRouteBuilder(
// //         pageBuilder: (context, animation, secondaryAnimation) => SecondPage(),
// //         transitionsBuilder: (context, animation, secondaryAnimation, child) {
// //           const begin = Offset(1.0, 0.0);
// //           const end = Offset.zero;
// //           const curve = Curves.ease;

// //           var tween = Tween(begin: begin, end: end).chain(CurveTween(curve: curve));
// //           var offsetAnimation = animation.drive(tween);

// //           return SlideTransition(
// //             position: offsetAnimation,
// //             child: child,
// //           );
// //         },
// //       ),
// //     );
// //   }

// //   @override
// //   Widget build(BuildContext context) {
// //     return Scaffold(
// //       appBar: AppBar(
// //         title: Text('Flutter Map Demo'),
// //         actions: [
// //           IconButton(
// //             icon: Icon(Icons.map),
// //             onPressed: () {
// //               navigateToSecondPage(context);
// //             },
// //           ),
// //         ],
// //       ),
// //       drawer: Drawer(
// //         child: ListView(
// //           padding: EdgeInsets.zero,
// //           children: <Widget>[
// //             DrawerHeader(
// //               decoration: BoxDecoration(
// //                 color: Theme.of(context).primaryColor,
// //               ),
// //               child: Text(
// //                 'Menu',
// //                 style: TextStyle(
// //                   color: Colors.white,
// //                   fontSize: 24,
// //                 ),
// //               ),
// //             ),
// //             ListTile(
// //               leading: Icon(Icons.home),
// //               title: Text('Home'),
// //               onTap: () {
// //                 Navigator.pop(context);
// //               },
// //             ),
// //             ListTile(
// //               leading: Icon(Icons.map),
// //               title: Text('Second Page'),
// //               onTap: () {
// //                 navigateToSecondPage(context);
// //               },
// //             ),
// //           ],
// //         ),
// //       ),
// //       body: Column(
// //         children: [
// //           Padding(
// //             padding: const EdgeInsets.all(8.0),
// //             child: Row(
// //               children: [
// //                 Expanded(
// //                   child: TextField(
// //                     controller: _searchController,
// //                     decoration: InputDecoration(
// //                       hintText: 'Enter location',
// //                       border: OutlineInputBorder(),
// //                       prefixIcon: Icon(Icons.search),
// //                     ),
// //                   ),
// //                 ),
// //                 IconButton(
// //                   icon: Icon(Icons.search),
// //                   onPressed: _searchLocation,
// //                 ),
// //               ],
// //             ),
// //           ),
// //           Expanded(
// //             child: FlutterMap(
// //               mapController: _mapController,
// //               options: MapOptions(
// //                 center: _currentLatLng,
// //                 zoom: 15.0,
// //               ),
// //               children: [
// //                 TileLayer(
// //                   urlTemplate: 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
// //                   subdomains: ['a', 'b', 'c'],
// //                 ),
// //                 MarkerLayer(
// //                   markers: [
// //                     Marker(
// //                       point: _currentLatLng,
// //                       width: 80.0,
// //                       height: 80.0,
// //                       builder: (ctx) => Container(
// //                         child: IconButton(
// //                           icon: Icon(Icons.location_on),
// //                           color: Colors.red,
// //                           iconSize: 45.0,
// //                           onPressed: () {
// //                             showDialog(
// //                               context: ctx,
// //                               builder: (ctx) => AlertDialog(
// //                                 title: Text('Hello'),
// //                                 content: Text('This is the pop-up message.'),
// //                                 actions: [
// //                                   TextButton(
// //                                     onPressed: () {
// //                                       Navigator.of(ctx).pop();
// //                                     },
// //                                     child: Text('Close'),
// //                                   )
// //                                 ],
// //                               ),
// //                             );
// //                           },
// //                         ),
// //                       ),
// //                     ),
// //                   ],
// //                 ),
// //               ],
// //             ),
// //           ),
// //         ],
// //       ),
// //     );
// //   }
// // }
// // lib/main.dart
// import 'package:flutter/material.dart';
// import 'map_screen.dart';

// void main() {
//   runApp(MyApp());
// }

// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       title: 'Flutter Map Demo',
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//         visualDensity: VisualDensity.adaptivePlatformDensity,
//         textTheme: TextTheme(
//           displayLarge: TextStyle(fontSize: 72.0, fontWeight: FontWeight.bold),
//           headlineSmall: TextStyle(fontSize: 36.0, fontStyle: FontStyle.italic),
//           bodyMedium: TextStyle(fontSize: 14.0, fontFamily: 'Hind'),
//         ),
//       ),
//       home: MapScreen(),
//     );
//   }
// }
import 'package:flutter/material.dart';
import 'api_service.dart';

class AuthScreen extends StatefulWidget {
  const AuthScreen({super.key});

  @override
  _AuthScreenState createState() => _AuthScreenState();
}

class _AuthScreenState extends State<AuthScreen> {
  bool _isAuthenticated = false;
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _checkAuthentication();
  }

  Future<void> _checkAuthentication() async {
    final apiService = ApiService();
    final isAuthenticated = await apiService.authenticate();
    setState(() {
      _isAuthenticated = isAuthenticated;
      _isLoading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return Scaffold(
        appBar: AppBar(
          title: const Text('Autenticação'),
        ),
        body: const Center(child: CircularProgressIndicator()),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: const Text('Autenticação'),
      ),
      body: Center(
        child: _isAuthenticated
            ? const Text('Autenticação bem-sucedida!')
            : const Text('Falha na autenticação.'),
      ),
    );
  }
}

void main() {
  runApp(const MaterialApp(
    home: AuthScreen(),
  ));
}