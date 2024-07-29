// Details.tsx
import React from "react";
import { View, Text, ActivityIndicator, ScrollView } from "react-native";
import { useRoute, RouteProp } from "@react-navigation/native";
import { useBusArrivalPredictions } from "../../../components/Map/mapComponents/useBusArrivalPredictions";
import { RootStackParamList } from "../../../types/types";
import { styles } from "./styles";

type DetailsRouteProp = RouteProp<RootStackParamList, "Details">;

export function Details() {
  const route = useRoute<DetailsRouteProp>();
  const { codigoParada, lt0, lt1 } = route.params;

  if (codigoParada !== undefined) {
    const { arrivalPredictions, loading, error } =
      useBusArrivalPredictions(codigoParada);

    if (loading) {
      return <ActivityIndicator size="large" color="#0000ff" />;
    }

    if (error) {
      return <Text>Erro ao carregar previsões de chegada</Text>;
    }

    if (
      !arrivalPredictions ||
      !arrivalPredictions.p ||
      !arrivalPredictions.p.l
    ) {
      return <Text>Sem dados de previsão de chegada</Text>;
    }

    return (
      <ScrollView>
        <View>
          <Text style={styles.textContainer}>
            Previsão de Chegada dos Ônibus:
          </Text>
          {arrivalPredictions.p.l.map((linha) => (
            <View style={styles.text} key={linha.cl}>
              <Text style={styles.header}>
                {linha.lt0} - {linha.lt1}
              </Text>
              {linha.vs.map((veiculo) => (
                <View key={veiculo.p}>
                  <Text>Código do Ônibus: {veiculo.p}</Text>
                  <Text>Horário: {veiculo.t}</Text>
                </View>
              ))}
            </View>
          ))}
        </View>
      </ScrollView>
    );
  }

  if (lt0 !== undefined && lt1 !== undefined) {
    return (
      <ScrollView>
        <View>
          <Text style={styles.textContainer}>Detalhes do Ônibus</Text>
          <Text style={styles.text}>Origem: {lt1}</Text>
          <Text style={styles.text}>Destino: {lt0}</Text>
        </View>
      </ScrollView>
    );
  }

  return <Text>Sem dados disponíveis</Text>;
}
