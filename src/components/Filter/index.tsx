import React, { useState } from "react";
import { View, Image, TouchableOpacity, Text, TextInput } from "react-native";
import { CheckBox } from "react-native-elements";
import { styles } from "@/components/Filter/styles";

export function Filter() {
  const [showFilters, setShowFilters] = useState(false);
  const [busesChecked, setBusesChecked] = useState(false);
  const [stationsChecked, setStationsChecked] = useState(false);
  const [linesChecked, setLinesChecked] = useState(false);
  const [filterText, setFilterText] = useState("");

  const toggleFilters = () => {
    setShowFilters(!showFilters);
  };

  return (
    <View>
      {showFilters && (
        <View style={styles.filterOptions}>
          <View style={styles.filterOption}>
            <CheckBox
              checked={busesChecked}
              onPress={() => setBusesChecked(!busesChecked)}
            />
            <Text>Buses</Text>
            {busesChecked && (
              <TextInput
                style={styles.textInput}
                placeholder="Digite o nome/localização"
                value={filterText}
                onChangeText={setFilterText}
              />
            )}
          </View>

          <View style={styles.filterOption}>
            <CheckBox
              checked={stationsChecked}
              onPress={() => setStationsChecked(!stationsChecked)}
            />
            <Text>Bus Stations</Text>
            {stationsChecked && (
              <TextInput
                style={styles.textInput}
                placeholder="Digite o nome/localização"
                value={filterText}
                onChangeText={setFilterText}
              />
            )}
          </View>

          <View style={styles.filterOption}>
            <CheckBox
              checked={linesChecked}
              onPress={() => setLinesChecked(!linesChecked)}
            />
            <Text>Lines</Text>
            {linesChecked && (
              <TextInput
                style={styles.textInput}
                placeholder="Digite a localização"
                value={filterText}
                onChangeText={setFilterText}
              />
            )}
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
