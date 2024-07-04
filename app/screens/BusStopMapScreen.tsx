// screens/BusStopsScreen.tsx
import React, { useEffect, useState } from "react";
import { StyleSheet, View, Text, ActivityIndicator } from "react-native";
import MapView, { Marker } from "react-native-maps";

interface BusStop {
  cp: string;
  np: string;
  ed: string;
  py: number;
  px: number;
}

const BusStopsScreen: React.FC = () => {
  const [busStops, setBusStops] = useState<BusStop[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token =
      "6139d58e7b6e874fc32994d53dfb25b453a27a07a900b2047fb093c622ce4ee3";

    const fetchBusStops = async () => {
      try {
        const authResponse = await fetch(
          `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${token}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (!authResponse.ok) {
          const errorText = await authResponse.text();
          throw new Error(
            `Erro na autenticação! status: ${authResponse.status}, message: ${errorText}`
          );
        }

        // Adicionando token aos headers da requisição
        const response = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha=1",
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        const data = await response.json();

        // Log para verificar a estrutura dos dados recebidos
        console.log("Dados recebidos:", data);

        // Verificação de dados válidos
        const validBusStops = data.filter((busStop: BusStop) => {
          const latitude = parseFloat(busStop.py as unknown as string);
          const longitude = parseFloat(busStop.px as unknown as string);
          if (isNaN(latitude) || isNaN(longitude)) {
            console.warn(
              `Parada inválida ${busStop.np}: (${latitude}, ${longitude})`
            );
            return false;
          }
          return true;
        });

        setBusStops(validBusStops);
      } catch (err: unknown) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError("An unknown error occurred");
        }
        console.error("Erro ao buscar paradas:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchBusStops();
  }, []);

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
        <Text>Error: {error}</Text>
      </View>
    );
  }

  return (
    <MapView
      style={styles.map}
      initialRegion={{
        latitude: -23.55052,
        longitude: -46.633308,
        latitudeDelta: 0.1,
        longitudeDelta: 0.1,
      }}
    >
      {busStops.map((busStop) => {
        const latitude = parseFloat(busStop.py as unknown as string);
        const longitude = parseFloat(busStop.px as unknown as string);

        console.log(`Marker ${busStop.np}: (${latitude}, ${longitude})`);

        if (isNaN(latitude) || isNaN(longitude)) {
          return (
            <Marker
              key={busStop.cp}
              coordinate={{ latitude: 0, longitude: 0 }} // Coordenadas de fallback
              title="Invalid Location"
              description={`Parada ${busStop.np} possui coordenadas inválidas`}
            />
          );
        }

        return (
          <Marker
            key={busStop.cp}
            coordinate={{ latitude, longitude }}
            title={busStop.np}
            description={busStop.ed}
            pinColor="red"
          />
        );
      })}
    </MapView>
  );
};

const styles = StyleSheet.create({
  map: {
    flex: 1,
  },
  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default BusStopsScreen;
