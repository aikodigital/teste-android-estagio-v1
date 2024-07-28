import { useState } from 'react';
import 
{
     View, TextInput, Button,
    FlatList, Text, StyleSheet, TouchableOpacity 
} from 'react-native';

import { Header } from '../components/Header';


export function EstimatedArrival() {

  const [ searchTerm, setSearchTerm ] = useState('');
  const [ stops, setStops ] = useState([]);
  const [ selectedStop, setSelectedStop ] = useState(null);
  const [ predictions, setPredictions ] = useState([]);


  const fetchStops = async () => {

    try {
      const response = await fetch(`https://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=${searchTerm}`);
      const data = await response.json();
      setStops(data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchPredictions = async (stopId: any) => {

    try {
      const response = await fetch(`https://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=${stopId}`);
      const data = await response.json();
      
      setPredictions(data.p?.l || []);  
    } catch (error) {
      console.error(error);
    }
  };

  const handleStopSelect = (stop: any) => {
    setSelectedStop(stop);
    fetchPredictions(stop.cp);
  };

  return (
    <View style={styles.container}>
      {
        !selectedStop ? (
          <View>
            <Header title="Previsão de chegada"/> 
            <TextInput
              style={styles.input}
              placeholder="Procurar uma parada"
              placeholderTextColor="#ffffffc3"
              value={searchTerm}
              onChangeText={setSearchTerm}
            />
            <Button title="Procurar" onPress={fetchStops} />

            <FlatList
              data={stops}
              keyExtractor={(item) => item.cp.toString()}
              renderItem={({ item }) => (
                <TouchableOpacity onPress={() => handleStopSelect(item)}>
                  <Text style={styles.item}>{item.np}</Text>
                </TouchableOpacity>
              )}
            />
          </View>
        ) : (
          <View>
            <Button title="Voltar" onPress={() => setSelectedStop(null)} />

            <Text style={styles.title}>
              Previsão de chegada de {selectedStop.np}
            </Text>

            <FlatList
              data={predictions}
              keyExtractor={(item) => item.c.toString()}  
              renderItem={({ item }) => (
                <View style={styles.item}>
                  <Text style={styles.text}>Linha: {item.c} - {item.sl === 1 ? "Sentido bairro" : "Sentido centro"}</Text>
                  {item.vs.map((vehicle: any) => (
                    <View key={vehicle.p}>
                      <Text style={styles.text}>Veículo prefixo: {vehicle.p}</Text>
                      <Text style={styles.text}>Previsão de chegada: {vehicle.t}</Text>
                    </View>
                  ))}
                </View>
              )}
            />
          </View>
        )
      }
    </View>
  );
}

const styles = StyleSheet.create({

  container: {
    flex: 1,
    paddingTop: 40,
    alignItems: "center",
    backgroundColor: '#29292E', 
  },

  input: {
    height: 40,
    color: "#fff",
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 12,
    marginTop: 10,
    paddingHorizontal: 8,
  },
  
  item: {
    padding: 16,
    borderBottomColor: 'gray',
    color: "#fff",
    borderBottomWidth: 1,
  },

  title: {
    fontSize: 20,
    marginBottom: 16,
    color: '#fff',
  },

  text: {
    color: "#fff",
  }
});
