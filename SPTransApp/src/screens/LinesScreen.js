import React, { useState } from "react";
import {
  StyleSheet,
  View,
  Text,
  TextInput,
  TouchableOpacity,
  FlatList,
  ActivityIndicator,
} from "react-native";
import { searchBusLines, getArrivalPredictionsByLine } from "../api/api";

// Token de autenticação para API
const token =
  "0e2071e7091125f16dd4c99b6775b4c889a97c94e049872ea222c40fd1f30a86";

const LinhasScreen = () => {
  // Estado para armazenar o termo de busca, as linhas de ônibus e o estado de carregamento
  const [searchTerm, setSearchTerm] = useState("");
  const [busLines, setBusLines] = useState([]);
  const [loading, setLoading] = useState(false);

  // Função para lidar com a busca das linhas de ônibus
  const handleSearch = async () => {
    setLoading(true);
    try {
      // Busca as linhas de ônibus baseadas no token e no termo de busca
      const lines = await searchBusLines(token, searchTerm);
      
      // Para cada linha encontrada, busca as previsões de chegada
      const linesWithPredictions = await Promise.all(
        lines.map(async (line) => {
          try {
            const predictions = await getArrivalPredictionsByLine(token, line.cl);
            return { ...line, predictions };
          } catch (error) {
            console.error(`Erro ao buscar previsões para linha ${line.cl}:`, error);
            return line;
          }
        })
      );
      
      // Atualiza o estado das linhas de ônibus com as previsões de chegada
      setBusLines(linesWithPredictions);
    } catch (error) {
      console.error("Erro ao buscar linhas:", error);
    } finally {
      setLoading(false);
    }
  };

  // Função para renderizar cada item de linha de ônibus
  const renderItem = ({ item }) => (
    <TouchableOpacity style={styles.lineItem}>
      <Text style={styles.lineText}>{item.lt}</Text>
      <Text style={styles.lineDetail}>{`Operadora: ${item.cl}`}</Text>
      <Text style={styles.lineDetail}>{`Terminal inicial: ${item.tp}`}</Text>
      <Text style={styles.lineDetail}>{`Terminal final: ${item.ts}`}</Text>
      <Text style={styles.lineDetail}>{`Extensão da linha: ${item.tl} metros`}</Text>
      <Text style={styles.lineDetail}>{`Horário de operação: ${
        item.sl === 1 ? "Dia útil" : "Sábado e domingo"
      }`}</Text>
      {item.predictions && item.predictions.length > 0 ? (
        <View>
          <Text style={styles.predictionsTitle}>Previsões de Chegada:</Text>
          {item.predictions.map((prediction, index) => (
            <Text key={index} style={styles.predictionText}>
              Linha: {prediction.linha} - Previsto para: {prediction.previsao}
            </Text>
          ))}
        </View>
      ) : (
        <Text style={styles.noPredictionsText}>Não há previsões disponíveis para esta linha.</Text>
      )}
    </TouchableOpacity>
  );

  // Componente LinhasScreen que renderiza a tela de listagem de linhas de ônibus
  return (
    <View style={styles.container}>
      <TextInput
        style={styles.searchInput}
        placeholder="Digite o número ou nome da linha..."
        value={searchTerm}
        onChangeText={(text) => setSearchTerm(text)}
      />
      <TouchableOpacity style={styles.searchButton} onPress={handleSearch}>
        <Text style={styles.searchButtonText}>Buscar</Text>
      </TouchableOpacity>
      {loading ? (
        <ActivityIndicator size="large" color="#007bff" style={styles.loader} />
      ) : (
        <FlatList
          data={busLines}
          keyExtractor={(item) => item.cl.toString()}
          renderItem={renderItem}
          ListEmptyComponent={
            <Text style={styles.emptyText}>Nenhuma linha encontrada.</Text>
          }
        />
      )}
    </View>
  );
};

// Estilos do componente LinhasScreen
const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 10,
    backgroundColor: "#fff",
  },
  searchInput: {
    width: "100%",
    height: 40,
    borderColor: "#ccc",
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 10,
  },
  searchButton: {
    width: "100%",
    backgroundColor: "#007bff",
    paddingVertical: 10,
    borderRadius: 5,
    alignItems: "center",
  },
  searchButtonText: {
    color: "#fff",
    fontSize: 16,
  },
  lineItem: {
    width: "100%",
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: "#ccc",
  },
  lineText: {
    fontSize: 18,
    fontWeight: "bold",
  },
  lineDetail: {
    fontSize: 14,
    marginTop: 5,
  },
  predictionsTitle: {
    fontSize: 16,
    fontWeight: "bold",
    marginTop: 10,
  },
  predictionText: {
    fontSize: 14,
    marginTop: 5,
  },
  noPredictionsText: {
    fontSize: 14,
    color: "red",
    marginTop: 5,
  },
  emptyText: {
    textAlign: "center",
    marginTop: 20,
    fontSize: 16,
  },
  loader: {
    marginTop: 20,
  },
});

export default LinhasScreen;
