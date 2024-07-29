import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import { useRoute } from '@react-navigation/native';
import { SearchStops } from '../services/SearchStops';

const LineStops = () => {
  const route = useRoute();
  const { lineCode } = route.params || {}; 
  
  const [searchTerm, setSearchTerm] = useState('');
  const [stops, setStops] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    // Load all stops initially
    handleSearch('');
  }, []);

  useEffect(() => {
    if (lineCode) {
      setSearchTerm(lineCode);
      handleSearch(lineCode);
    }
  }, [lineCode]);

  const handleSearch = async (codeToSearch) => {
    const term = String(codeToSearch || searchTerm);

    if (typeof term !== 'string') {
      setError('Erro interno: term não é uma string.');
      return;
    }

    setLoading(true);
    setError('');
    
    try {
      const data = await SearchStops(term);
      if (Array.isArray(data)) {
        setStops(data);
      } else {
        setError('Erro ao buscar paradas da linha pesquisada.');
        setStops([]);
      }
    } catch (err) {
      setError('Erro ao buscar paradas da linha pesquisada.');
      setStops([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Digite o código da linha"
        value={searchTerm}
        onChangeText={setSearchTerm}
        style={styles.input}
      />
      <Button title="Buscar" onPress={() => handleSearch()} color="#003184" />

      {loading && <Text style={styles.loading}>Buscando...</Text>}
      
      {error ? (
        <Text style={styles.error}>{error}</Text>
      ) : (
        <MapView
          style={styles.map}
          initialRegion={{
            latitude: -23.55,
            longitude: -46.63,
            latitudeDelta: 0.0922,
            longitudeDelta: 0.0421, 
          }}
        >
          {stops.map((stop) => (
            <Marker
              key={stop.cp}
              coordinate={{ latitude: stop.py, longitude: stop.px }}
              title={`${stop.cp} - ${stop.np}`}
              description={stop.ed}
            />
          ))}
        </MapView>
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
  map: {
    flex: 1,
    marginTop: 20,
  },
});

export default LineStops;
