import ItemContainer from '@/components/containers/ItemContainer';
import ErrorComponent from '@/components/form/error/Error';
import Loading from '@/components/form/loading/Loading';
import ItemText from '@/components/text/ItemText';
import { Linha } from '@/types/types';
import React from 'react';
import { FlatList, StyleSheet, View } from 'react-native';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  inputDePesquisa: string;
  linhas: Linha[];
};

const SearchResult = ({ formState, inputDePesquisa, linhas }: Props) => {
  if (formState.error) return <ErrorComponent messagem={formState.error} />;
  return formState.loading && !formState.error ? (
    <Loading />
  ) : (
    inputDePesquisa.length > 0 && linhas.length > 0 && (
      <FlatList
        data={linhas}
        renderItem={({ item }) => {
          const sl =
            item.sl === 1
              ? 'Principal para Secundário'
              : 'Secundário para Principal';
          return (
            <ItemContainer>
              <ItemText>
                Linha: {item.lt} - {item.tl}
              </ItemText>
              <ItemText>Código Identificador: {item.cl}</ItemText>
              <ItemText>Terminal Principal: {item.tp}</ItemText>
              <ItemText>Terminal Secundário: {item.ts}</ItemText>
              <ItemText>Sentido: {sl}</ItemText>
              <ItemText>Operação: {item.lc ? 'Circular' : 'Terminal'}</ItemText>
            </ItemContainer>
          );
        }}
      />
    )
  );
};

export default SearchResult;
