import {
  requestForegroundPermissionsAsync,
  getCurrentPositionAsync,
  LocationObject,
  watchPositionAsync,
  LocationAccuracy,
} from "expo-location";
import MapView, { Marker } from "react-native-maps";
import { useEffect, useState, useRef, useContext } from "react";
import { TouchableOpacity, TouchableWithoutFeedback, View } from "react-native";

import Icon from "react-native-vector-icons/FontAwesome5";

import { Menu } from "../../components/Menu";
import { GlobalContext } from "../../context/GlobalContext";
import { NativeStackNavigationProp } from "react-native-screens/lib/typescript/native-stack/types";
import { RootStackParamList } from "../../Routes";
import React from "react";
import axios from "axios";
import { IconParadas } from "../../components/iconParadas";
import InfoParadaSelected from "../../components/InfoParadaSelected";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

export default function Home() {
  const {showMenu,setShowMenu,token,setInfoParada,infoParada, setDataParada, dataParada, filterParada} = useContext(GlobalContext);

  const [currentLocation, setCurrentLocation] = useState<LocationObject | null>(
    null
  );
  const [isLocationVisible, setIsLocationVisible] = useState<boolean>(true);
  const mapRef = useRef<MapView>(null); // Referência para o MapView

  useEffect(() => {
    const initialRequestPost = async () => {
      await axios.post(
        `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${token}`
      );
    };

    initialRequestPost();
  }, []);

  const urlParada =
    "https://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=Ibirapuera";
  useEffect(() => {
    const requestParada = async () => {
      const response = await axios.get(urlParada);
      setDataParada(response.data);
    };

    requestParada();
  }, [urlParada]);

  async function requestLocaltionPermition() {
    const { granted } = await requestForegroundPermissionsAsync();

    if (granted) {
      const currentPosition = await getCurrentPositionAsync();
      setCurrentLocation(currentPosition);
    }
  }

  useEffect(() => {
    requestLocaltionPermition();
  }, []);

  useEffect(() => {
    watchPositionAsync(
      {
        accuracy: LocationAccuracy.Highest,
        timeInterval: 1000,
        distanceInterval: 1,
      },
      (response) => {
        setCurrentLocation(response);
      }
    );
  }, []);

  const goToCurrentLocation = () => {
    if (currentLocation && mapRef.current) {
      mapRef.current.animateToRegion({
        latitude: currentLocation.coords.latitude,
        longitude: currentLocation.coords.longitude,
        latitudeDelta: 0.005,
        longitudeDelta: 0.005,
      });
      setIsLocationVisible(true);
    }
  };

  const onRegionChange = (region: any) => {
    if (currentLocation) {
      const { latitude, longitude } = currentLocation.coords;
      const {
        latitudeDelta,
        longitudeDelta,
        latitude: regionLat,
        longitude: regionLon,
      } = region;

      const isLatitudeVisible =
        latitude >= regionLat - latitudeDelta / 2 &&
        latitude <= regionLat + latitudeDelta / 2;
      const isLongitudeVisible =
        longitude >= regionLon - longitudeDelta / 2 &&
        longitude <= regionLon + longitudeDelta / 2;

      isLatitudeVisible && isLongitudeVisible
        ? setIsLocationVisible(true)
        : setIsLocationVisible(false);
    }
  };

  return (
    <View>
      {currentLocation && (
        <View className="w-full h-full">
          <TouchableWithoutFeedback className="w-full h-full" onPress={()=> setInfoParada(false)}>
            <MapView
              style={{ flex: 1 }}
              ref={mapRef}
              initialRegion={{
                latitude: -23.610592,
                longitude: -46.665759,
                latitudeDelta: 0.005,
                longitudeDelta: 0.005,
              }}
              showsUserLocation
              zoomEnabled={true}
              scrollEnabled={true}
              pitchEnabled={true}
              rotateEnabled={true}
              onRegionChange={onRegionChange}
            >
              {dataParada &&
                dataParada.map((parada: any) => (
                  <Marker
                    key={parada.cp}
                    coordinate={{
                      latitude: parada.py,
                      longitude: parada.px,
                    }}
                    onPress={() => {
                      filterParada(parada.cp)
                      setInfoParada(true);
                    }}
                  >
                    <IconParadas />
                  </Marker>
                ))}
            </MapView>
          </TouchableWithoutFeedback>

          <TouchableOpacity
            className="w-[70px] h-[70px] flex items-center justify-center bg-[#fff] rounded-full absolute top-[80px] left-5 shadow-lg shadow-[#000]"
            onPress={() => setShowMenu(true)}
          >
            <Icon name="bars" size={30} color="#003184" />
          </TouchableOpacity>
          
          <InfoParadaSelected/>

          <TouchableOpacity
            className={`w-[60px] h-[60px] flex items-center justify-center bg-[#fff] rounded-full absolute ${
              infoParada ? "bottom-[416px]" : "bottom-[136px]"
            }  right-5 border border-[#F2F2F2] shadow-lg shadow-[#000]`}
            onPress={goToCurrentLocation}
          >
            <Icon
              name="location-arrow"
              size={30}
              color={`${isLocationVisible ? "#003184" : "#949494"}`}
            />
          </TouchableOpacity>
        </View>
      )}
      {showMenu && <Menu />}
    </View>
  );
}
