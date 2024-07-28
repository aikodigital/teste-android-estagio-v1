import React from "react";
import { API_ACCESS_TOKEN } from '@env'; 

import { useEffect, useState, useRef, useContext } from "react";

import { GlobalContext } from "../../../context/GlobalContext";

import MapView, { Marker, Region } from "react-native-maps";

import axios from "axios";

import {
  requestForegroundPermissionsAsync,
  getCurrentPositionAsync,
  LocationObject,
  watchPositionAsync,
  LocationAccuracy,
} from "expo-location";

import {
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
  Text,
} from "react-native";

import Icon from "react-native-vector-icons/FontAwesome5";

import { Menu } from "../../../components/Menu";
import { IconParadas } from "../../../components/iconParadas";
import InfoParadaSelected from "../../../components/InfoParadaSelected";

export default function Home() {
  const {
    showMenu,
    setShowMenu,
    token,
    setInfoParada,
    infoParada,
    setDataParada,
    dataParada,
    filterParada,
  } = useContext(GlobalContext);

  const [currentLocation, setCurrentLocation] = useState<LocationObject | null>(
    null
  );
  const [isLocationVisible, setIsLocationVisible] = useState<boolean>(true);
  const [visibleParadas, setVisibleParadas] = useState<any[]>([]);
  const [region, setRegion] = useState<Region | null>(null);
  const [isTooFar, setIsTooFar] = useState<boolean>(false);

  const mapRef = useRef<MapView>(null);

  useEffect(() => {
    const initialRequestPost = async () => {
      await axios.post(
        `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${API_ACCESS_TOKEN}`
      );
    };

    initialRequestPost();
  }, [token]);

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
    const requestParada = async () => {
      if (currentLocation) {
        const { latitude, longitude } = currentLocation.coords;
        const urlParada = `https://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=&latitude=${latitude}&longitude=${longitude}`;

        const response = await axios.get(urlParada); // Adicione esta linha para verificar a resposta da API
        setDataParada(response.data);
      }
    };

    requestParada();
  }, [currentLocation]);

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

  const onRegionChangeComplete = (region: Region) => {
    setRegion(region);
    filterParadas(region);
  };

  const filterParadas = (region: Region) => {
    if (dataParada) {
      const maxLatitudeDelta = 0.07; // Ajuste o valor conforme necessário
      const maxLongitudeDelta = 0.07; // Ajuste o valor conforme necessário

      const filteredParadas = dataParada.filter((parada: any) => {
        return (
          parada.py >= region.latitude - region.latitudeDelta / 2 &&
          parada.py <= region.latitude + region.latitudeDelta / 2 &&
          parada.px >= region.longitude - region.longitudeDelta / 2 &&
          parada.px <= region.longitude + region.longitudeDelta / 2
        );
      });

      setVisibleParadas(filteredParadas);

      const isFar =
        region.latitudeDelta > maxLatitudeDelta ||
        region.longitudeDelta > maxLongitudeDelta;
      setIsTooFar(isFar);
    }
  };

  return (
    <View className="flex-1">
      {currentLocation && (
        <View className="w-full h-full">
          <TouchableWithoutFeedback
            className="w-full h-full"
            onPress={() => setInfoParada(false)}
          >
            <MapView
              style={{ flex: 1 }}
              ref={mapRef}
              initialRegion={{
                latitude: -23.610592,
                longitude: -46.665759,
                latitudeDelta: 0.01,
                longitudeDelta: 0.01,
              }}
              showsUserLocation
              zoomEnabled={true}
              scrollEnabled={true}
              pitchEnabled={true}
              rotateEnabled={true}
              onRegionChangeComplete={onRegionChangeComplete}
            >
              {!isTooFar &&
                visibleParadas.map((parada: any) => (
                  <Marker
                    key={parada.cp}
                    coordinate={{
                      latitude: parada.py,
                      longitude: parada.px,
                    }}
                    onPress={() => {
                      filterParada(parada.cp);
                      setInfoParada(true);
                    }}
                  >
                    <IconParadas />
                  </Marker>
                ))}
            </MapView>
          </TouchableWithoutFeedback>

          {isTooFar && (
            <View className="flex justify-center items-center">
              <View className="absolute bottom-[350px] w-[300px] p-4 bg-bluePrimary rounded-[30px]">
                <Text className="text-[#fff] text-center font-semibold">
                  Aproxime o mapa para ver as paradas
                </Text>
              </View>
            </View>
          )}

          <TouchableOpacity
            className="w-[70px] h-[70px] flex items-center justify-center bg-[#fff] rounded-full absolute top-[80px] left-5 shadow-lg shadow-[#000]"
            onPress={() => setShowMenu(true)}
          >
            <Icon name="bars" size={30} color="#003184" />
          </TouchableOpacity>

          <InfoParadaSelected />

          <TouchableOpacity
            className={`w-[60px] h-[60px] flex items-center justify-center bg-[#fff] rounded-full absolute ${
              infoParada ? "bottom-[416px]" : "bottom-[136px]"
            } right-5 border border-[#F2F2F2] shadow-lg shadow-[#000]`}
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
