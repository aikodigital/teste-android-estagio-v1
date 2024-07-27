import { FlatList, StyleSheet } from 'react-native';
import { useState } from 'react';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import { ParadaPrevisaoChegada, PrevisaoChegada } from '@/types/types';
import ItemText from '@/components/text/ItemText';
import useFetchHook from '@/custom-hooks/useFetchHook';
import ItemContainer from '@/components/containers/ItemContainer';
import SearchInput from '@/components/pages/linhaDeOnibus/SearchInput';
import Inputs from '@/components/pages/previsaoDeChegada/Inputs';
import SearchResult from '@/components/pages/previsaoDeChegada/SearchResult';

export default function PaginaPrevisaoDeChegada() {
  const [parada, setParada] = useState<ParadaPrevisaoChegada | null>(null);
  const [inputDePesquisa, setInputDePesquisa] = useState({
    codigoParada: '',
    codigoLinha: '',
  });
  const { pesquisar, formState } = useFetchHook({});

  const aoConcluirPesquisa = async (json: PrevisaoChegada) => {
    setParada(json.p);
  };

  const lidarComPesquisa = async () => {
    if (!inputDePesquisa.codigoLinha || !inputDePesquisa.codigoParada) return;
    setParada(null);
    await pesquisar<PrevisaoChegada>(
      aoConcluirPesquisa,
      `/Previsao?codigoParada=${inputDePesquisa.codigoParada}&codigoLinha=${inputDePesquisa.codigoLinha}`
    );
  };

  return (
    <PageContainer>
      <Title>Previsao de chegada</Title>
      <Inputs
        inputDePesquisa={inputDePesquisa}
        lidarComPesquisa={lidarComPesquisa}
        setInputDePesquisa={setInputDePesquisa}
      />

      <SearchResult
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        parada={parada}
      />
    </PageContainer>
  );
}
