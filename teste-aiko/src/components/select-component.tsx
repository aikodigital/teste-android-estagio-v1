import React, { memo, useState } from "react"
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  FlatList,
  StyleSheet,
} from "react-native"
import { Linha } from "../types/types"

type SelectProps = {
  linhas: Linha[]
  linhaSelecionada: (e: number) => void
}

const SelectComponent = ({ linhas, linhaSelecionada }: SelectProps) => {
  const [selectedLinha, setSelectedLinha] = useState<number>(0)
  const [searchTerm, setSearchTerm] = useState("")
  const [dropdownVisible, setDropdownVisible] = useState(false)

  const handleLinhaChange = (linha: Linha) => {
    setSelectedLinha(linha.cl)
    linhaSelecionada(linha.cl)
    setDropdownVisible(false)
  }

  const filteredLinhas = linhas.filter((linha) =>
    linha.lt0.toLowerCase().includes(searchTerm.toLowerCase())
  )

  return (
    <View style={{ flex: 1 }}>
      <TouchableOpacity
        style={styles.dropdownButton}
        onPress={() => setDropdownVisible(!dropdownVisible)}
      >
        <Text style={styles.dropdownButtonText}>
          {selectedLinha
            ? linhas.find((linha) => linha.cl === selectedLinha)?.lt0
            : "Selecione uma linha"}
        </Text>
      </TouchableOpacity>

      {dropdownVisible && (
        <View style={styles.dropdown}>
          <TextInput
            style={styles.searchInput}
            placeholder="Pesquisar..."
            value={searchTerm}
            onChangeText={(text) => setSearchTerm(text)}
          />
          <FlatList
            data={filteredLinhas}
            keyExtractor={(item) => item.cl.toString()}
            renderItem={({ item }) => (
              <TouchableOpacity
                style={styles.dropdownItem}
                onPress={() => handleLinhaChange(item)}
              >
                <Text style={styles.dropdownItemText}>{item.lt0}</Text>
              </TouchableOpacity>
            )}
          />
        </View>
      )}
    </View>
  )
}

const styles = StyleSheet.create({
  dropdownButton: {
    width: "80%",
    height: 50,
    backgroundColor: "#ffffff",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: "#444",
    alignSelf: "center",
    justifyContent: "center",
    alignItems: "center",
    marginVertical: 20,
  },
  dropdownButtonText: {
    textAlign: "center",
    fontSize: 16,
    color: "red",
  },
  dropdown: {
    width: "80%",
    height: 200,
    backgroundColor: "#ffffff",
    borderRadius: 8,
    borderWidth: 1,
    borderColor: "#444",
    alignSelf: "center",
    marginBottom: 20,
    zIndex: 99,
  },
  searchInput: {
    padding: 10,
    borderBottomWidth: 1,
    borderColor: "#cccccc",
  },
  dropdownItem: {
    padding: 10,
  },
  dropdownItemText: {
    fontSize: 16,
  },
  centeredView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 22,
  },
  modalView: {
    margin: 20,
    backgroundColor: "white",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalText: {
    marginBottom: 15,
    textAlign: "center",
  },
  closeButton: {
    backgroundColor: "#2196F3",
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  closeButtonText: {
    color: "white",
    fontWeight: "bold",
    textAlign: "center",
  },
})

export default memo(SelectComponent) 
