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
import useFetchHook from '@/custom-hooks/useFetchHook';

export default function HomeScreen() {
  const { pesquisar } = useFetchHook({
    iniciarSemLoading: true,
    pesquisarSemTimeout: true,
  });
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

  const aoConcluirPesquisa = async (json: PosicaoDosVeiculos) => {
    setLinhas(json?.l);
    setRegioes(json?.l.map((r) => r.lt0));
  };

  const fetchData = async () => {
    await pesquisar<PosicaoDosVeiculos>(
      aoConcluirPesquisa,
      process.env.API_URL + '/Posicao'
    );
  };

  useRodeEmIntervalo(fetchData, 15000);

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
