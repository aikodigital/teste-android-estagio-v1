import React, { useState } from "react";
import { View, Button, Text, TextInput, FlatList, Alert } from "react-native";
import { fetchBusLines, fetchArrivalForecast } from "../api/api";
import styles from "../utils/busLinesStyles";

const BusLinesScreen = ({ route }) => {
  const { token } = route.params;
  const [busLines, setBusLines] = useState([]);
  const [arrivalForecasts, setArrivalForecasts] = useState({});
  const [lineCode, setLineCode] = useState("");

  const loadBusLines = async () => {
    try {
      const busLinesData = await fetchBusLines(token, lineCode);
      console.log("Bus lines data:", busLinesData);

      if (busLinesData && busLinesData.length > 0) {
        setBusLines(busLinesData);

        const forecasts = {};
        for (const line of busLinesData) {
          const arrivalData = await fetchArrivalForecast(token, line.cl);
          console.log(
            `Arrival forecast data for line ${line.cl}:`,
            arrivalData
          );

          if (arrivalData && arrivalData.ps) {
            forecasts[line.cl] = arrivalData.ps;
          } else {
            Alert.alert(
              "No Data",
              `No arrival forecast data available for line ${line.cl}`
            );
          }
        }
        setArrivalForecasts(forecasts);
      } else {
        Alert.alert("No Data", "No bus lines data available");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Failed to fetch data");
    }
  };

  const renderForecasts = (lineCode) => {
    const forecasts = arrivalForecasts[lineCode];
    if (!forecasts) {
      return <Text>No forecast available</Text>;
    }

    return forecasts.map((forecast) => (
      <View key={forecast.cp} style={styles.forecastItem}>
        <Text>Stop: {forecast.np}</Text>
        <Text>
          Next arrival:{" "}
          {forecast.vs.length > 0 ? forecast.vs[0].t : "No forecast"}
        </Text>
      </View>
    ));
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Enter bus line code --> ex: 8000, 42"
        value={lineCode}
        onChangeText={setLineCode}
      />
      <Button
        title="Fetch Bus Lines and Arrival Forecasts"
        onPress={loadBusLines}
      />
      <FlatList
        data={busLines}
        keyExtractor={(item) => item.cl.toString()}
        renderItem={({ item }) => (
          <View style={styles.item}>
            <Text>Line Code: {item.cl}</Text>
            <Text>Term: {item.ts}</Text>
            <Text>Other term: {item.tp}</Text>
            {renderForecasts(item.cl)}
          </View>
        )}
      />
    </View>
  );
};

export default BusLinesScreen;
