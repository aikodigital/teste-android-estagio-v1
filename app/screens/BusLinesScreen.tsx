import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  ActivityIndicator,
  SafeAreaView,
} from "react-native";
import MapView, { Marker } from "react-native-maps";

interface BusLine {
  cl: number;
  lt: string;
  sl: number;
  tl: number;
  tp: string;
  ts: string;
}

const BusLines = () => {
  const [busLines, setBusLines] = useState<BusLine[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const token =
      "6139d58e7b6e874fc32994d53dfb25b453a27a07a900b2047fb093c622ce4ee3";

    const fetchBusLines = async () => {
      try {
        // Primeira chamada para autenticação
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
          throw new Error(
            `Erro na autenticação! status: ${authResponse.status}, message: ${errorText}`
          );
        }

        // Segunda chamada para buscar as linhas de ônibus
        const response = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Linha/BuscarLinhaSentido?termosBusca=800&sentido=1",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              // A API da Olho Vivo SPTRANS usa cookies para autenticação após a primeira chamada
            },
          }
        );

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(
            `HTTP error! status: ${response.status}, message: ${errorText}`
          );
        }

        const json: BusLine[] = await response.json();
        setBusLines(json);
      } catch (error) {
        console.error("Fetch Error:", error);
        setError(
          error instanceof Error
            ? error.message
            : "Ocorreu um erro desconhecido"
        );
      } finally {
        setLoading(false);
      }
    };

    fetchBusLines();
  }, []); // Dependência vazia, executa apenas uma vez após a montagem inicial do componente

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#0000ff" />
        <Text>Carregando...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text>Error: {error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={busLines}
        keyExtractor={(item, index) => `${item.cl}-${index}`}
        renderItem={({ item }) => (
          <SafeAreaView style={styles.item}>
            <View style={styles.title}>
              <Text>{item.lt}</Text>
              <Text>
                {item.tp} {item.ts}
              </Text>
              <Text>
                {item.cl} - {item.lt} - {item.tl}
              </Text>
            </View>
          </SafeAreaView>
        )}
      />
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
  item: {
    backgroundColor: "#14e039",
    padding: 20,
    marginVertical: 8,
    marginHorizontal: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: "bold",
    justifyContent: "center",
    alignItems: "center",
  },
});

export default BusLines;
