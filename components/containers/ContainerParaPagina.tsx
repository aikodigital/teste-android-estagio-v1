import React from 'react';
import { StyleSheet, View, ViewProps } from 'react-native';

const ContainerParaPagina = (props: ViewProps) => {
  return (
    <View style={styles.ContainerParaPagina} {...props}>
      {props.children}
    </View>
  );
};

export default ContainerParaPagina;

const styles = StyleSheet.create({
  ContainerParaPagina: {
    paddingTop: 28,
    paddingBottom: 120,
    paddingHorizontal: 14,
    backgroundColor: '#f0f4f8',
  },
});
