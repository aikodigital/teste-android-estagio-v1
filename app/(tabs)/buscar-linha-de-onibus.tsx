import { useState } from 'react';
import { Linha } from '@/types/types';
import Titulo from '@/components/text/Titulo';
import useFetchHook from '@/custom-hooks/useFetchHook';
import SearchResult from '@/components/pages/linhaDeOnibus/SearchResult';
import ContainerParaPagina from '@/components/containers/ContainerParaPagina';
import InputDeBusca from '@/components/pages/linhaDeOnibus/InputDeBusca';

export default function PaginaDeLinhaDeOnibus() {
  const [linhas, setLinhas] = useState<Linha[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const { formState, pesquisar } = useFetchHook({});

  const aoConcluirPesquisa = async (json: Linha[]) => {
    setLinhas(json);
  };

  const lidarComPesquisa = async () => {
    setLinhas([]);
    await pesquisar<Linha[]>(
      aoConcluirPesquisa,
      process.env.API_URL + '/Linha/Buscar?termosBusca=' + inputDePesquisa
    );
  };

  return (
    <ContainerParaPagina>
      <Titulo>Buscar Linhas de Ônibus</Titulo>
      <InputDeBusca
        placeholder='Ex: 8000'
        value={inputDePesquisa}
        onChangeText={setInputDePesquisa}
        onChange={lidarComPesquisa}
      />
      <SearchResult
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        linhas={linhas}
      />
    </ContainerParaPagina>
  );
}
