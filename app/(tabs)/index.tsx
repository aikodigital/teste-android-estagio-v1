import { StyleSheet, View } from 'react-native';
import { useEffect, useRef, useState } from 'react';
import {
  LinhaParaPosicao,
  PosicaoDosVeiculos,
  PosicaoVeiculo,
} from '@/types/posicao';
import MapView, { MapMarker } from 'react-native-maps';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import De from '@/components/pages/posicaoDosVeiculos/De';
import Para from '@/components/pages/posicaoDosVeiculos/Para';
import Loading from '@/components/form/loading/Loading';
import useRodeEmIntervalo from '@/custom-hooks/useRodeEmIntervalo';
import Title from '@/components/text/Title';

export type Trajeto = { de: string | null; para: string | null };

export default function HomeScreen() {
  const [linhas, setLinhas] = useState<LinhaParaPosicao<PosicaoVeiculo>[]>([]);
  const [regioes, setRegioes] = useState<string[]>([]);
  const [trajeto, setTrajeto] = useState<Trajeto>({
    de: 'Todos',
    para: 'Todos',
  });
  const [cordenadasSp, setCordenadasSp] = useState({
    latitude: -23.550522,
    longitude: -46.633328,
    latitudeDelta: 1,
    longitudeDelta: 0.012,
  });

  const fetchData = async () => {
    try {
      const res = await fetch(process.env.API_URL + '/Posicao');
      if (!res.ok) throw Error('Error fetching Data');
      const json = (await res.json()) as PosicaoDosVeiculos;
      if (!json) throw new Error('Error fetching Data');
      setLinhas(json?.l);
      setRegioes(json?.l.map((r) => r.lt0));
    } catch (error) {
      error instanceof Error && console.log(error.message);
      await autenticarNaApi();
      await fetchData();
    }
  };

  useRodeEmIntervalo(fetchData, 10000);

  const seTiverFiltro = linhas.length > 0 && trajeto.para !== 'Todos';

  const linhasFiltradas = seTiverFiltro
    ? linhas?.filter(
        ({ lt0, lt1 }) => lt0 === trajeto.de && lt1 === trajeto.para
      )
    : linhas?.slice(0, 66);

  if (linhas && !linhas?.length) return <Loading />;
  return (
    <View style={styles.pageContainer}>
      <Title>Posição dos Veículos</Title>
      <MapView
        region={cordenadasSp}
        initialRegion={cordenadasSp}
        style={styles.map}
      >
        {linhasFiltradas?.map(({ vs, lt0, lt1 }) =>
          vs
            ?.flat(1)
            ?.map(({ px, py, p }, i) => (
              <MapMarker
                key={i}
                tracksViewChanges={false}
                coordinate={{ latitude: py, longitude: px }}
                style={{ opacity: 0.5 }}
                title={`De ${lt0} para ${lt1}`}
              />
            ))
        )}
      </MapView>
      {regioes.length > 0 && (
        <View style={styles.inputContainer}>
          <De regioes={regioes} setTrajeto={setTrajeto} trajeto={trajeto} />
          {trajeto.de !== 'Todos' && (
            <Para
              linhas={linhas}
              setCordenadasSp={setCordenadasSp}
              setTrajeto={setTrajeto}
              trajeto={trajeto}
            />
          )}
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  pageContainer: {
    paddingTop: 28,
    paddingBottom: 120,
    paddingHorizontal: 14,
    backgroundColor: '#f0f4f8',
  },
  map: {
    width: '100%',
    height: '100%',
  },
  inputContainer: {
    display: 'flex',
    flexWrap: 'wrap',
    flexDirection: 'row',
    gap: 8,
    shadowColor: '#000',
    padding: 12,
    backgroundColor: '#f5f5f5',
    opacity: 0.9,
    alignItems: 'center',
    position: 'absolute',
    bottom: 50,
    left: 0,
    width: '105%',
  },
});
