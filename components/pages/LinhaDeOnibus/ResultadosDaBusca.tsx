import ContainerParaItem from '@/components/containers/ContainerParaItem';
import Erro from '@/components/form/error/Erro';
import Carregando from '@/components/form/loading/Carregando';
import TextoDeItem from '@/components/text/TextoDeItem';
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

const ResultadosDaBusca = ({ formState, inputDePesquisa, linhas }: Props) => {
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
              <TextoDeItem>
                Linha: {item.lt} - {item.tl}
              </TextoDeItem>
              <TextoDeItem>Código Identificador: {item.cl}</TextoDeItem>
              <TextoDeItem>Terminal Principal: {item.tp}</TextoDeItem>
              <TextoDeItem>Terminal Secundário: {item.ts}</TextoDeItem>
              <TextoDeItem>Sentido: {sl}</TextoDeItem>
              <TextoDeItem>
                Operação: {item.lc ? 'Circular' : 'Terminal'}
              </TextoDeItem>
            </ContainerParaItem>
          );
        }}
      />
    )
  );
};

export default ResultadosDaBusca;
