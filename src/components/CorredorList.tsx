import React from "react";
import { FlatList, TouchableOpacity, Text, StyleSheet } from "react-native";
import { Corredor } from "../types/types";
import { theme } from "../theme";

interface Props {
  corredores: Corredor[];
  handleSearchCorredor: (inputValue: string) => void;
  setInputValue: React.Dispatch<React.SetStateAction<string>>;
}

const CorredorList: React.FC<Props> = ({
  corredores,
  handleSearchCorredor,
  setInputValue,
}) => {
  return (
    <FlatList
      data={corredores}
      keyExtractor={(item) => item.cc.toString()}
      renderItem={({ item }) => (
        <TouchableOpacity
          style={styles.corredorItem}
          onPress={() => {
            const cc = item.cc.toString();
            setInputValue(cc);
            handleSearchCorredor(cc);
          }}
        >
          <Text style={styles.corredorText}>
            {item.cc} - {item.nc}
          </Text>
        </TouchableOpacity>
      )}
    />
  );
};

const styles = StyleSheet.create({
  corredorText: {
    fontSize: 16,
    fontFamily: theme.fontFamily.bold,
  },
  corredorItem: {
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderBottomWidth: 1,
    borderBottomColor: theme.colors.gray[800],
  },
});

export default CorredorList;
