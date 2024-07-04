import React, { useEffect, useState, useRef } from "react";
import { StyleSheet, View, Text, ActivityIndicator, Alert } from "react-native";
import MapView, { Marker } from "react-native-maps";
import * as Location from "expo-location";

interface VehiclePosition {
  px: number; // longitude
  py: number; // latitude
  p: string; // some property, e.g., id or name
  ta: string; // last updated timestamp
}

interface Vehicle {
  c: string; // some property, e.g., id or name
  vs: VehiclePosition[];
}

const VehicleMap = () => {
  const [vehicles, setVehicles] = useState<VehiclePosition[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const mapRef = useRef<MapView>(null); // Referência para o componente MapView

  useEffect(() => {
    const token =
      "6139d58e7b6e874fc32994d53dfb25b453a27a07a900b2047fb093c622ce4ee3";

    const authenticate = async () => {
      try {
        const authResponse = await fetch(
          `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${token}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({ token }),
          }
        );

        if (!authResponse.ok) {
          const errorText = await authResponse.text();
          console.error(
            `Error na autenticação! status: ${authResponse.status}, message: ${errorText}`
          );
          throw new Error(
            `Error na autenticação! status: ${authResponse.status}, message: ${errorText}`
          );
        }

        console.log("Autenticação bem-sucedida!");
      } catch (error) {
        console.error("Erro ao autenticar na API:", error);
        setError(
          error instanceof Error ? error.message : "Error ao autenticar na API"
        );
      }
    };

    const fetchVehicles = async () => {
      try {
        const response = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Posicao",
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          const errorText = await response.text();
          console.error(
            `Error ao buscar dados da API! status: ${response.status}, message: ${errorText}`
          );
          throw new Error(
            `Error ao buscar dados da API! status: ${response.status}, message: ${errorText}`
          );
        }

        const data = await response.json();
        if (data.l) {
          const allVehiclePositions = data.l.flatMap(
            (vehicle: Vehicle) => vehicle.vs
          );
          console.log("Dados dos veículos recebidos");
          setVehicles(allVehiclePositions);
        } else {
          console.log("Nenhum dado de veículo encontrado.");
        }
      } catch (error) {
        console.error("Error ao buscar dados da API:", error);
        setError(
          error instanceof Error
            ? error.message
            : "Error ao buscar dados da API"
        );
      } finally {
        setLoading(false);
      }
    };

    const getLocationAndFetchVehicles = async () => {
      await authenticate();
      await fetchVehicles();
    };

    getLocationAndFetchVehicles();

    // Atualiza os veículos a cada 30 segundos
    const intervalId = setInterval(() => {
      getLocationAndFetchVehicles();
    }, 30000);

    return () => {
      clearInterval(intervalId);
    };
  }, []);

  useEffect(() => {
    // console.log("Veículos atualizados:", vehicles);
  }, [vehicles]);

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#0000ff" />
        <Text>Carregando...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.loadingContainer}>
        <Text>Erro: {error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <MapView
        ref={mapRef}
        style={styles.map}
        initialRegion={{
          latitude: -23.55052,
          longitude: -46.633308,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
        showsUserLocation={true}
        followsUserLocation={true}
      >
        {vehicles.map((vehicle, index) => (
          <Marker
            key={index}
            coordinate={{
              latitude: vehicle.py,
              longitude: vehicle.px,
            }}
            title={`Veículo ${vehicle.p}`}
            description={`Última atualização: ${vehicle.ta}`}
            pinColor="red"
          />
        ))}
      </MapView>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
  },
  map: {
    width: "100%",
    height: "100%",
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default VehicleMap;
