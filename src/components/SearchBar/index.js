import React from "react";
import { View, TextInput } from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";
import { styles } from "./styles";

const SearchBar = ({ onHandleInput }) => {
  function onInputChange(text) {
    onHandleInput(text);
  }

  return (
    <View style={styles.searchContainer}>
      <TextInput
        style={styles.searchInput}
        placeholder="Pesquisar..."
        onChangeText={(text) => onInputChange(text)}
      />
      <Icon style={styles.icon} name="search" size={22} color="#696969" />
    </View>
  );
};



export default SearchBar;
