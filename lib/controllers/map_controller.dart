import 'package:flutter/material.dart';
import 'package:geolocator/geolocator.dart';
import 'package:get/get.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:mova_sp/models/bus_line.dart';
import 'package:mova_sp/repository/sptrans_repository.dart';
import 'package:mova_sp/widgets/bus_stop_detail_wrap.dart';

class MapController extends GetxController{
  RxDouble lat = 0.0.obs;
  RxDouble long = 0.0.obs;
  String error = '';
  Set<Marker> markers = <Marker>{}.obs;
  late GoogleMapController _mapsController;
  var busLines = <BusLine>[].obs;

  get mapsController => _mapsController;

  static MapController get to => Get.find<MapController>();

  onMapCreated(GoogleMapController controller,BuildContext context) {
    _mapsController = controller;
    getPosition();
    loadBusLines(context);    
  }

  loadBusLines(BuildContext context) async {
    busLines.value = await SpTransRepository.to.getAllBusLine();
    final busStops = await SpTransRepository.to.getAllBusStop();
    busStops.forEach((busStop) async {            
      markers.add(
        Marker(
          markerId: MarkerId(busStop.name),
          position: LatLng(busStop.latitude, busStop.longitude),                    
          icon: await BitmapDescriptor.asset(
            const ImageConfiguration(
              size: Size(48, 48),
            ),
            'images/bus_stop.png',
          ),
          onTap: () => {
            showModalBottomSheet(
              isScrollControlled: true,
              context: context,
              builder: (context) => BusStopDetailWrap(busStop: busStop),
            )
          },
        ),
      );
    });
  }  

  getPosition() async {
    try {
      Position posicao = await _getCurrentPosition();
      lat.value = posicao.latitude;
      long.value = posicao.longitude;
      _mapsController.animateCamera(CameraUpdate.newLatLng(LatLng(lat.value, long.value)));
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