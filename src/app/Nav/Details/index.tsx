// Details.tsx

import React from "react";
import { View, Text } from "react-native";
import { RouteProp } from "@react-navigation/native";
import { Bus } from "../../../types/types";
import { styles } from "./styles";

type RootStackParamList = {
  Details: {
    bus: Bus;
    lineDetails: {
      lt0: string;
      lt1: string;
    };
  };
};

type DetailsRouteProp = RouteProp<RootStackParamList, "Details">;

type DetailsProps = {
  route: DetailsRouteProp;
};

export function Details({ route }: DetailsProps) {
  const { bus, lineDetails } = route.params;

  return (
    <View style={styles.container}>
      <Text>Letreiro de destino: {lineDetails.lt0}</Text>
      <Text>Letreiro de origem: {lineDetails.lt1}</Text>
      <Text>Prefixo do ônibus: {bus.p}</Text>
      <Text>Última atualização: {bus.ta}</Text>
      <Text>Latitude: {bus.py}</Text>
      <Text>Longitude: {bus.px}</Text>
    </View>
  );
}
