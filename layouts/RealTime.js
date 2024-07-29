import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, Button, StyleSheet } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import { SearchCurrentPosition } from '../config/services/SearchCurrentPosition';

const RealTime = () => {
  const [lineNumber, setLineNumber] = useState('');
  const [data, setData] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const fetchCurrentPosition = async () => {
    if (lineNumber.trim() === '') {
      setError('Digite uma linha de ônibus:');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await SearchCurrentPosition(lineNumber);
      //console.log('API Response:', response);
      if (response && response.vs) {
        setData(response);
      } else {
        setError('Nenhuma informação encontrada para a linha informada.');
        setData(null);
      }
    } catch (err) {
      setError('Erro ao buscar a posição atual dos veículos.');
      setData(null);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      fetchCurrentPosition();
    }, 5000); // Atualiza a cada 5 segundos

    return () => clearInterval(interval);
  }, [lineNumber]);

  const renderMarkers = () => {
    if (data && data.vs) {
      //console.log('Markers Data:', data.vs); 
      return data.vs.map((vehicle) => (
        <Marker
          key={vehicle.p}
          coordinate={{ latitude: vehicle.py, longitude: vehicle.px }}
          title={`Veículo ${vehicle.p}`}
        />
      ));
    }
    return null;
  };

  // Ajusta a região do mapa com base na posição do primeiro veículo se disponível
  const mapRegion = data && data.vs && data.vs.length > 0
    ? {
        latitude: data.vs[0].py,
        longitude: data.vs[0].px,
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
      }
    : {
        latitude: -23.55,
        longitude: -46.63,
        latitudeDelta: 0.0922,
        longitudeDelta: 0.0421,
      };

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Digite o número da linha"
        value={lineNumber}
        onChangeText={setLineNumber}
        style={styles.input}
      />
      <Button title="Buscar" onPress={fetchCurrentPosition} color="#003184" />

      {loading && <Text style={styles.loading}>Buscando...</Text>}

      {error ? (
        <Text style={styles.error}>{error}</Text>
      ) : (
        <MapView
          style={styles.map}
          region={mapRegion} // Atualize a região do mapa dinamicamente
        >
          {renderMarkers()}
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

export default RealTime;
