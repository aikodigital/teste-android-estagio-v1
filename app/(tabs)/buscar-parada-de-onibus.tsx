import { StyleSheet, TextInput, View } from 'react-native';
import { useState } from 'react';
import { MapRegion, Parada } from '@/types/types';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
import Title from '@/components/text/Title';
import MapView, { MapMarker } from 'react-native-maps';
import PageContainer from '@/components/containers/PageContainer';
import useFormState from '@/custom-hooks/useFormState';
import useOnChangeTimeout from '@/custom-hooks/useOnChangeTimeout';
import SearchInput from '@/components/pages/linhaDeOnibus/SearchInput';
import MapResult from '@/components/pages/paradaDeOnibus/MapResult';

export default function BuscarParadaDeOnibus() {
  const [paradas, setParadas] = useState<Parada[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const { formState, pesquisar } = useFormState();

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
        inputDePesquisa={inputDePesquisa}
        lidarComPesquisa={lidarComPesquisa}
        setInputDePesquisa={setInputDePesquisa}
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
