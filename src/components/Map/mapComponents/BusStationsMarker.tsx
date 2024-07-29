// BusStationsMarker.tsx
import React from "react";
import { Marker } from "react-native-maps";
import { useNavigation } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import {
  BusStationsMarkerProps,
  RootStackParamList,
} from "../../../types/types";

export const BusStationsMarker: React.FC<BusStationsMarkerProps> = ({
  station,
}) => {
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  return (
    <Marker
      image={require("@/assets/busstation.png")}
      coordinate={{
        latitude: station.py,
        longitude: station.px,
      }}
      title={station.np || `Parada ${station.cp}`}
      description={`${station.cp}`}
    />
  );
};
