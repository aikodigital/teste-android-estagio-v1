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
  const { formState, setFormState } = useFormState();
  const { runTimeout } = useOnChangeTimeout();

  const fetchData = async () => {
    try {
      const res = await fetch(
        process.env.API_URL + '/Linha/Buscar?termosBusca=' + inputDePesquisa
      );
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as Linha[];
      if (!json || !json.length) throw new Error(`Nenhuma linha encontrada.`);
      setLinhas(json);
    } catch (error) {
      if (error instanceof Error) {
        setFormState((prev) => ({ ...prev, error: error.message }));
      }
    } finally {
      setFormState((prev) => ({ ...prev, loading: false }));
    }
  };

  const lidarComPesquisa = () => {
    setFormState({ error: '', loading: true });
    setLinhas([]);
    runTimeout(fetchData);
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
