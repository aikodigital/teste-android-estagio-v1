import { Text, TouchableOpacity, View } from "react-native";
import { styles } from "./styles";

export function BusLine({ busLine }) {
  return busLine ? (
    <View style={styles.container}>
      <Text>{busLine.lt + "-" + busLine.tl}</Text>
      <Text>{busLine.tp}</Text>
      <Text>{busLine.ts}</Text>
    </View>
  ) : (
    <View style={styles.container}>
      <Text>Linha não encontrada</Text>
    </View>
  );
}
