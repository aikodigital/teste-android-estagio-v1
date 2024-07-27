import {
  requestForegroundPermissionsAsync,
  getCurrentPositionAsync,
  LocationObject,
  watchPositionAsync,
  LocationAccuracy,
} from "expo-location";
import MapView from "react-native-maps";
import { useEffect, useState, useRef, useContext } from "react";
import { Text, TouchableOpacity, View } from "react-native";

import Icon from "react-native-vector-icons/FontAwesome5";

import { Menu } from "../../components/Menu";
import { GlobalContext } from "../../context/GlobalContext";
import { useNavigation } from "@react-navigation/native";
import { NativeStackNavigationProp } from "react-native-screens/lib/typescript/native-stack/types";
import { RootStackParamList } from "../../Routes";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, 'Home'>;

export default function Home() {
  const { showMenu, setShowMenu, setRowOrSearch } = useContext(GlobalContext);

  const navigation = useNavigation<NavigationProp>();

  const [currentLocation, setCurrentLocation] = useState<LocationObject | null>(
    null
  );
  const [isLocationVisible, setIsLocationVisible] = useState<boolean>(true);
  const mapRef = useRef<MapView>(null); // Referência para o MapView

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
          <MapView
            className="w-full h-[87%]"
            ref={mapRef}
            initialRegion={{
              latitude: currentLocation.coords.latitude,
              longitude: currentLocation.coords.longitude,
              latitudeDelta: 0.005,
              longitudeDelta: 0.005,
            }}
            showsUserLocation
            zoomEnabled={true}
            scrollEnabled={true}
            pitchEnabled={true}
            rotateEnabled={true}
            onRegionChange={onRegionChange}
          />
          <TouchableOpacity
            className="w-[70px] h-[70px] flex items-center justify-center bg-[#fff] rounded-full absolute top-[80px] left-5 shadow-lg shadow-[#000]"
            onPress={() => {
              setShowMenu(true);
            }}
          >
            <Icon name="bars" size={30} color="#003184" />
          </TouchableOpacity>
          <View className="w-full h-[116px] absolute bottom-0 flex px-5 items-center  rounded-t-[20px] bg-[#fff] border border-[#F2F2F2] shadow-lg shadow-[#000]">
            <TouchableOpacity
              className="w-full h-[50px] flex flex-row items-center mt-[18px] bg-[#fff] border border-[#F2F2F2] rounded-[30px] pl-5 shadow-lg shadow-[#000]"
              onPress={() => {
                setRowOrSearch("linhas")
                navigation.navigate("Busca")}}
            >
              <Icon name="search" size={30} color="#949494" />
              <Text className="pl-3 text-[#949494] font-semibold text-xl">
                Linhas e destinos
              </Text>
            </TouchableOpacity>
          </View>
          <TouchableOpacity
            className="w-[60px] h-[60px] flex items-center justify-center bg-[#fff] rounded-full absolute bottom-[136px] right-5 border border-[#F2F2F2] shadow-lg shadow-[#000]"
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
