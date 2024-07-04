import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:provider/provider.dart';

import '../models/bus_stop_model.dart';
import '../services/olho_vivo_service.dart';

class SearchStopTab extends StatefulWidget {
  @override
  _SearchStopTabState createState() => _SearchStopTabState();
}

class _SearchStopTabState extends State<SearchStopTab> {
  GoogleMapController? _mapController;
  final TextEditingController _searchController = TextEditingController();
  final Set<Marker> _markers = {};

  Future<void> _searchStops(String query, String searchType) async {
    final api = Provider.of<OlhoVivoService>(context, listen: false);
    List<dynamic> stops = [];

    switch (searchType) {
      case 'name':
      case 'address':
        stops = await api.searchStops(query);
        break;
      case 'line':
        stops = await api.searchStopsByLine(int.parse(query));
        break;
      case 'corridor':
        stops = await api.searchStopsByCorridor(int.parse(query));
        break;
    }

    if (stops.isNotEmpty) {
      setState(() {
        _markers.clear();
        for (var stop in stops) {
          _markers.add(
            Marker(
              markerId: MarkerId(stop['cp'].toString()),
              position: LatLng(stop['py'], stop['px']),
              infoWindow: InfoWindow(
                title: stop['np'],
                snippet: stop['ed'],
              ),
              onTap: () => _showBusArrivals(stop['cp']),
            ),
          );
        }
      });

      // Move a câmera para o primeiro resultado da busca
      _mapController?.animateCamera(
        CameraUpdate.newLatLngZoom(
          LatLng(stops[0]['py'], stops[0]['px']),
          15.0,
        ),
      );
    }
  }

  Future<void> _showBusArrivals(int stopId) async {
    final api = Provider.of<OlhoVivoService>(context, listen: false);
    List<BusArrivalModel>? arrivals = [];

    try {
      arrivals = await api.getBusArrivals(stopId);

      showDialog(
        context: context,
        builder: (BuildContext context) {
          return SizedBox(
            height: 100,
            child: SingleChildScrollView(
              scrollDirection: Axis.vertical,
              child: AlertDialog(
                backgroundColor: Color.fromARGB(255, 214, 232, 243),
                title: Text('Previsões de Chegada'),
                content: arrivals == null || arrivals.isEmpty
                    ? Text('Nenhuma previsão disponível.')
                    : Column(
                  mainAxisSize: MainAxisSize.min,
                  children: arrivals.map<Widget>((arrival) {
                    return ListTile(
                      title: Text('Prefixo: ${arrival.prefix}'),
                      subtitle: Text('Previsão: ${arrival.arrivalTime}'),
                    );
                  }).toList(),
                ),
                actions: [
                  TextButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text('Fechar'),
                  ),
                ],
              ),
            ),
          );
        },
      );
    } catch (e) {
      print('Error getting bus arrivals: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          GoogleMap(
            initialCameraPosition: const CameraPosition(
              target: LatLng(-23.5505, -46.6333),
              zoom: 12,
            ),
            markers: _markers,
            onMapCreated: (controller) => _mapController = controller,
          ),
          Positioned(
            top: 0,
            left: 0,
            right: 0,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: TextField(
                controller: _searchController,
                decoration: const InputDecoration(
                  labelText: 'Buscar por nome, endereço, linha ou corredor',
                  border: OutlineInputBorder(
                      borderRadius: BorderRadius.all(Radius.circular(20.0))),
                  prefixIcon: Icon(Icons.search),
                  filled: true,
                  fillColor: Colors.white,
                ),
                onSubmitted: (value) => _searchStops(
                    value, 'name'), // Exemplo de uso ao submeter o texto
              ),
            ),
          ),
          Positioned(
            top: 60,
            // Ajuste de posição conforme necessário para evitar sobreposição com o TextField
            left: 0,
            right: 0,
            child: SizedBox(
              height: 80,
              child: SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    ElevatedButton(
                        onPressed: () {
                          _searchStops(_searchController.text, 'name');
                          FocusScope.of(context).requestFocus(FocusNode());
                        },
                        style: ElevatedButton.styleFrom(
                            backgroundColor:
                            Color.fromRGBO(255, 214, 232, 243)),
                        child: const Text(
                          'Nome',
                          style: TextStyle(color: Colors.white),
                        )),
                    const SizedBox(
                      width: 3,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        _searchStops(_searchController.text, 'address');
                        FocusScope.of(context).requestFocus(FocusNode());
                      },
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Color.fromRGBO(255, 214, 232, 243)),
                      child: const Text(
                        'Endereço',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                    const SizedBox(
                      width: 3,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        _searchStops(_searchController.text, 'line');
                        FocusScope.of(context).requestFocus(FocusNode());
                      },
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Color.fromRGBO(255, 214, 232, 243)),
                      child: const Text(
                        'Linha',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                    const SizedBox(
                      width: 3,
                    ),
                    ElevatedButton(
                      onPressed: () {
                        _searchStops(_searchController.text, 'corridor');
                        FocusScope.of(context).requestFocus(FocusNode());
                      },
                      style: ElevatedButton.styleFrom(
                          backgroundColor: Color.fromRGBO(255, 214, 232, 243)),
                      child: const Text(
                        'Corredor',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
