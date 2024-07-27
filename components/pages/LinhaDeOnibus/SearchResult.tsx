import ContainerParaItem from '@/components/containers/ContainerParaItem';
import Erro from '@/components/form/error/Erro';
import Carregando from '@/components/form/loading/Carregando';
import ItemText from '@/components/text/ItemText';
import { Linha } from '@/types/types';
import React from 'react';
import { FlatList } from 'react-native';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  inputDePesquisa: string;
  linhas: Linha[];
};

const SearchResult = ({ formState, inputDePesquisa, linhas }: Props) => {
  if (formState.error) return <Erro messagem={formState.error} />;
  return formState.loading && !formState.error ? (
    <Carregando />
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
            <ContainerParaItem>
              <ItemText>
                Linha: {item.lt} - {item.tl}
              </ItemText>
              <ItemText>Código Identificador: {item.cl}</ItemText>
              <ItemText>Terminal Principal: {item.tp}</ItemText>
              <ItemText>Terminal Secundário: {item.ts}</ItemText>
              <ItemText>Sentido: {sl}</ItemText>
              <ItemText>Operação: {item.lc ? 'Circular' : 'Terminal'}</ItemText>
            </ContainerParaItem>
          );
        }}
      />
    )
  );
};

export default SearchResult;
