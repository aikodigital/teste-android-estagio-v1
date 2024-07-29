import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Button, ScrollView, KeyboardAvoidingView, Alert } from 'react-native';
import MapView, { Marker } from 'react-native-maps';

const SPTRANS_API_KEY = 'c0f5edd03e7e638f5c49159864abc5d965180de393221857083c7cfa813c61ec';

export default function TabTwoScreen() {
  const [region, setRegion] = useState({
    latitude: -23.55052,
    longitude: -46.633308,
    latitudeDelta: 0.0922,
    longitudeDelta: 0.0421,
  });
  const [search, setSearch] = useState('');
  const [markers, setMarkers] = useState<{ latitude: number; longitude: number }[]>([]);
  const [authenticated, setAuthenticated] = useState(false);

  useEffect(() => {
    authenticate();
  }, []);

  const authenticate = async () => {
    try {
      const response = await fetch('http://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=' + SPTRANS_API_KEY, {
        method: 'GET',
      });
      const success = await response.json();
      if (success) {
        setAuthenticated(true);
      } else {
        Alert.alert('Erro', 'Falha na autenticação com a API SPTrans');
      }
    } catch (error) {
      Alert.alert('Erro', 'Falha na autenticação com a API SPTrans');
    }
  };

  const handleSearch = async () => {
    if (!authenticated) {
      Alert.alert('Erro', 'Não autenticado com a API SPTrans');
      return;
    }

    try {
      const response = await fetch(`http://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=${search}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await response.json();
      console.log('Resposta da API para parada:', data); // Adicionado para depuração

      if (data.length > 0) {
        const parada = data[0];
        console.log('Parada:', parada); // Adicionado para depuração

        const latitude = parseFloat(parada.py); // Atualizado para 'py'
        const longitude = parseFloat(parada.px); // Atualizado para 'px'

        if (!isNaN(latitude) && !isNaN(longitude)) {
          setRegion({
            latitude,
            longitude,
            latitudeDelta: 0.0922,
            longitudeDelta: 0.0421,
          });
          setMarkers([{ latitude, longitude }]);
        } else {
          Alert.alert('Erro', 'Coordenadas da parada inválidas');
        }
      } else {
        Alert.alert('Nenhuma parada encontrada');
      }
    } catch (error) {
      console.error('Erro na busca da parada:', error); // Adicionado para depuração
      Alert.alert('Erro', 'Falha na busca da parada');
    }
  };

  const showAllVehicles = async () => {
    if (!authenticated) {
      Alert.alert('Erro', 'Não autenticado com a API SPTrans');
      return;
    }

    try {
      const response = await fetch('http://api.olhovivo.sptrans.com.br/v2.1/Posicao', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data = await response.json();
      const vehicles = data.l.flatMap((linha: any) => linha.vs);
      const newMarkers = vehicles.map((vehicle: any) => ({
        latitude: vehicle.py,
        longitude: vehicle.px,
      }));
      setMarkers(newMarkers);
    } catch (error) {
      console.error('Erro na busca das posições dos veículos:', error); // Adicionado para depuração
      Alert.alert('Erro', 'Falha na busca das posições dos veículos');
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Busque uma parada e veja sua localização no mapa</Text>
      <Text style={styles.subtitle}>
        Realize uma busca das paradas de ônibus do sistema SPTrans com base no nome informado.
      </Text>

      <KeyboardAvoidingView behavior="padding" style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          placeholder="Digite o nome ou endereço da parada"
          value={search}
          onChangeText={setSearch}
        />
        <Button title="BUSCAR" onPress={handleSearch} />
      </KeyboardAvoidingView>

      <Text style={styles.sectionTitle}>Posição dos veículos:</Text>
      <Text style={styles.subtitle}>
        Veja onde todos os veículos estavam na última atualização.
      </Text>
      <Button title="Ver posição no mapa" onPress={showAllVehicles} />

      <View style={styles.mapContainer}>
        <MapView
          style={styles.map}
          region={region}
          onRegionChangeComplete={setRegion}
        >
          {markers.map((marker, index) => (
            <Marker key={index} coordinate={marker} />
          ))}
        </MapView>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
    marginTop: 20,
  },
  subtitle: {
    fontSize: 16,
    marginBottom: 20,
    textAlign: 'center',
    paddingHorizontal: 10,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    width: '100%',
    marginBottom: 20,
  },
  input: {
    flex: 1,
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginRight: 10,
  },
  mapContainer: {
    width: '100%',
    height: 400,
    marginTop: 20,
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});