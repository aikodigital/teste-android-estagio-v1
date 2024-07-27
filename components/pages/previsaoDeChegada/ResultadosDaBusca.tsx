import ContainerParaItem from '@/components/containers/ContainerParaItem';
import Erro from '@/components/form/error/Erro';
import Carregando from '@/components/form/loading/Carregando';
import ItemText from '@/components/text/ItemText';
import Titulo from '@/components/text/Titulo';
import { ParadaPrevisaoChegada } from '@/types/types';
import React from 'react';
import { FlatList } from 'react-native';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  inputDePesquisa: {
    codigoParada: string;
    codigoLinha: string;
  };
  parada: ParadaPrevisaoChegada | null;
};

const ResultadosDaBusca = ({ formState, inputDePesquisa, parada }: Props) => {
  if (formState.error) return <Erro messagem={formState.error} />;
  {
    formState.loading && !formState.error ? (
      <Carregando />
    ) : (
      inputDePesquisa.codigoLinha.length > 0 &&
      inputDePesquisa.codigoParada.length > 0 &&
      parada && (
        <>
          <Titulo>{parada.np}</Titulo>
          <FlatList
            data={parada.l}
            renderItem={({ item }) => {
              return (
                <ContainerParaItem>
                  <ItemText>
                    De "{item.lt0}" para "{item.lt1}":
                  </ItemText>
                  <FlatList
                    data={item.vs}
                    renderItem={({ item }) => {
                      return (
                        <ContainerParaItem>
                          <ItemText>Carro: {item.p}</ItemText>
                          <ItemText>
                            Previsão de chegada para {item.t}.
                          </ItemText>
                        </ContainerParaItem>
                      );
                    }}
                  />
                </ContainerParaItem>
              );
            }}
          />
        </>
      )
    );
  }
};

export default ResultadosDaBusca;
