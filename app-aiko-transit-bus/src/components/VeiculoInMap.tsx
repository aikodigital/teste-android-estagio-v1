import React, { FunctionComponent, useContext } from "react";

import { GlobalContext } from "../context/GlobalContext";

import { useNavigation } from "@react-navigation/native";
import { NativeStackNavigationProp } from "@react-navigation/native-stack";

import { RootStackParamList } from "../Routes";

import { Text, View } from "react-native";

import MapView, { Marker } from "react-native-maps";
import { TouchableOpacity } from "react-native-gesture-handler";

import { IconBus } from "./IconBus";

import Icon from "react-native-vector-icons/FontAwesome5";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

type VeiculoInMapProps = {
  py: number;
  px: number;
};

export const VeiculoInMap: FunctionComponent<VeiculoInMapProps> = ({ py , px }) => {
  const { setShowBusInMap } = useContext(GlobalContext);


  const navigation = useNavigation<NavigationProp>();

  return (
    <View className="w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0 z-10 ">
      <View className="pl-5 pb-5 bg-bluePrimary">
        <TouchableOpacity
          className="flex flex-row items-center gap-1 pt-[80px]"
          onPress={() => {
            setShowBusInMap(false);
            navigation.navigate("Linha");
          }}
        >
          <Icon name="arrow-left" size={25} color="#fff" />
        </TouchableOpacity>
      </View>
      <View className="pl-5 pt-2">
        <Text className="font-bold text-bluePrimary text-xl">Localização atual do veículo</Text>
      </View>
      <View className="pt-5 w-full h-full rounded-md">
        <MapView
          className="w-full h-full rounded-md"
          initialRegion={{
            latitude: py,
            longitude: px,
            latitudeDelta: 0.03,
            longitudeDelta: 0.03,
          }}
          zoomEnabled={true}
          scrollEnabled={true}
          pitchEnabled={true}
          rotateEnabled={true}
        >
          <Marker coordinate={{ latitude: py, longitude: px }}>
            <IconBus />
          </Marker>
        </MapView>
      </View>
    </View>
  );
};
