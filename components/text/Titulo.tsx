import React, { memo, PropsWithChildren } from 'react';
import { StyleSheet, Text } from 'react-native';

const Titulo = ({ children }: PropsWithChildren) => {
  return <Text style={styles.title}>{children}</Text>;
};

export default memo(Titulo);

const styles = StyleSheet.create({
  title: {
    textAlign: 'center',
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 10,
  },
});
