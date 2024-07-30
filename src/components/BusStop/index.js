import { Text, View } from "react-native";
import { styles } from './style';

export function BusStop({ previsao }) {
  return (
    <View style={styles.container}>
      <Text>Linha: {previsao.c}</Text>
      <Text>Veículo: {previsao.lt0}</Text>
      <Text>Chegada: {previsao.itemT}</Text>
    </View>
  );
}
