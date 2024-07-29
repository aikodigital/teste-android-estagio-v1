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
  static const LatLng initialCameraPosition = LatLng(-23.5505, -46.6333);
  final ApiOlhoVivo api = ApiOlhoVivo();
  Set<Marker> _markers = {};
  BitmapDescriptor? _busIcon;
  BitmapDescriptor? _stopIcon;
  List<Parada> lstParadas = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadCustomMarkers();
  }

  Future<void> _loadCustomMarkers() async {
    final Uint8List busMarkerIcon =
        await getBytesFromAsset('assets/images/bus_icon.png', 20);
    final Uint8List stopMarkerIcon =
        await getBytesFromAsset('assets/images/stop_icon.png', 20);
    setState(() {
      _busIcon = BitmapDescriptor.bytes(busMarkerIcon);
      _stopIcon = BitmapDescriptor.bytes(stopMarkerIcon);
    });
    await _loadBusPositions();
    await _loadBusStops();
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _loadBusPositions() async {
    List<BusPosition> busPositions = await api.obterPosicoesVeiculos();
    Set<Marker> markers = _markers;

    for (var busPosition in busPositions) {
      for (var bus in busPosition.listaOnibus) {
        markers.add(Marker(
          markerId: MarkerId(bus.id.toString()),
          position: LatLng(bus.position!.latitude, bus.position!.longitude),
          icon: _busIcon ??
              BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
          infoWindow: InfoWindow(
            title: "Ônibus ${bus.id.toString()}",
            snippet: "Linha: ${busPosition.linhaOnibus.letreiro.toString()}",
          ),
        ));
      }
    }

    setState(() {
      _markers = markers;
    });
  }

  Future<void> _loadBusStops() async {
    lstParadas = await api.obterParadas('');
    Set<Marker> markers = _markers;

    for (var stop in lstParadas) {
      markers.add(Marker(
        markerId: MarkerId(stop.idParada.toString()),
        position: LatLng(stop.position.latitude, stop.position.longitude),
        icon: _stopIcon ??
            BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueRed),
        infoWindow: InfoWindow(
          title: "PARADA ${stop.idParada}",
          snippet: stop.endereco,
        ),
      ));
    }

    setState(() {
      _markers = markers;
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
          PopupMenuButton(
            icon: const Icon(
              Icons.help,
              color: Colors.white,
            ),
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
                        child: Image.asset('assets/images/bus_icon.png'),
                      ),
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
                        child: Image.asset('assets/images/stop_icon.png'),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
      body: _isLoading
          ? const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircularProgressIndicator(
                    color: Colors.black,
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Text(
                    'Carregando isso pode demorar um pouco, por favor aguarde...',
                    style: TextStyle(color: Colors.black),
                    textAlign: TextAlign.center,
                  ),
                ],
              ),
            )
          : GoogleMap(
              initialCameraPosition: const CameraPosition(
                target: initialCameraPosition,
                zoom: 13,
              ),
              markers: _markers,
            ),
    );
  }
}

Future<Uint8List> getBytesFromAsset(String path, int width) async {
  ByteData data = await rootBundle.load(path);
  ui.Codec codec = await ui.instantiateImageCodec(data.buffer.asUint8List(),
      targetWidth: width);
  ui.FrameInfo fi = await codec.getNextFrame();
  return (await fi.image.toByteData(format: ui.ImageByteFormat.png))!
      .buffer
      .asUint8List();
}
