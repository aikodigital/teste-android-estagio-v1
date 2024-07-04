import {
  StyleSheet,
  View,
  Text,
  StatusBar,
  useColorScheme,
} from "react-native";

import VehicleMap from "../screens/VehicleScreen";
import BusStopMap from "../screens/BusStopMapScreen";

export default function HomeScreen() {
  const colorScheme = useColorScheme();
  return (
    <>
      <View style={styles.container}>
        <StatusBar barStyle={"dark-content"} />
        <Text style={styles.titleContainer}>Olho Vivo Bus</Text>
      </View>
      <BusStopMap lineCode={0} baseUrl={""} />
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    width: "100%",
  },
  titleContainer: {
    // flexDirection: "row",
    alignItems: "center",
    // backgroundColor: "#5f557b",
    textAlign: "center",
    marginTop: 30,
    fontSize: 22,
    color: "#0000f8",
    width: "100%",
  },
});
