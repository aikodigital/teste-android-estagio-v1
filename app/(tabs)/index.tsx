import { StyleSheet, Text, View } from 'react-native';
import { useEffect, useRef, useState } from 'react';
import {
  LinhaParaPosicao,
  PosicaoDosVeiculos,
  PosicaoVeiculo,
} from '@/types/posicao';
import MapView, { MapMarker, Marker } from 'react-native-maps';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import RNPickerSelect from 'react-native-picker-select';
import De from '@/components/pages/posicaoDosVeiculos/De';
import Para from '@/components/pages/posicaoDosVeiculos/Para';

const useRodeEmIntervalo = (fn: () => Promise<any>, delay: number) => {
  const intervaloRef = useRef<NodeJS.Timeout>();

  useEffect(() => {
    const rodarIntervalo = async () => {
      // await fn();
      if (!intervaloRef.current) {
        intervaloRef.current = setInterval(async () => await fn(), delay);
      }
    };
    rodarIntervalo();

    return () => {
      clearInterval(intervaloRef.current);
      intervaloRef.current = undefined;
    };
  }, []);
};

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
      setLinhas(json.l);
      setRegioes(json.l.map((r) => r.lt0));
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

  if (!linhas?.length) return <Text>Carregando</Text>;
  return (
    <>
      <MapView
        region={cordenadasSp}
        initialRegion={cordenadasSp}
        style={styles.map}
      >
        {linhasFiltradas
          ?.map(({ vs }) => vs)
          ?.flat(1)
          ?.map(({ px, py }, i) => (
            <MapMarker
              key={i}
              tracksViewChanges={false}
              coordinate={{ latitude: py, longitude: px }}
              style={{ opacity: 0.5 }}
            />
          ))}
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
    </>
  );
}

const styles = StyleSheet.create({
  map: {
    flex: 1,
    width: '100%',
    height: '100%',
    position: 'relative',
  },
  inputContainer: {
    display: 'flex',
    flexDirection: 'row',
    gap: 8,
    padding: 12,
    backgroundColor: 'white',
    opacity: 0.9,
    alignItems: 'center',
  },
});
