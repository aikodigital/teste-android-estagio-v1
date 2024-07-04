import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import {
  getCurrentPositionAsync,
  requestForegroundPermissionsAsync,
  LocationObject,
} from "expo-location";
import { useEffect, useState } from "react";

export default function ShowScreen() {
  const [location, setLocation] = useState<LocationObject | null>(null);

  async function requestLocationPermissions() {
    const { granted } = await requestForegroundPermissionsAsync();

    if (granted) {
      const currentPosition = await getCurrentPositionAsync();
      setLocation(currentPosition);

      // console.info("Localização atual, ", currentPosition);
    }
  }

  useEffect(() => {
    requestLocationPermissions();
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.titleContainer}>Show Map</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    backgroundColor: "#66ff00",
  },

  titleContainer: {
    alignItems: "center",
    backgroundColor: "#5f557b",
    textAlign: "center",
    justifyContent: "center",
    fontSize: 22,
    color: "#fff",
    width: "100%",
  },
});
