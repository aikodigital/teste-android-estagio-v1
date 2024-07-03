import React from "react";
import { View, Text, FlatList } from "react-native";

const Arrival = ({ arrivalPredictions }) => {
  // Função para renderizar cada item de previsão de chegada
  const renderPredictionItem = ({ item }) => (
    <View style={styles.predictionItem}>
      <Text style={styles.predictionText}>
        Linha: {item.c} - Previsto para: {item.vs[0]?.t}
      </Text>
    </View>
  );

  // Componente de chegada que renderiza as previsões de chegada
  return (
    <View style={styles.predictionsContainer}>
      <Text style={styles.predictionsHeader}>
        Previsões de Chegada
      </Text>
      {arrivalPredictions.length > 0 ? (
        <FlatList
          data={arrivalPredictions}
          keyExtractor={(item) => item.p.toString()}
          renderItem={renderPredictionItem}
        />
      ) : (
        <Text>Nenhuma previsão disponível para esta parada.</Text>
      )}
    </View>
  );
};

// Estilos do componente Arrival
const styles = {
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
  predictionItem: {
    marginBottom: 10,
  },
  predictionText: {
    fontSize: 14,
  },
};

export default Arrival;
