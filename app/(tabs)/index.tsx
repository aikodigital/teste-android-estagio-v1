import { StyleSheet, View } from 'react-native';
import { useEffect, useState } from 'react';
import { PosicaoDosVeiculos, PosicaoVeiculo } from '@/types/posicao';
import MapView, { Marker, MarkerAnimated } from 'react-native-maps';
import { useFocusEffect } from 'expo-router';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';

export default function HomeScreen() {
  const [carrosPosicao, setCarrosPosicao] = useState<PosicaoVeiculo[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        await autenticarNaApi();
        const res = await fetch(process.env.API_URL + '/Posicao');
        if (!res.ok) throw Error('Error fetching Data');
        const json = (await res.json()) as PosicaoDosVeiculos;
        const carrosPosicaoJSON = json?.l?.map((linha) => linha.vs).flat(1);
        setCarrosPosicao(carrosPosicaoJSON);
      } catch (error) {
        error instanceof Error && console.log(error.message);
      }
    };

    fetchData();
  }, []);

  const SaoPauloCoords = {
    latitude: -23.550522,
    longitude: -46.633328,
  };

  const INITIAL_REGION = {
    ...SaoPauloCoords,
    latitudeDelta: 1,
    longitudeDelta: 0.012,
  };

  return (
    <View style={styles.container}>
      <MapView zoomEnabled={false} region={INITIAL_REGION} style={styles.map}>
        {carrosPosicao?.map(({ a, p, px, py, ta }, i) => (
          <Marker
            key={i}
            tracksViewChanges={false}
            coordinate={{ latitude: py, longitude: px }}
          />
        ))}
      </MapView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
});
