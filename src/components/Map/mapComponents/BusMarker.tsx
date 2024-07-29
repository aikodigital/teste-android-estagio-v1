import React from "react";
import { Marker } from "react-native-maps";
import { useNavigation } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { BusMarkerProps, RootStackParamList } from "../../../types/types";

export const BusMarker: React.FC<BusMarkerProps> = ({ bus, lineDetails }) => {
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  return (
    <Marker
      image={require("@/assets/bus.png")}
      coordinate={{
        latitude: bus.py,
        longitude: bus.px,
      }}
      title={`Ônibus ${bus.p}`}
      description={`Última atualização: ${bus.ta}`}
      onPress={() => navigation.navigate("Details", { bus, lineDetails })}
    />
  );
};
