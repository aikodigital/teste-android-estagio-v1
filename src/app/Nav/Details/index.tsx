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
      <View style={styles.textContainer}>
        <Text style={styles.number}>Número do ônibus: {bus.p}</Text>
        <Text style={styles.text}>Origem: {lineDetails.lt1}</Text>
        <Text style={styles.text}>Destino: {lineDetails.lt0}</Text>
      </View>
    </View>
  );
}
