import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

// Componente funcional BusDetails recebe o parâmetro route desestruturado do props
const BusDetails = ({ route }) => {
  // Extrai o objeto bus do parâmetro route.params
  const { bus } = route.params;

  // Renderiza a interface do componente
  return (
    <View style={styles.container}>
      {/* Título dos detalhes do ônibus */}
      <Text style={styles.title}>Detalhes do Ônibus</Text>
      {/* Informação do prefixo do ônibus */}
      <Text style={styles.info}>Prefixo: {bus.prefixo}</Text>
      {/* Informação da linha do ônibus */}
      <Text style={styles.info}>Linha: {bus.linha}</Text>
      {/* Informação da latitude do ônibus */}
      <Text style={styles.info}>Latitude: {bus.py}</Text>
      {/* Informação da longitude do ônibus */}
      <Text style={styles.info}>Longitude: {bus.px}</Text>
    </View>
  );
};

// Estilos CSS para o componente BusDetails
const styles = StyleSheet.create({
  // Estilo do container principal
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  // Estilo do título
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  // Estilo das informações
  info: {
    fontSize: 18,
    marginBottom: 10,
  },
});

// Exporta o componente BusDetails como padrão
export default BusDetails;
