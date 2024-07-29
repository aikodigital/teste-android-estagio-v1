import React, { useContext, useState } from "react";
import { View, Image, TouchableOpacity, Text, TextInput } from "react-native";
import { CheckBox } from "react-native-elements";
import { styles } from "@/components/Filter/styles";
import { MapContext } from "../../contexts/MapContext";

export function Filter() {
  const context = useContext(MapContext);

  // Verifica se o contexto está definido
  if (!context) {
    throw new Error("Filter must be used within a MapProvider");
  }

  const { showBuses, setShowBuses, showBusStations, setShowBusStations } =
    context;

  const [showFilters, setShowFilters] = useState(false);
  const [busesChecked, setBusesChecked] = useState(showBuses);
  const [stationsChecked, setStationsChecked] = useState(showBusStations);
  const [linesChecked, setLinesChecked] = useState(false);
  const [filterText, setFilterText] = useState("");

  const toggleFilters = () => {
    setShowFilters(!showFilters);
  };

  const handleBusesCheck = () => {
    setBusesChecked(!busesChecked);
    setShowBuses(!busesChecked);
  };

  const handleStationsCheck = () => {
    setStationsChecked(!stationsChecked);
    setShowBusStations(!stationsChecked);
  };

  return (
    <View>
      {showFilters && (
        <View style={styles.filterOptions}>
          <View style={styles.filterOption}>
            <CheckBox checked={busesChecked} onPress={handleBusesCheck} />
            <Text>Visualizar Ônibus</Text>
          </View>

          <View style={styles.filterOption}>
            <CheckBox checked={stationsChecked} onPress={handleStationsCheck} />
            <Text>Visualizar Paradas de Ônibus</Text>
          </View>
        </View>
      )}
      <TouchableOpacity style={styles.button} onPress={toggleFilters}>
        <Text style={styles.text}>
          Filter{"   "}
          <Image
            source={require("@/assets/lupa.png")}
            style={styles.buttonImage}
          />
        </Text>
      </TouchableOpacity>
      <Image style={styles.margin} />
    </View>
  );
}
