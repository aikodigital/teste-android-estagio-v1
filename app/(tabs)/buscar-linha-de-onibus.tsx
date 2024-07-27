import { useState } from 'react';
import { Linha } from '@/types/types';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import useFormState from '@/custom-hooks/useFormState';
import useOnChangeTimeout from '@/custom-hooks/useOnChangeTimeout';
import SearchInput from '@/components/pages/linhaDeOnibus/SearchInput';
import SearchResult from '@/components/pages/linhaDeOnibus/SearchResult';

export default function PaginaDeLinhaDeOnibus() {
  const [linhas, setLinhas] = useState<Linha[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const { formState, pesquisar } = useFormState();

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
    <PageContainer>
      <Title>Buscar Linhas de Ônibus</Title>
      <SearchInput
        placeholder='Ex: 8000'
        inputDePesquisa={inputDePesquisa}
        lidarComPesquisa={lidarComPesquisa}
        setInputDePesquisa={setInputDePesquisa}
      />
      <SearchResult
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        linhas={linhas}
      />
    </PageContainer>
  );
}
