import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, ActivityIndicator } from 'react-native';
import ConfigAPI from '../config/services/ConfigAPI'; 

const Corridors = () => {
  const [corridors, setCorridors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

 // Função para buscar os dados dos corredores
  const fetchCorridors = async () => {
    try {
      const response = await ConfigAPI.get('/Corredor'); 
      setCorridors(response.data); 
    } catch (err) {
      setError('Erro ao buscar corredores.');
    } finally {
      setLoading(false); 
    }
  };

  useEffect(() => {
    fetchCorridors();
  }, []);

  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <Text style={styles.code}>Código: {item.cc}</Text>
      <Text style={styles.name}>Nome: {item.nc}</Text>
    </View>
  );

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#003184" />
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text style={styles.error}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={corridors}
        keyExtractor={(item) => item.cc.toString()}
        renderItem={renderItem}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  item: {
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
  },
  code: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  name: {
    fontSize: 14,
    color: '#333',
  },
  error: {
    color: 'red',
    textAlign: 'center',
    marginTop: 20,
  },
});

export default Corridors;
