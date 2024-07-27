import { useState } from 'react';
import {
  LinhaParaPosicao,
  MapRegion,
  PosicaoDosVeiculos,
  PosicaoVeiculo,
  Trajeto,
} from '@/types/types';
import Loading from '@/components/form/loading/Loading';
import useRodeEmIntervalo from '@/custom-hooks/useRodeEmIntervalo';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import HomepageMap from '@/components/pages/posicaoDosVeiculos/HomepageMap';
import FilteringInputs from '@/components/pages/posicaoDosVeiculos/FilteringInputs';

export default function HomeScreen() {
  const [linhas, setLinhas] = useState<LinhaParaPosicao<PosicaoVeiculo>[]>([]);
  const [regioes, setRegioes] = useState<string[]>([]);
  const [trajeto, setTrajeto] = useState<Trajeto>({
    de: 'Todos',
    para: 'Todos',
  });
  const [cordenadasSp, setCordenadasSp] = useState<MapRegion>({
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
      setTimeout(async () => await fetchData(), 15000);
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
    <PageContainer>
      <Title>Posição dos Veículos</Title>
      <HomepageMap
        cordenadasSp={cordenadasSp}
        linhasFiltradas={linhasFiltradas}
      />
      <FilteringInputs
        linhas={linhas}
        regioes={regioes}
        setCordenadasSp={setCordenadasSp}
        setTrajeto={setTrajeto}
        trajeto={trajeto}
      />
    </PageContainer>
  );
}
