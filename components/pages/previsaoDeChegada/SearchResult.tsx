import ItemContainer from '@/components/containers/ItemContainer';
import ErrorComponent from '@/components/form/error/Error';
import Loading from '@/components/form/loading/Loading';
import ItemText from '@/components/text/ItemText';
import Title from '@/components/text/Title';
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

const SearchResult = ({ formState, inputDePesquisa, parada }: Props) => {
  if (formState.error) return <ErrorComponent messagem={formState.error} />;
  {
    formState.loading && !formState.error ? (
      <Loading />
    ) : (
      inputDePesquisa.codigoLinha.length > 0 &&
      inputDePesquisa.codigoParada.length > 0 &&
      parada && (
        <>
          <Title>{parada.np}</Title>
          <FlatList
            data={parada.l}
            renderItem={({ item }) => {
              return (
                <ItemContainer>
                  <ItemText>
                    De "{item.lt0}" para "{item.lt1}":
                  </ItemText>
                  <FlatList
                    data={item.vs}
                    renderItem={({ item }) => {
                      return (
                        <ItemContainer>
                          <ItemText>Carro: {item.p}</ItemText>
                          <ItemText>
                            Previsão de chegada para {item.t}.
                          </ItemText>
                        </ItemContainer>
                      );
                    }}
                  />
                </ItemContainer>
              );
            }}
          />
        </>
      )
    );
  }
};

export default SearchResult;
