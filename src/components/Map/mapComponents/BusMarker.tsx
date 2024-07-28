// BusMarker.tsx

import React from "react";
import { Marker } from "react-native-maps";
import { useNavigation } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { Bus } from "../../../types/types";

type RootStackParamList = {
  Details: {
    bus: Bus;
    lineDetails: {
      lt0: string;
      lt1: string;
    };
  };
};

type LineDetails = {
  lt0: string;
  lt1: string;
};

type BusMarkerProps = {
  bus: Bus;
  lineDetails: LineDetails;
};

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
