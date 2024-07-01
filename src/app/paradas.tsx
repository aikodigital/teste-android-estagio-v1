import { View, StyleSheet, Dimensions } from "react-native";
import React, { useCallback, useEffect, useState } from "react";
import { useLocalSearchParams, useNavigation } from "expo-router";
import axios from "axios";
import { Parada, Posicao, PrevisaoChegada } from "../types/types";
import Header from "../components/Header";
import MapViewContainer from "../components/MapViewContainer";
import BusPredictions from "../components/BusPredictions";

const { width, height } = Dimensions.get("window");
const ASPECT_RATIO = width / height;
const LATITUDE_DELTA = 0.1;
const LONGITUDE_DELTA = LATITUDE_DELTA * ASPECT_RATIO;

type PrevisoesPorParada = {
  [key: number]: PrevisaoChegada[];
};

export default function Paradas() {
  const params = useLocalSearchParams();
  const [paradas, setParadas] = useState<Parada[]>([]);
  const [position, setPosition] = useState<Posicao | null>(null);
  const [previsoesPorParada, setPrevisoesPorParada] =
    useState<PrevisoesPorParada>({});
  const [selectedParada, setSelectedParada] = useState<Parada | null>(null);
  const [mapRegion] = useState({
    latitude: -23.55052,
    longitude: -46.633308,
    latitudeDelta: LATITUDE_DELTA,
    longitudeDelta: LONGITUDE_DELTA,
  });
  const navigation = useNavigation();

  const getPosition = useCallback(async () => {
    try {
      const position = await axios.get(
        `https://aiko-olhovivo-proxy.aikodigital.io/Posicao/Linha?codigoLinha=${params.cl}`,
      );
      setPosition(position.data);
    } catch (error) {
      console.log(error);
    }
  }, [params.cl]);

  const getPrevisaoChegada = useCallback(
    async (cp: number) => {
      try {
        const response = await axios.get(
          `https://aiko-olhovivo-proxy.aikodigital.io/Previsao?codigoParada=${cp}&codigoLinha=${params.cl}`,
        );
        const previsao = response.data;
        if (previsao && previsao.p && previsao.p.l) {
          setPrevisoesPorParada((prev) => ({
            ...prev,
            [cp]: [...(prev[cp] || []), previsao],
          }));
        }
      } catch (error) {
        console.log(error);
      }
    },
    [params.cl],
  );

  useEffect(() => {
    const getParadasPorLinha = async () => {
      try {
        const response = await axios.get(
          `https://aiko-olhovivo-proxy.aikodigital.io/Parada/BuscarParadasPorLinha?codigoLinha=${params.cl}`,
        );
        setParadas(response.data);
      } catch (error) {
        console.log(error);
      }
    };
    getPosition();
    getParadasPorLinha();
    const interval = setInterval(() => {
      getPosition();
    }, 10000);
    return () => clearInterval(interval);
  }, [params.cl, getPosition]);

  useEffect(() => {
    if (paradas.length > 0) {
      paradas.forEach((parada) => getPrevisaoChegada(parada.cp));
    }
  }, [paradas, getPrevisaoChegada]);

  const handleSelectParada = (parada: Parada) => {
    setSelectedParada(parada);
  };

  return (
    <View style={styles.container}>
      <Header navigation={navigation} />
      <MapViewContainer
        position={position}
        paradas={paradas}
        handleSelectParada={handleSelectParada}
        selectedParada={selectedParada}
        previsoesPorParada={previsoesPorParada}
        mapRegion={mapRegion}
      />
      <BusPredictions
        selectedParada={selectedParada}
        previsoesPorParada={previsoesPorParada}
        position={position}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 60,
    alignItems: "center",
    padding: 16,
  },
});
