import React, { memo } from 'react';
import { StyleSheet, View } from 'react-native';
import De from './De';
import Para from './Para';
import {
  LinhaParaPosicao,
  RegiaoDoMapa,
  PosicaoVeiculo,
  Trajeto,
} from '@/types/types';

type Props = {
  regioes: string[];
  trajeto: Trajeto;
  setTrajeto: React.Dispatch<React.SetStateAction<Trajeto>>;
  linhas: LinhaParaPosicao<PosicaoVeiculo>[];
  setcoordenadasSp: React.Dispatch<React.SetStateAction<RegiaoDoMapa>>;
};

const InputsDeFiltro = ({
  regioes,
  trajeto,
  setTrajeto,
  linhas,
  setcoordenadasSp,
}: Props) => {
  return (
    regioes.length > 0 && (
      <View style={styles.inputContainer}>
        <De regioes={regioes} setTrajeto={setTrajeto} trajeto={trajeto} />
        {trajeto.de !== 'Todos' && (
          <Para
            linhas={linhas}
            setcoordenadasSp={setcoordenadasSp}
            setTrajeto={setTrajeto}
            trajeto={trajeto}
          />
        )}
      </View>
    )
  );
};

export default memo(InputsDeFiltro);

const styles = StyleSheet.create({
  inputContainer: {
    display: 'flex',
    flexWrap: 'wrap',
    flexDirection: 'row',
    gap: 8,
    shadowColor: '#000',
    padding: 12,
    backgroundColor: '#f5f5f5',
    opacity: 0.9,
    alignItems: 'center',
    position: 'absolute',
    bottom: 50,
    left: 0,
    width: '105%',
  },
});
