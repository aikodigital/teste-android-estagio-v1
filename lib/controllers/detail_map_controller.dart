import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:geolocator/geolocator.dart';
import 'package:get/get.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:mova_sp/models/bus_line.dart';
import 'package:mova_sp/repository/sptrans_repository.dart';

class DetailMapController extends GetxController {
  RxDouble lat = 0.0.obs;
  RxDouble long = 0.0.obs;
  String error = '';
  Set<Marker> markers = <Marker>{}.obs;
  late GoogleMapController _mapsController;
  var busLines = <BusLine>[].obs;

  get mapsController => _mapsController;

  static DetailMapController get to => Get.find<DetailMapController>();

  onMapCreated (
      GoogleMapController controller, BuildContext context, int busLineCode) async {
    _mapsController = controller;
    getPosition();
    await loadBusLines(context, busLineCode);
  }

  loadBusLines(BuildContext context, int busLineCode) async {
    final busLineDetail = await SpTransRepository.to.getArrivalTimeByBusLine(busLineCode);

    markers.clear();

    busLineDetail.forEach((busStop) async {
      markers.add(
        Marker(
          markerId: MarkerId('busStop_${busStop.stopName}'),
          position: LatLng(busStop.latitude, busStop.longitude),
          icon: await BitmapDescriptor.asset(
            const ImageConfiguration(
              size: Size(48, 48),
            ),
            'images/bus_stop.png',
          ),
          onTap: () => {
            
          },
        ),
      );

      busStop.vehicles.forEach((vehicle) async {
        markers.add(
          Marker(
            markerId: MarkerId('vehicle_${vehicle.prefix}'),
            position: LatLng(vehicle.latitude, vehicle.longitude),     
            icon: await BitmapDescriptor.asset(
            const ImageConfiguration(
              size: Size(48, 48),
            ),
            'images/bus.png',
          ),       
            onTap: () => {}
          ),
        );
      });
    });
  }

  getPosition() async {
    try {
      Position posicao = await _getCurrentPosition();
      lat.value = posicao.latitude;
      long.value = posicao.longitude;
      _mapsController
          .animateCamera(CameraUpdate.newLatLng(LatLng(lat.value, long.value)));
    } catch (e) {
      error = e.toString();
    }
  }

  Future<Position> _getCurrentPosition() async {
    LocationPermission permissao;

    bool ativado = await Geolocator.isLocationServiceEnabled();
    if (!ativado) {
      return Future.error('Por favor, habilite a localização no smartphone');
    }

    permissao = await Geolocator.checkPermission();
    if (permissao == LocationPermission.denied) {
      permissao = await Geolocator.requestPermission();
      if (permissao == LocationPermission.denied) {
        return Future.error('Você precisa autorizar o acesso à localização');
      }
    }

    if (permissao == LocationPermission.deniedForever) {
      return Future.error('Você precisa autorizar o acesso à localização');
    }

    return await Geolocator.getCurrentPosition();
  }
}
