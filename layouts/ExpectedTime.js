import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet, FlatList } from 'react-native';

import { SearchExpectedTime } from '../config/services/SearchExpectedTime'; 

const ExpectedTime = () => {
  const [stop, setStop] = useState('');
  const [data, setData] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (stop.trim() === '') {
      setError('Digite um ponto de parada.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await SearchExpectedTime(stop);

      if (response && response.ps && response.ps.length > 0) {
        setData(response.ps);
      } else {
        setError('Nenhuma informação encontrada para a linha pesquisada.');
        setData(null);
      }
    } catch (err) {
      setError('Erro ao buscar informações de posições.');
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <Text style={styles.itemText}>Ponto: <Text style={styles.boldText}>{item.np}</Text></Text>
      <FlatList
        data={item.vs}
        keyExtractor={(vehicle) => vehicle.p}
        renderItem={({ item }) => (
          <View style={styles.stopItem}>
            <Text style={styles.stopText}>Veículo: <Text style={styles.boldText}>{item.p}</Text></Text>
            <Text style={styles.stopText}>Previsão: <Text style={styles.boldText}>{item.t}</Text></Text>
            <Text style={styles.stopText}>Acessível: <Text style={styles.boldText}>{item.a ? 'Sim' : 'Não'}</Text></Text>
          </View>
        )}
      />
    </View>
  );

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Digite uma linha de ônibus."
        value={stop}
        onChangeText={setStop}
        style={styles.input}
      />
      <Button title="Buscar" onPress={handleSearch} color="#003184" />

      {loading && <Text style={styles.loading}>Buscando...</Text>}
      {error ? (
        <Text style={styles.error}>{error}</Text>
      ) : (
        data ? (
          <FlatList
            data={data}
            keyExtractor={(item, index) => index.toString()}
            renderItem={renderItem}
          />
        ) : (
          <Text style={styles.placeholderText}>
            Informe uma linha de ônibus para buscar as informações de veículos em tempo real.
          </Text>
        )
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  input: {
    marginBottom: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    borderRadius: 5,
  },
  loading: {
    marginTop: 10,
    textAlign: 'center',
  },
  error: {
    color: 'red',
    textAlign: 'center',
    marginTop: 10,
  },
  item: {
    padding: 15,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#ddd',
    marginBottom: 10,
    backgroundColor: '#f9f9f9',
  },
  itemText: {
    fontSize: 14,
    marginBottom: 5,
  },
  boldText: {
    fontWeight: 'bold',
  },
  stopItem: {
    marginTop: 10,
    padding: 10,
    borderRadius: 5,
    borderWidth: 1,
    borderColor: '#ddd',
    backgroundColor: '#d4edda',
  },
  stopText: {
    fontSize: 12,
  },
  placeholderText: {
    marginTop: 20,
    fontSize: 16,
    color: '#888',
    textAlign: 'center',
  },
});

export default ExpectedTime;