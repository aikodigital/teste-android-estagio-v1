import React, { useState } from 'react';
import { View, Text, TextInput, Button, StyleSheet } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import { SearchCurrentPosition } from '../services/SearchCurrentPosition';

const RealTime = () => {
  const [lineNumber, setLineNumber] = useState('');
  const [data, setData] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchCurrentPosition = async (line) => {
    setLoading(true);
    setError('');

    try {
      const response = await SearchCurrentPosition(line.trim());
      if (response && Array.isArray(response)) {
        setData(response);
      } else {
        setData([]);
        setError('Dados retornados não são válidos.');
      }
    } catch (err) {
      setError('Erro ao buscar a posição atual dos veículos.');
      setData([]);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = () => {
    fetchCurrentPosition(lineNumber);
  };

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Digite o número da linha"
        value={lineNumber}
        onChangeText={setLineNumber}
        style={styles.input}
      />
      <Button title="Buscar" onPress={handleSearch} color="#003184" />
      {loading && <Text style={styles.loading}>Carregando...</Text>}
      {error && <Text style={styles.error}>{error}</Text>}
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.55,
          longitude: -46.63,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
        showsUserLocation={true}
      >
        {data.map(item => 
          item.vs.map(vehicle => (
            <Marker
              key={vehicle.p}
              coordinate={{ latitude: vehicle.py, longitude: vehicle.px }}
              title={`ônibus: ${vehicle.p}`}
              pinColor="blue"
            />
          ))
        )}
      </MapView>
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

export default RealTime;
