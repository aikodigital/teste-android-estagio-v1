import React, { useEffect, useState } from "react";
import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  TextInput,
  FlatList,
  Image,
  KeyboardAvoidingView,
  Platform,
  ScrollView,
} from "react-native";
import LinesScreen from "./src/screens/LinesScreen"; // Substitua pelo caminho correto do arquivo

import MapView, { Marker } from "react-native-maps"; // Importa o MapView e Marker do react-native-maps
import {
  getVehiclesPositions,
  getBusStops,
  getArrivalPredictions,
  getBusLines,
  searchBusStops,
} from "./src/api/api"; // Importa funções da API para buscar dados
import busIcon from "./src/assets/bus.png"; // Ícone de ônibus
import busStopIcon from "./src/assets/stopbus.webp"; // Ícone de parada de ônibus

const token =
  "0e2071e7091125f16dd4c99b6775b4c889a97c94e049872ea222c40fd1f30a86"; // Token de autenticação da API

const App = () => {
  // Definição dos estados
  const [vehiclePositions, setVehiclePositions] = useState([]);
  const [busStops, setBusStops] = useState([]);
  const [busLines, setBusLines] = useState([]);
  const [lastUpdateTime, setLastUpdateTime] = useState("");
  const [selectedStop, setSelectedStop] = useState(null);
  const [arrivalPredictions, setArrivalPredictions] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [activeTab, setActiveTab] = useState("Local");

  // Hook useEffect para carregar dados iniciais
  useEffect(() => {
    const fetchData = async () => {
      try {
        // Busca as posições dos veículos
        const positions = await getVehiclesPositions(token);
        if (positions && positions.l) {
          setVehiclePositions(positions.l);
          setLastUpdateTime(positions.hr);
        }

        // Busca as paradas de ônibus
        const stops = await getBusStops(token);
        setBusStops(stops);

        // Busca as linhas de ônibus
        const lines = await getBusLines(token);
        setBusLines(lines);
      } catch (error) {
        console.error("Erro ao buscar dados:", error);
      }
    };

    fetchData(); // Chama a função fetchData ao montar o componente
  }, []); // Array vazio para garantir que o useEffect só execute uma vez

  // Função para lidar com o pressionar de uma parada de ônibus
  const handleStopPress = async (stop) => {
    setSelectedStop(stop);
  
    try {
      const stopId = stop.cp;
      const lineId = stop.cl; // Utiliza stop.cl em vez de selectedLine.cl
  
      if (lineId) {
        const predictions = await getArrivalPredictions(token, stopId, lineId);
  
        if (predictions.length > 0) {
          setArrivalPredictions(predictions);
          setIsModalVisible(true);
          sendArrivalPredictionNotification(predictions);
        } else {
          setArrivalPredictions([]);
          setIsModalVisible(false);
          console.log(`Não foram encontradas previsões para a parada ${stopId}`);
        }
      } else {
        console.log("ID da linha não definido.");
        setArrivalPredictions([]);
        setIsModalVisible(false);
      }
    } catch (error) {
      if (error.response && error.response.status === 404) {
        console.log(`Não foram encontradas previsões para a parada ${stop.cp}`);
        setArrivalPredictions([]);
        setIsModalVisible(false);
      } else {
        console.error("Erro ao buscar previsões de chegada:", error.message);
        setArrivalPredictions([]);
        setIsModalVisible(false);
      }
    }
  };
  

  // Função para limpar o termo de busca
  const clearSearchTerm = () => {
    setSearchTerm("");
  };

  // Função para lidar com o pressionar de uma aba
  const handleTabPress = (tabName) => {
    setActiveTab(tabName);
  };

  // Função para lidar com a busca de paradas de ônibus
  const handleSearch = async () => {
    try {
      const stops = await searchBusStops(token, searchTerm); // Busca paradas de ônibus pelo termo de busca
      setBusStops(stops); // Atualiza a lista de paradas de ônibus com o resultado da busca
    } catch (error) {
      if (error.response) {
        console.error("Erro ao buscar paradas:", error.response.status);
      } else if (error.request) {
        console.error("Erro ao buscar paradas: Não houve resposta do servidor");
      } else {
        console.error("Erro ao buscar paradas:", error.message);
      }
    }
  };

  // Componente para renderizar cada item de previsão de chegada
  const renderPredictionItem = ({ item }) => (
    <View style={styles.predictionItem}>
      <Text style={styles.predictionText}>
        Linha: {item.c} - Previsto para: {item.vs[0]?.t}
      </Text>
    </View>
  );

  // Renderização do componente
  return (
    <KeyboardAvoidingView
      style={styles.container}
      behavior={Platform.OS === "ios" ? "padding" : "height"}
    >
      <View style={styles.header}>
        <View style={styles.containerTitle}>
          <Text style={styles.title}>SPTrans</Text>
        </View>
        <View style={styles.searchContainer}>
          {searchTerm.length > 0 && (
            <TouchableOpacity
              style={styles.clearButton}
              onPress={clearSearchTerm}
            >
              <Text style={styles.clearButtonText}>Limpar</Text>
            </TouchableOpacity>
          )}
        </View>
        <ScrollView horizontal showsHorizontalScrollIndicator={false}>
          {["Local", "Linhas", "Paradas", "Previsão"].map((tabName) => (
            <TouchableOpacity
              key={tabName}
              style={[
                styles.tabButton,
                activeTab === tabName && styles.activeTab,
              ]}
              onPress={() => handleTabPress(tabName)}
            >
              <Text style={styles.tabButtonText}>{tabName}</Text>
            </TouchableOpacity>
          ))}
        </ScrollView>
      </View>

      {activeTab === "Local" && (
        <MapView
          style={styles.map}
          initialRegion={{
            latitude: -23.5505,
            longitude: -46.6333,
            latitudeDelta: 0.0922,
            longitudeDelta: 0.0421,
          }}
        >
          {vehiclePositions.map((vehicle, index) =>
            vehicle.vs.map((position, idx) => (
              <Marker
                key={`${index}-${idx}`}
                coordinate={{
                  latitude: position.py || 0,
                  longitude: position.px || 0,
                }}
                title={`Ônibus: ${vehicle.cl || "Desconhecido"}`}
                description={`Última atualização: ${
                  position.ta ?? "Não encontrada"
                }`}
              >
                <Image source={busIcon} style={{ width: 35, height: 35 }} />
              </Marker>
            ))
          )}
        </MapView>
      )}

      {activeTab === "Linhas" && <LinesScreen />}

      {activeTab === "Paradas" && (
        <>
          <TextInput
            style={styles.searchInput}
            placeholder="Digite o nome ou endereço da parada..."
            value={searchTerm}
            onChangeText={(text) => setSearchTerm(text)}
          />
          <TouchableOpacity
            style={styles.searchButton}
            onPress={handleSearch}
          >
            <Text style={styles.searchButtonText}>Buscar</Text>
          </TouchableOpacity>

          <MapView
            style={styles.map}
            initialRegion={{
              latitude: -23.5505,
              longitude: -46.6333,
              latitudeDelta: 0.0922,
              longitudeDelta: 0.0421,
            }}
          >
            {busStops.map((stop, index) => (
              <Marker
                key={index}
                coordinate={{
                  latitude: stop.py || 0,
                  longitude: stop.px || 0,
                }}
                title={`Parada: ${stop.np || "Desconhecida"}`}
                description={`Endereço: ${stop.ed ?? "Não encontrado"}`}
              >
                <TouchableOpacity onPress={() => handleStopPress(stop)}>
                  <Image
                    source={busStopIcon}
                    style={{ width: 40, height: 40 }}
                  />
                </TouchableOpacity>
              </Marker>
            ))}
          </MapView>

          {selectedStop && (
            <View style={styles.predictionsContainer}>
              <Text style={styles.predictionsTitle}>
                Previsões de chegada para: {selectedStop.np}
              </Text>
              {arrivalPredictions.length > 0 ? (
                <FlatList
                  data={arrivalPredictions}
                  keyExtractor={(item) => item.cl.toString()}
                  renderItem={renderPredictionItem}
                />
              ) : (
                <Text style={styles.noPredictionsText}>
                  Não há previsões disponíveis para esta parada.
                </Text>
              )}
            </View>
          )}
        </>
      )}
    </KeyboardAvoidingView>
  );
};

export default App;

// Estilos do componente
const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginTop: Platform.OS === "ios" ? 40 : 0,
    backgroundColor: "#f0f0f0",
  },
  containerTitle: {
    justifyContent: "center",
    alignItems: "center",
  },
  title: {
    margin: 10,
    fontWeight: "bold",
    fontSize: 30,
    color: "#f0f0f0",
  },
  header: {
    backgroundColor: "#e6263c",
    padding: 10,
    marginBottom: 10,
  },
  searchContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 10,
  },
  clearButton: {
    paddingHorizontal: 10,
    paddingVertical: 5,
  },
  clearButtonText: {
    color: "#f0f0f0",
  },
  tabButton: {
    padding: 10,
    alignItems: "center",
    borderBottomWidth: 2,
    borderBottomColor: "#ccc",
    marginHorizontal: 10,
  },
  tabButtonText: {
    color: "#fff",
    fontWeight: "bold",
  },
  activeTab: {
    borderBottomColor: "#fff",
  },
  map: {
    flex: 1,
  },
  modalContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(0,0,0,0.5)",
  },
  modalContent: {
    width: "80%",
    padding: 10,
    backgroundColor: "#fff",
    borderRadius: 5,
    alignItems: "center",
  },
  modalInner: {
    width: "100%",
  },
  predictionItem: {
    marginBottom: 10,
  },
  predictionsContainer: {
    position: "absolute",
    bottom: 50,
    left: 10,
    right: 10,
    backgroundColor: "rgba(255, 255, 255, 0.9)",
    padding: 10,
    borderRadius: 8,
  },
  predictionsHeader: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 10,
  },
  searchInput: {
    margin: 10,
    height: 40,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    backgroundColor: "#fff",
  },
  searchButton: {
    margin: 10,
    backgroundColor: "#007bff",
    paddingVertical: 10,
    borderRadius: 5,
    alignItems: "center",
  },
  searchButtonText: {
    color: "#fff",
    fontSize: 16,
  },
  loader: {
    position: "absolute",
    top: 120,
  },
  noPredictionsText: {
    fontSize: 14,
    color: "red",
  },
});
