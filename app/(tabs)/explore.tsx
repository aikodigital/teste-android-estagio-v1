import Ionicons from "@expo/vector-icons/Ionicons";
import {
  StyleSheet,
  Image,
  Platform,
  SafeAreaView,
  Text,
  View,
} from "react-native";

import { ThemedText } from "@/components/ThemedText";
import { ThemedView } from "@/components/ThemedView";
import BusLinesScreen from "../screens/BusLinesScreen";
import BusLines from "../screens/BusLinesScreen";

export default function ExploreScreen() {
  return (
    <View style={styles.container}>
      <SafeAreaView>
        <Text style={styles.titleContainer}>Explore</Text>
      </SafeAreaView>
      <BusLines />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#ffffff",
  },
  titleContainer: {
    alignItems: "center",
    textAlign: "center",
    marginTop: 30,
    fontSize: 22,
    color: "#0000f8",
    width: "100%",
  },
});
