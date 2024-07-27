import { useState } from 'react';
import Titulo from '@/components/text/Titulo';
import { ParadaPrevisaoChegada, PrevisaoChegada } from '@/types/types';
import useFetchHook from '@/custom-hooks/useFetchHook';
import Inputs from '@/components/pages/previsaoDeChegada/Inputs';
import ContainerParaPagina from '@/components/containers/ContainerParaPagina';
import ResultadosDaBusca from '@/components/pages/previsaoDeChegada/ResultadosDaBusca';

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
    <ContainerParaPagina>
      <Titulo>Previsao de chegada</Titulo>
      <Inputs
        inputDePesquisa={inputDePesquisa}
        lidarComPesquisa={lidarComPesquisa}
        setInputDePesquisa={setInputDePesquisa}
      />

      <ResultadosDaBusca
        formState={formState}
        inputDePesquisa={inputDePesquisa}
        parada={parada}
      />
    </ContainerParaPagina>
  );
}
