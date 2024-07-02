import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  ActivityIndicator,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import MapView, { Marker } from "react-native-maps";
import { useLocalSearchParams, useNavigation } from "expo-router";
import axios from "axios";
import { Ionicons } from "@expo/vector-icons";
import { theme } from "../theme";

interface CorredorDetails {
  nome: string;
  localizacao: string;
  latitude: number;
  longitude: number;
}

export default function Corredores() {
  const codigoCorredor = useLocalSearchParams<{ nc: string }>();
  const [corredorDetails, setCorredorDetails] =
    useState<CorredorDetails | null>(null);
  const navigation = useNavigation();

  useEffect(() => {
    const fetchCorredorDetails = async () => {
      try {
        const response = await axios.get(
          `https://maps.googleapis.com/maps/api/geocode/json?address=corredor${codigoCorredor.nc}&key=AIzaSyAShYytzPWQdXUMhCf1p-Y6pMUxHW53fBI`,
        );
        if (response.data.results.length > 0) {
          const result = response.data.results[0];
          const nome = result.formatted_address;
          const latitude = result.geometry.location.lat;
          const longitude = result.geometry.location.lng;
          const localizacao = result.place_id;
          setCorredorDetails({ nome, localizacao, latitude, longitude });
        }
      } catch (error) {
        console.error("Erro ao buscar detalhes do corredor:", error);
      }
    };

    fetchCorredorDetails();
  }, [codigoCorredor]);

  if (!corredorDetails) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#0000ff" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.backButton}
        >
          <Ionicons name="arrow-back" size={24} color="white" />
        </TouchableOpacity>
        <Text style={styles.title}>Detalhes do Corredor</Text>
      </View>
      <Text style={styles.text}>Nome: {corredorDetails.nome}</Text>
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: corredorDetails.latitude,
          longitude: corredorDetails.longitude,
          latitudeDelta: 0.01,
          longitudeDelta: 0.01,
        }}
      >
        <Marker
          coordinate={{
            latitude: corredorDetails.latitude,
            longitude: corredorDetails.longitude,
          }}
          title={corredorDetails.nome}
        />
      </MapView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-evenly",
    gap: 14,
    marginBottom: 20,
  },
  backButton: {
    backgroundColor: theme.colors.dark_blue,
    padding: 6,
    borderRadius: 8,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    marginBottom: 10,
  },
  text: {
    fontSize: 16,
    marginBottom: 5,
  },
  map: {
    width: "100%",
    height: 400,
    marginTop: 20,
  },
});
