import { useState } from 'react';
import { MapRegion, Parada } from '@/types/types';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import useFetchHook from '@/custom-hooks/useFetchHook';
import SearchInput from '@/components/pages/linhaDeOnibus/SearchInput';
import MapResult from '@/components/pages/paradaDeOnibus/MapResult';

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
  const [cordenadasSp, setCordenadasSp] = useState<MapRegion>(
    coordernadasSpValorInicial
  );

  const aoConcluirPesquisa = async (json: Parada[]) => {
    setCordenadasSp(coordernadasSpValorInicial);
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
    <PageContainer>
      <Title>Buscar Paradas de Ônibus</Title>
      <SearchInput
        placeholder='Ex: AFONSO BRAZ'
        value={inputDePesquisa}
        onChange={lidarComPesquisa}
        onChangeText={setInputDePesquisa}
      />
      <MapResult
        cordenadasSp={cordenadasSp}
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        paradas={paradas}
      />
    </PageContainer>
  );
}
