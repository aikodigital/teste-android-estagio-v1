import 'dart:typed_data';
import 'dart:ui' as ui;
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/widgets.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:implementacao/models/parada.dart';
import 'package:implementacao/services/api.dart';
import 'package:implementacao/models/bus_position.dart';

enum Menu { onibus, paradas }

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  static const CameraPosition initialCameraPosition = CameraPosition(
    target: LatLng(-23.56052, -46.640308),
    zoom: 13,
  );
  final ApiOlhoVivo api = ApiOlhoVivo();
  final Set<Marker> _markers = {};
  BitmapDescriptor? _busIcon;
  BitmapDescriptor? _stopIcon;
  List<Parada> lstParadas = [];
  GoogleMapController? _controller;
  bool _isMapLoaded = false;

  @override
  void initState() {
    super.initState();
    _loadCustomMarkers();
  }

  Future<void> _loadCustomMarkers() async {
    final Uint8List busMarkerIcon =
        await _getBytesFromAsset('assets/images/bus_icon.png', 20);
    final Uint8List stopMarkerIcon =
        await _getBytesFromAsset('assets/images/stop_icon.png', 20);
    setState(() {
      _busIcon = BitmapDescriptor.bytes(busMarkerIcon);
      _stopIcon = BitmapDescriptor.bytes(stopMarkerIcon);
    });
  }

  Future<void> _loadBusPositions() async {
    List<BusPosition> busPositions = await api.obterPosicoesVeiculos();

    final List<Marker> busMarkers = busPositions.expand((busPosition) {
      return busPosition.listaOnibus.map((bus) {
        return Marker(
          markerId: MarkerId(bus.id.toString()),
          position: LatLng(bus.position!.latitude, bus.position!.longitude),
          icon: _busIcon ??
              BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
          infoWindow: InfoWindow(
            title: "Ônibus ${bus.id.toString()}",
            snippet: "Linha: ${busPosition.linhaOnibus.letreiro.toString()}",
          ),
        );
      });
    }).toList();

    setState(() {
      _markers.addAll(busMarkers);
    });
  }

  Future<void> _loadBusStops() async {
    lstParadas = await api.obterParadas('');

    final List<Marker> stopMarkers = lstParadas.map((stop) {
      return Marker(
        markerId: MarkerId(stop.idParada.toString()),
        position: LatLng(stop.position.latitude, stop.position.longitude),
        icon: _stopIcon ??
            BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueRed),
        infoWindow: InfoWindow(
          title: "PARADA ${stop.idParada}",
          snippet: stop.endereco,
        ),
      );
    }).toList();

    setState(() {
      _markers.addAll(stopMarkers);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: const Color(0xFF363636),
        title:
            const Text("Olho Vivo Api", style: TextStyle(color: Colors.white)),
        actions: [
          PopupMenuButton<Menu>(
            icon: const Icon(Icons.help, color: Colors.white),
            color: Colors.white,
            onSelected: (Menu item) {},
            offset: const Offset(0, 50),
            itemBuilder: (BuildContext context) => <PopupMenuEntry<Menu>>[
              PopupMenuItem<Menu>(
                value: Menu.onibus,
                child: ListTile(
                  title: Row(
                    children: [
                      const Text('Ônibus'),
                      const Spacer(),
                      SizedBox(
                          width: 30,
                          height: 30,
                          child: Image.asset('assets/images/bus_icon.png')),
                    ],
                  ),
                ),
              ),
              PopupMenuItem<Menu>(
                value: Menu.paradas,
                child: ListTile(
                  title: Row(
                    children: [
                      const Text('Paradas'),
                      const Spacer(),
                      SizedBox(
                          width: 30,
                          height: 30,
                          child: Image.asset('assets/images/stop_icon.png')),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
      body: Stack(
        children: [
          GoogleMap(
            initialCameraPosition: initialCameraPosition,
            markers: _markers,
            onMapCreated: (GoogleMapController controller) {
              _controller = controller;
              Future.wait([
                _loadBusStops(),
                _loadBusPositions(),
              ]).then((_) async {
                await Future.delayed(const Duration(seconds: 25));
                setState(() {
                  _isMapLoaded = true;
                });
              });
            },
          ),
          if (!_isMapLoaded)
            const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircularProgressIndicator(
                    color: Colors.black,
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  Text(
                    'Carregando, isso pode demorar um pouco. Por favor aguarde...',
                    textAlign: TextAlign.center,
                    style: TextStyle(color: Colors.black, fontWeight: FontWeight.bold),
                  )
                ],
              ),
            ),
        ],
      ),
    );
  }

  Future<Uint8List> _getBytesFromAsset(String path, int width) async {
    final ByteData data = await rootBundle.load(path);
    final ui.Codec codec = await ui
        .instantiateImageCodec(data.buffer.asUint8List(), targetWidth: width);
    final ui.FrameInfo fi = await codec.getNextFrame();
    return (await fi.image.toByteData(format: ui.ImageByteFormat.png))!
        .buffer
        .asUint8List();
  }
}
