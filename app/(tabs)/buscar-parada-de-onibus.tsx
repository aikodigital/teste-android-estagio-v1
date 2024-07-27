import { useState } from 'react';
import { MapRegion, Parada } from '@/types/types';
import Titulo from '@/components/text/Titulo';
import useFetchHook from '@/custom-hooks/useFetchHook';
import InputDeBusca from '@/components/pages/linhaDeOnibus/InputDeBusca';
import MapResult from '@/components/pages/paradaDeOnibus/MapResult';
import ContainerParaPagina from '@/components/containers/ContainerParaPagina';

export default function BuscarParadaDeOnibus() {
  const [paradas, setParadas] = useState<Parada[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const { formState, pesquisar } = useFetchHook({});

  const coordernadasSpValorInicial = {
    latitude: -23.550522,
    longitude: -46.633328,
    latitudeDelta: 0.5,
    longitudeDelta: 0.012,
  };
  const [coordenadasSp, setcoordenadasSp] = useState<MapRegion>(
    coordernadasSpValorInicial
  );

  const aoConcluirPesquisa = async (json: Parada[]) => {
    setcoordenadasSp(coordernadasSpValorInicial);
    setParadas(json);
  };

  const lidarComPesquisa = async () => {
    setParadas([]);
    await pesquisar<Parada[]>(
      aoConcluirPesquisa,
      process.env.API_URL + '/Parada/Buscar?termosBusca=' + inputDePesquisa
    );
  };

  return (
    <ContainerParaPagina>
      <Titulo>Buscar Paradas de Ônibus</Titulo>
      <InputDeBusca
        placeholder='Ex: AFONSO BRAZ'
        value={inputDePesquisa}
        onChange={lidarComPesquisa}
        onChangeText={setInputDePesquisa}
      />
      <MapResult
        coordenadasSp={coordenadasSp}
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        paradas={paradas}
      />
    </ContainerParaPagina>
  );
}
