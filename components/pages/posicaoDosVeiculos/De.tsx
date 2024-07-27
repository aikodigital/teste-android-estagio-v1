import { Trajeto } from '@/app/(tabs)';
import React from 'react';
import { StyleSheet, Text } from 'react-native';
import RNPickerSelect from 'react-native-picker-select';

type Props = {
  setTrajeto: (value: React.SetStateAction<Trajeto>) => void;
  trajeto: Trajeto;
  regioes: string[];
};

const De = ({ setTrajeto, trajeto, regioes }: Props) => {
  return (
    <>
      <Text>De:</Text>
      <RNPickerSelect
        useNativeAndroidPickerStyle={false}
        onValueChange={(value) =>
          setTrajeto((prev) => ({ ...prev, de: value }))
        }
        value={trajeto.de}
        style={{ inputAndroid: styles.select }}
        items={[
          { label: 'Todos', value: 'Todos' },
          ...regioes.map((rergiao) => ({
            label: rergiao,
            value: rergiao,
          })),
        ]}
      />
    </>
  );
};

export default De;

const styles = StyleSheet.create({
  select: {
    borderColor: '1px solid #333',
    borderWidth: 1,
    borderRadius: 4,
    paddingHorizontal: 8,
    fontSize: 12,
  },
});
