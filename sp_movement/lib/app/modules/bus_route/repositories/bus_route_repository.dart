



import 'package:dio/dio.dart';
import 'package:sp_movement/app/core/repository/app_repository.dart';

class BusRouteRepository extends AppRepository {
   final endpoint = "/Posicao";

   static Future<void> getPosition() async{
      final response = await getDio()!.get();
      print(response.data);


   }

   static void getRoute(){

   }

   static void getGarage(){

   }

}