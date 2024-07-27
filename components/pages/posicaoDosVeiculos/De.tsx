import { Trajeto } from '@/types/types';
import React from 'react';
import { StyleSheet, Text, View } from 'react-native';
import RNPickerSelect from 'react-native-picker-select';

type Props = {
  setTrajeto: (value: React.SetStateAction<Trajeto>) => void;
  trajeto: Trajeto;
  regioes: string[];
};

const De = ({ setTrajeto, trajeto, regioes }: Props) => {
  return (
    <View style={styles.selectContainer}>
      <Text style={styles.label}>De:</Text>
      <RNPickerSelect
        useNativeAndroidPickerStyle={false}
        onValueChange={(value) =>
          setTrajeto((prev) => ({ ...prev, de: value }))
        }
        value={trajeto.de}
        style={{ inputAndroid: styles.select, inputIOS: styles.select }}
        items={[
          { label: 'Todos', value: 'Todos' },
          ...regioes.map((regiao) => ({
            label: regiao,
            value: regiao,
          })),
        ]}
      />
    </View>
  );
};

export default De;

export const styles = StyleSheet.create({
  select: {
    borderColor: '#333',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 10,
    backgroundColor: '#fff',
    color: '#333',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 4,
    elevation: 2,
    fontSize: 11,
  },
  selectContainer: {
    flexDirection: 'row',
    gap: 8,
    alignItems: 'center',
    backgroundColor: '#f9f9f9',
    padding: 10,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 1,
  },
  label: {
    fontWeight: 'bold',
    color: '#333',
  },
});
