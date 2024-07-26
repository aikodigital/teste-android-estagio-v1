import React, { useState } from "react";
import {
  View,
  StyleSheet,
  Button,
  TextInput,
  ActivityIndicator,
  Text,
  FlatList,
} from "react-native";
import MapView, { Marker } from "react-native-maps";

const MyApp = () => {
  const urlBase = "https://aiko-olhovivo-proxy.aikodigital.io";
  const token =
    "498ea22d83ea7a6377baf07ad05fe979fab8c7d3f1a952da64431639b2d5208f";
  const [vehicles, setVehicles] = useState([]);
  const [line, setLine] = useState("");
  const [busPerLine, setBusPerLine] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchPosition = async () => {
    setLoading(true);
    try {
      const response = await fetch(`${urlBase}/Posicao`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      const extractedVehicles = Array.isArray(data.l)
        ? data.l.flatMap((item) =>
            Array.isArray(item.vs)
              ? item.vs.map((vehicle) => ({
                  py: vehicle.py,
                  px: vehicle.px,
                }))
              : []
          )
        : [];

      setVehicles((prevVehicles) => {
        if (
          JSON.stringify(prevVehicles) !== JSON.stringify(extractedVehicles)
        ) {
          return extractedVehicles;
        }
        return prevVehicles;
      });
    } catch (error) {
      console.error("Erro ao buscar localizações:", error);
    } finally {
      setLoading(false);
    }
  };

  const fetchLine = async () => {
    try {
      const response = await fetch(
        `${urlBase}/Linha/Buscar?termosBusca=${line}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();

      // Verificar se há dados antes de atualizar o estado e logar
      if (data && data.length > 0) {
        setBusPerLine(data);
        console.log(data); // Mostrar dados retornados
      } else {
        console.log("Nenhum dado retornado.");
        setBusPerLine([]); // Limpar o estado se não houver dados
      }
    } catch (error) {
      console.error("Erro ao buscar linha:", error);
    }
  };

  const renderItem = ({ item }) => (
    <View style={styles.busItem}>
      <Text>CL: {item.cl}</Text>
      <Text>LT: {item.lt}</Text>
      <Text>SL: {item.sl}</Text>
      <Text>TL: {item.tl}</Text>
      <Text>TP: {item.tp}</Text>
      <Text>TS: {item.ts}</Text>
    </View>
  );

  return (
    <>
      <View style={styles.container}>
        <MapView
          style={styles.map}
          initialRegion={{
            latitude: -23.55052,
            longitude: -46.633308,
            latitudeDelta: 0.0922,
            longitudeDelta: 0.0421,
          }}
        >
          {vehicles.map((vehicle, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: vehicle.py,
                longitude: vehicle.px,
              }}
            />
          ))}
        </MapView>
      </View>
      <View style={styles.data}>
        <Button
          title={loading ? "Carregando..." : "Obter localização dos ônibus"}
          onPress={fetchPosition}
          disabled={loading}
        />
        <View style={styles.busPerLine}>
          <TextInput
            style={styles.input}
            placeholder="Digite o número da linha"
            value={line}
            onChangeText={setLine}
          />
          <Button
            title={"Buscar linha"}
            onPress={fetchLine}
            disabled={loading}
          />
          <FlatList
            data={busPerLine}
            renderItem={renderItem}
            keyExtractor={(item) => item.cl.toString()}
          />
        </View>
      </View>
    </>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  map: {
    ...StyleSheet.absoluteFillObject,
  },
  data: {
    flex: 1,
    padding: 16,
    gap: 10,
  },
  busPerLine: {},
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 8,
  },
  busItem: {
    marginBottom: 10,
  },
});

export default MyApp;
