import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const Erro = ({ messagem }: { messagem: string }) => {
  return (
    <View style={styles.containerDeErro}>
      <Text style={styles.mensagemDeErro}>{messagem}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  containerDeErro: {
    backgroundColor: '#f2dede', // Cor de fundo para indicar erro
    padding: 10,
    borderRadius: 5,
    marginBottom: 10,
  },
  mensagemDeErro: {
    color: '#a94442', // Cor do texto de erro
    fontSize: 16,
    fontWeight: 'bold',
  },
});

export default Erro;
