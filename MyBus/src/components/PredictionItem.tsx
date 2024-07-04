// src/components/PredictionItem.tsx

import React from 'react';
import { View, Text } from 'react-native';
import { Prediction } from '../types/interfaces';
import styles from '../styles/MapScreenStyles';

type PredictionItemProps = {
  item: Prediction;
}

const PredictionItem: React.FC<PredictionItemProps> = ({ item }) => (
  <View style={styles.prediction}>
    <Text style={styles.predictionText}>Linha: {item.c}</Text>
    {item.vs.map((vehicle, index) => (
      <Text key={index} style={styles.predictionText}>
        Veículo: {vehicle.p} - Chegada Prevista: {vehicle.t}
      </Text>
    ))}
  </View>
);

export default PredictionItem;
