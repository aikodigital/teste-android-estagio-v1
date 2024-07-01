import React from "react";
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  FlatList,
  Keyboard,
} from "react-native";
import { theme } from "../theme";
import { Corredor } from "../types/types";

interface Props {
  selectedOption: "linha" | "corredor" | "empresa";
  inputValue: string;
  setInputValue: React.Dispatch<React.SetStateAction<string>>;
  handleSearchLinha: (inputValue: string) => void;
  handleSearchCorredor: (inputValue: string) => void;
  corredores: Corredor[];
}

const SearchInput: React.FC<Props> = ({
  selectedOption,
  inputValue,
  setInputValue,
  handleSearchLinha,
  handleSearchCorredor,
  corredores,
}) => {
  const handleKeyPress = (key: string) => {
    if (key === "Enter") {
      switch (selectedOption) {
        case "linha":
          handleSearchLinha(inputValue);
          break;
        case "corredor":
          handleSearchCorredor(inputValue);
          break;
        default:
          break;
      }
      Keyboard.dismiss();
    }
  };

  return (
    <View style={styles.inputContainer}>
      <Text style={styles.inputLabel}>
        {selectedOption === "linha"
          ? "Buscar Linha 🚌:"
          : selectedOption === "corredor"
            ? "Selecione o Corredor 🛣️:"
            : "Selecione a Empresa 🏢:"}
      </Text>

      {selectedOption === "linha" && (
        <>
          <TextInput
            style={styles.input}
            placeholder={`Digite o ${selectedOption}`}
            value={inputValue}
            onChangeText={(text) => setInputValue(text)}
            onSubmitEditing={() => handleKeyPress("Enter")}
            blurOnSubmit={true}
          />
          <Text style={styles.inputDescription}>
            {selectedOption === "linha" && "Exemplo: 8000, Lapa ou Ramos"}
          </Text>
          <TouchableOpacity
            style={styles.button}
            onPress={() => handleSearchLinha(inputValue)}
          >
            <Text style={styles.buttonText}>Buscar</Text>
          </TouchableOpacity>
        </>
      )}

      {selectedOption === "corredor" && (
        <FlatList
          data={corredores}
          keyExtractor={(item) => item.cc.toString()}
          renderItem={({ item }) => (
            <TouchableOpacity
              style={styles.corredorItem}
              onPress={() => {
                const cc = item.cc.toString();
                setInputValue(cc);
                handleSearchCorredor(item.nc);
              }}
            >
              <Text style={styles.corredorText}>
                {item.cc} - {item.nc}
              </Text>
            </TouchableOpacity>
          )}
        />
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  inputContainer: {
    marginBottom: 20,
    width: "100%",
  },
  inputLabel: {
    fontSize: 16,
    marginBottom: 5,
    fontFamily: theme.fontFamily.bold,
  },
  input: {
    borderWidth: 1,
    borderColor: theme.colors.gray[800],
    borderRadius: 5,
    height: 40,
    paddingHorizontal: 10,
    marginBottom: 5,
  },
  inputDescription: {
    fontSize: 14,
    color: theme.colors.gray[600],
    lineHeight: 20,
  },
  button: {
    backgroundColor: theme.colors.light_green,
    borderRadius: 5,
    paddingVertical: 10,
    paddingHorizontal: 20,
    alignItems: "center",
    marginTop: 30,
  },
  buttonText: {
    color: theme.colors.white,
    fontSize: 16,
    fontFamily: theme.fontFamily.bold,
  },
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
  empresaItem: {
    paddingVertical: 15,
    paddingHorizontal: 25,
    marginVertical: 10,
    marginHorizontal: 15,
    backgroundColor: theme.colors.gray[600],
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.8,
    shadowRadius: 2,
    elevation: 5,
  },
  empresaText: {
    fontFamily: theme.fontFamily.bold,
    color: theme.colors.white,
  },
});

export default SearchInput;
