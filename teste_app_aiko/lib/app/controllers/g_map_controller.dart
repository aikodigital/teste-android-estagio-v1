import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:teste_app_aiko/app/models/stops_markers_model.dart';
import 'package:teste_app_aiko/app/repositories/stops_markers_repository.dart';

import '../models/lines_maker_model.dart';
import '../repositories/bus_markers_repository.dart';
import '../repositories/previsao_chegada_repository.dart';

class GMapController extends ChangeNotifier {
  var initialPosition = const CameraPosition(
      target: LatLng(-23.546235192469393, -46.66511154752894), zoom: 14);
  String error = '';
  MapLocationState mapLocationState = MapLocationState.loading;
  late GoogleMapController _mapController;
  final BusMarkersRepository busMarkersRepository;
  final StopsMarkersRepository stopsMarkersRepository;
  final PrevisaoChegadaRepository previsaoChegadaRepository;
  final Set<Marker> setMarkers = {};
  StopsMarkerModel? selectedStopMarker;

  GMapController(
    this.busMarkersRepository,
    this.stopsMarkersRepository,
    this.previsaoChegadaRepository,
  );

  onMapCreated(GoogleMapController gmc) async {
    _mapController = gmc;
  }

  void animateCameraTo(LatLng newLatLng) {
    _mapController.animateCamera(CameraUpdate.newLatLng(newLatLng));
  }

  void navToStop(StopsMarkerModel stop) {
    animateCameraTo(stop.localizacao);
    _onSelectStop(stop);
  }

  Future<void> fetchMarkers() async {
    try {
      await stopsMarkersRepository.fetchMarkers();
      // busMarkersRepository.fetchMarkers();
      _buildMarkers();
      mapLocationState = MapLocationState.success;
      notifyListeners();
    } catch (e) {
      mapLocationState = MapLocationState.success;
      error = 'Erro ao buscar dados!';
      notifyListeners();
    }
  }

  void _buildMarkers() {
    // busMarkersRepository.markers.forEach(_addBusMarkers);
    setMarkers.clear();
    stopsMarkersRepository.markers.forEach(_addStopsMarkers);
  }

  void _addBusMarkers(LinesMakerModel lMarker) {
    for (final bus in lMarker.veiculos) {
      setMarkers.add(
        Marker(
            icon: BitmapDescriptor.defaultMarkerWithHue(
                BitmapDescriptor.hueGreen),
            markerId: MarkerId(bus.cod.toString()),
            position: bus.localizacao),
      );
    }
  }

  void _addStopsMarkers(StopsMarkerModel sMarker,
      {bool disabbleOnTap = false}) {
    setMarkers.add(
      Marker(
          icon: BitmapDescriptor.defaultMarkerWithHue(BitmapDescriptor.hueBlue),
          markerId: MarkerId(sMarker.cod.toString()),
          position: sMarker.localizacao,
          onTap: disabbleOnTap
              ? null
              : () {
                  _onSelectStop(sMarker);
                  // onSelectStopMarker?.call(sMarker);
                }),
    );
  }

  Future<void> _onSelectStop(StopsMarkerModel sMarker) async {
    selectedStopMarker = sMarker;
    setMarkers.clear();
    _addStopsMarkers(sMarker, disabbleOnTap: true);
    final lines = await previsaoChegadaRepository.fetchBusesByLine(sMarker.cod);
    selectedStopMarker = selectedStopMarker!.copyWith(lines: lines);
    lines.forEach(_addBusMarkers);
    notifyListeners();
    print('Selecionou o marker de id ${sMarker.cod}');
  }

  void onCloseSelectedStop() {
    selectedStopMarker = null;
    _buildMarkers();
    notifyListeners();
  }
}

enum MapLocationState { loading, error, success }
