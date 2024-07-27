import React, { memo } from 'react';
import { StyleSheet, TextInput, TextInputProps, View } from 'react-native';
import LupaDoInputDeBusca from './LupaDoInputDeBusca';
import ContainerDoInputDeBusca from './ContainerDoInputDeBusca';

const InputDeBusca = ({ ...props }: TextInputProps) => {
  return (
    <ContainerDoInputDeBusca>
      <LupaDoInputDeBusca />
      <TextInput style={styles.input} {...props} />
    </ContainerDoInputDeBusca>
  );
};

export default memo(InputDeBusca);

const styles = StyleSheet.create({
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#333',
  },
});
