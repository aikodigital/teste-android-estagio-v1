import React, { useState } from "react";
import {
  View,
  StyleSheet,
  Button,
  TextInput,
  Text,
  FlatList,
  TouchableOpacity,
} from "react-native";
import MapView, { Marker } from "react-native-maps";

const MyApp = () => {
  const urlBase = "https://aiko-olhovivo-proxy.aikodigital.io";
  const token =
    "498ea22d83ea7a6377baf07ad05fe979fab8c7d3f1a952da64431639b2d5208f";
  const [vehicles, setVehicles] = useState([]);
  const [line, setLine] = useState("");
  const [busPerLine, setBusPerLine] = useState([]);
  const [codigoParada, setCodigoParada] = useState("");
  const [loading, setLoading] = useState(false);
  const [forecastVehicles, setForecastVehicles] = useState([]);
  const [currentList, setCurrentList] = useState("");

  // Get the position of all buses
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

  // Get information about the line
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

      if (data && data.length > 0) {
        setBusPerLine(data);
        setCurrentList("busPerLine");
      } else {
        setBusPerLine([]);
      }
    } catch (error) {
      console.error("Erro ao buscar linha:", error);
    }
  };

  const fetchForecast = async () => {
    try {
      const response = await fetch(
        `${urlBase}/Previsao/Parada?codigoParada=${codigoParada}`,
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

      // Verifica se a estrutura esperada existe
      if (data && data.p && Array.isArray(data.p.l)) {
        // Acessa o array l
        const paradas = data.p.l;
        const vehiclesList = []; // Lista para armazenar veículos

        // Itera sobre as paradas para acessar o array vs
        paradas.forEach((parada) => {
          if (Array.isArray(parada.vs)) {
            // Itera sobre os veículos e acessa os campos p e t
            parada.vs.forEach((veiculo) => {
              vehiclesList.push(veiculo); // Armazena o veículo na lista
            });
          }
        });

        setForecastVehicles(vehiclesList);
        setCurrentList("forecastVehicles");
      } else {
        console.error("Estrutura de dados inesperada", data);
      }
    } catch (error) {
      console.error("Erro ao buscar previsão:", error);
    }
  };

  const renderVehicle = ({ item }) => (
    <View style={styles.vehicleItem}>
      <Text>Prefixo do veículo: {item.p}</Text>
      <Text> Horário previsto para chegada: {item.t}h</Text>
    </View>
  );

  const renderItem = ({ item }) => (
    <View style={styles.busItem}>
      <Text>CL: {item.cl}</Text>
      <Text>Linha: {item.lt}</Text>
      <Text>Sentido: {item.sl}</Text>
      <Text>Modos: {item.tl}</Text>
      <Text>Terminal Principal: {item.tp}</Text>
      <Text>Terminal Secundário: {item.ts}</Text>
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
          color={"#000"}
          title={loading ? "Carregando..." : "Obter localização dos ônibus"}
          onPress={fetchPosition}
          disabled={loading}
        />
        <Text>Consulte os ônibus disponíveis na linha:</Text>
        <View style={styles.line}>
          <TextInput
            style={styles.input}
            placeholder="Código da linha"
            value={line}
            onChangeText={setLine}
          />
          <TouchableOpacity onPress={fetchLine} style={styles.button}>
            <Text style={styles.textButton}>Consultar</Text>
          </TouchableOpacity>
        </View>
        <Text>Consulte a previsão de chegada dos ônibus:</Text>
        <View style={styles.line}>
          <TextInput
            style={styles.input}
            placeholder="Código da parada"
            value={codigoParada}
            onChangeText={setCodigoParada}
          />

          <TouchableOpacity onPress={fetchForecast} style={styles.button}>
            <Text style={styles.textButton}>Consultar</Text>
          </TouchableOpacity>
        </View>
        {currentList === "busPerLine" && (
          <FlatList
            data={busPerLine}
            renderItem={renderItem}
            keyExtractor={(item) => item.cl.toString()}
          />
        )}
         {currentList === "forecastVehicles" && (
            <FlatList
              data={forecastVehicles}
              renderItem={renderVehicle}
              keyExtractor={(item) => item.p.toString()}
            />
          )}
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
    flex: 2,
    padding: 16,
    gap: 10,
  },
  line: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-between",
  },
  input: {
    width: "60%",
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    paddingHorizontal: 8,
    borderRadius: 10,
  },
  button: {
    width: "30%",
    justifyContent: "center",
    backgroundColor: "#f9d797",
    padding: 10,
    borderRadius: 15,
  },
  textButton: {
    justifyContent: "center",
    textAlign: "center",
    fontSize: 15,
  },
  busItem: {
    marginBottom: 10,
  },
  vehicleItem: {
    marginBottom: 10,
  },
});

export default MyApp;
