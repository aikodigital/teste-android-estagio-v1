import { useCallback, useState } from 'react';
import {
  LinhaParaPosicao,
  RegiaoDoMapa,
  PosicaoDosVeiculos,
  PosicaoVeiculo,
  Trajeto,
} from '@/types/types';
import Carregando from '@/components/form/loading/Carregando';
import useRodeEmIntervalo from '@/custom-hooks/useRodeEmIntervalo';
import Titulo from '@/components/text/Titulo';
import useFetchHook from '@/custom-hooks/useFetchHook';
import ContainerParaPagina from '@/components/containers/ContainerParaPagina';
import MapaParaPaginaPrincipal from '@/components/pages/posicaoDosVeiculos/MapaParaPaginaPrincipal';
import InputsDeFiltro from '@/components/pages/posicaoDosVeiculos/InputsDeFiltro';

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
  const [coordenadasSp, setcoordenadasSp] = useState<RegiaoDoMapa>({
    latitude: -23.550522,
    longitude: -46.633328,
    latitudeDelta: 1,
    longitudeDelta: 0.012,
  });

  const aoConcluirPesquisa = useCallback(async (json: PosicaoDosVeiculos) => {
    setLinhas(json?.l);
    setRegioes(json?.l.map((r) => r.lt0));
  }, []);

  const fetchData = useCallback(async () => {
    await pesquisar<PosicaoDosVeiculos>(
      aoConcluirPesquisa,
      process.env.API_URL + '/Posicao'
    );
  }, [pesquisar, aoConcluirPesquisa]);

  useRodeEmIntervalo(fetchData, 15000);

  const seTiverFiltro = linhas.length > 0 && trajeto.para !== 'Todos';
  const linhasFiltradas = seTiverFiltro
    ? linhas?.filter(
        ({ lt0, lt1 }) => lt0 === trajeto.de && lt1 === trajeto.para
      )
    : linhas?.slice(0, 66);

  if (linhas && !linhas?.length) return <Carregando />;

  return (
    <ContainerParaPagina>
      <Titulo>Posição dos Veículos</Titulo>
      <MapaParaPaginaPrincipal
        coordenadasSp={coordenadasSp}
        linhasFiltradas={linhasFiltradas}
      />
      <InputsDeFiltro
        linhas={linhas}
        regioes={regioes}
        setcoordenadasSp={setcoordenadasSp}
        setTrajeto={setTrajeto}
        trajeto={trajeto}
      />
    </ContainerParaPagina>
  );
}
