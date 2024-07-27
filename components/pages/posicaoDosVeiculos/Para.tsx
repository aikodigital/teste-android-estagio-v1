import { Trajeto } from '@/app/(tabs)';
import { LinhaParaPosicao, PosicaoVeiculo } from '@/types/posicao';
import React from 'react';
import { StyleSheet, Text } from 'react-native';
import RNPickerSelect from 'react-native-picker-select';

type Props = {
  setTrajeto: (value: React.SetStateAction<Trajeto>) => void;
  trajeto: Trajeto;
  linhas: LinhaParaPosicao<PosicaoVeiculo>[];
  setCordenadasSp: (
    value: React.SetStateAction<{
      latitude: number;
      longitude: number;
      latitudeDelta: number;
      longitudeDelta: number;
    }>
  ) => void;
};

const Para = ({ setTrajeto, trajeto, linhas, setCordenadasSp }: Props) => {
  return (
    <>
      <Text>Para:</Text>
      <RNPickerSelect
        useNativeAndroidPickerStyle={false}
        onValueChange={(value) => {
          setTrajeto((prev) => ({ ...prev, para: value }));
          setCordenadasSp((prev) => {
            const linhasFiltradas = linhas?.filter(
              (linha) => linha.lt1 === value
            );

            if (
              !linhasFiltradas ||
              !linhasFiltradas.length ||
              !linhasFiltradas[0] ||
              !linhasFiltradas[0].vs.length ||
              !linhasFiltradas[0].vs[0] ||
              !linhasFiltradas[0].vs[0].px ||
              !linhasFiltradas[0].vs[0].py
            )
              return prev;

            return {
              ...prev,
              latitude: linhasFiltradas[0].vs[0].py,
              longitude: linhasFiltradas[0].vs[0].px,
              latitudeDelta: 0.5,
            };
          });
        }}
        value={trajeto.para}
        style={{ inputAndroid: styles.select }}
        items={[
          { label: 'Todos', value: 'Todos' },
          ...linhas
            .filter(({ lt0 }) => lt0 === trajeto.de)
            .map(({ lt1 }) => ({
              label: lt1,
              value: lt1,
            })),
        ]}
      />
    </>
  );
};

export default Para;

const styles = StyleSheet.create({
  select: {
    borderColor: '1px solid #333',
    borderWidth: 1,
    borderRadius: 4,
    paddingHorizontal: 8,
    fontSize: 12,
  },
});
