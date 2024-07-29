import React, { useEffect, useState } from "react";
import { Button, FlatList, Modal, SafeAreaView, StyleSheet, Text, TextInput, TouchableOpacity, View } from "react-native";
import MapView, { Marker, Callout } from 'react-native-maps';
import { getBusPositions, getBusStops, getArrivalPrediction } from "@/services/APIServices";

interface BusPosition {
  py: number;
  px: number;
}

interface BusData {
  hr: string;
  l: Array<{
    lt0: string;
    lt1: string;
    vs: Array<{
      p: number;
      a: boolean;
      ta: string;
      py: number;
      px: number;
    }>
  }>
}

interface BusStop {
  cp: number;
  np: string;
  ed: string;
  py: number;
  px: number;
}

interface BusStopWithPrediction extends BusStop {
  prediction: string[];
}

export function Map() {
  const [search, setSearch] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [busStops, setBusStops] = useState<BusStop[]>([]);
  const [busPosition, setBusPosition] = useState<BusData | null>(null);
  const [selectedBusStop, setSelectedBusStop] = useState<BusStopWithPrediction | null>(null);
  const [showBuses, setShowBuses] = useState(false); // Novo estado para controlar a visibilidade dos ônibus

  const fetchBusPositions = async () => {
    const data = await getBusPositions();
    if (data) {
      setBusPosition(data);
    }
  };

  useEffect(() => {
    fetchBusPositions();
  }, []);

  const handleSearch = async () => {
    if (search) {
      const stops = await getBusStops(search);
      if (stops) {
        setBusStops(stops);
      }
    }
  };

  useEffect(() => {
    handleSearch();
  }, [search]);

  const handleMarkerPress = async (stop: BusStop) => {
    const predictionData = await getArrivalPrediction(stop.cp);
    let prediction: string[] = [];

    if (predictionData && predictionData.p && predictionData.p.l) {
      prediction = predictionData.p.l.map((line: any) => {
        const nextBus = line.vs[0];
        return `Próximo: ${nextBus.t} min`;
      });
    }

    setSelectedBusStop({ ...stop, prediction });
    setModalVisible(true);
  };

  const handleShowBuses = () => {
    if (showBuses) {
      setBusPosition(null); // Remove as posições dos ônibus
    } else {
      fetchBusPositions(); // Busca as posições dos ônibus
    }
    setShowBuses(!showBuses); // Alterna a visibilidade
  };

  const renderPredictionItem = ({ item }: { item: string }) => (
    <View style={styles.predictionItem}>
      <Text>{item}</Text>
    </View>
  );

  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.Title}>spBus</Text>
      <View style={styles.searchContainer}>
        <TextInput
          style={styles.searchInput}
          placeholder="Pesquise paradas por bairro"
          value={search}
          onChangeText={setSearch}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button title={showBuses ? "Ocultar Ônibus" : "Mostrar Todos os Ônibus"} onPress={handleShowBuses} />
      </View>
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.5505,
          longitude: -46.6333,
          latitudeDelta: 1,
          longitudeDelta: 1,
        }}
      >
        {showBuses && busPosition?.l.map((line, index) =>
          line.vs.map((bus, busIndex) => (
            <Marker
              key={`${index}=${busIndex}`}
              coordinate={{
                latitude: bus.py,
                longitude: bus.px,
              }}
              title={`${bus.p}`}
              description={`${line.lt1} - ${line.lt0}`}
              pinColor={"#00f"}
            />
          ))
        )}
        {busStops.map((stop, index) => (
          <Marker
            key={index}
            coordinate={{
              latitude: stop.py,
              longitude: stop.px,
            }}
          >
            <Callout>
              <View style={{ width: 200 }}>
                <Text style={{ fontWeight: 'bold' }}>{stop.np}</Text>
                <Text>ENDEREÇO: {stop.ed}</Text>
                <TouchableOpacity onPress={() => handleMarkerPress(stop)}>
                  <Text style={{ color: 'black', marginVertical: 5 }}>Clique para ver a previsão</Text>
                </TouchableOpacity>
              </View>
            </Callout>
          </Marker>
        ))}
      </MapView>
      {selectedBusStop && (
        <Modal
          animationType="slide"
          transparent={true}
          visible={modalVisible}
          onRequestClose={() => setModalVisible(false)}
        >
          <View style={styles.modalContainer}>
            <View style={styles.modalContent}>
              <Text style={styles.modalTitle}>Previsão de Chegada</Text>
              <Text style={styles.modalSubTitle}>{selectedBusStop.np}</Text>
              <FlatList
                data={selectedBusStop.prediction}
                renderItem={renderPredictionItem}
                keyExtractor={(item, index) => index.toString()}
              />
              <Button title="Fechar" onPress={() => setModalVisible(false)} />
            </View>
          </View>
        </Modal>
      )}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  Title: {
    fontSize: 24,
    fontWeight: 'bold',
    alignSelf: 'center',
    marginTop: 25,
  },
  buttonContainer: {
    width: '80%',
    alignSelf: 'center',
    marginBottom: 10,
  },
  searchContainer: {
    width: '80%',
    alignSelf: 'center',
    marginTop: 10,
    marginBottom: 10,
  },
  searchInput: {
    height: 40,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
  },
  map: {
    flex: 1,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0,0,0,0.5)',
  },
  modalContent: {
    width: 300,
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 20,
    alignItems: 'center',
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  modalSubTitle: {
    marginVertical: 10,
    fontSize: 16,
  },
  predictionItem: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
});
