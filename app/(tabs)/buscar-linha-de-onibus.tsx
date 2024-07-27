import { FlatList, StyleSheet, Text, View } from 'react-native';
import { useEffect, useRef, useState } from 'react';
import {
  Linha,
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

export type Trajeto = { de: string | null; para: string | null };

export default function PaginaDeLinhaDeOnibus() {
  const [linhas, setLinhas] = useState<Linha[]>([]);

  const fetchData = async () => {
    try {
      const res = await fetch(
        process.env.API_URL + '/Linha/Buscar?termosBusca=8000'
      );
      const json = (await res.json()) as Linha[];
      console.log(json.length);
      setLinhas(json);
    } catch (error) {
      error instanceof Error && console.log(error.message);
      await autenticarNaApi();
      await fetchData();
    }
  };
  useRodeEmIntervalo(fetchData, 10000);

  if (!linhas?.length) return <Loading />;
  return (
    <View style={styles.pageContainer}>
      <Text style={styles.title}>Buscar Linhas de Ônibus</Text>
      <FlatList
        style={styles.itemsContainer}
        data={linhas}
        renderItem={({ item }) => {
          let sl: string;
          switch (item.sl) {
            case 1:
              sl = 'Principal para Secundário';
              break;
            case 2:
              sl = 'Secundário para Principal';
              break;
            default:
              sl = '';
              break;
          }
          return (
            <View style={styles.item}>
              <Text>Código Identificador: {item.cl}</Text>
              <Text>Terminal Principal: {item.tp}</Text>
              <Text>Terminal Secundário: {item.ts}</Text>
              <Text>Sentido: {sl}</Text>
              <Text>Operação: {item.lc ? 'Circular' : 'Terminal'}</Text>
            </View>
          );
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  pageContainer: { paddingTop: 28, paddingHorizontal: 14 },
  title: { textAlign: 'center' },
  itemsContainer: {},
  item: {
    borderWidth: 1,
    borderRadius: 6,
    borderColor: '#eee',
    backgroundColor: 'white',
    padding: 8,
    fontSize: 18,
    marginTop: 8,
    color: '#333',
    // width: 180,
  },
});
