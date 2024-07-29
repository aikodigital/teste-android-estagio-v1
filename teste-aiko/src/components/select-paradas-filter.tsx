import React, { memo, useState } from 'react';
import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  FlatList,
  StyleSheet,
} from 'react-native';
import { Parada } from '../types/types';

type SelectProps = {
  paradas: Parada[];
  ParadaSelecionada: (e: Parada) => void;
};

const SelectParadas = ({ paradas, ParadaSelecionada }: SelectProps) => {
  const [selectedParada, setSelectedParada] = useState<number>(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [dropdownVisible, setDropdownVisible] = useState(false);

  const handleParadaChange = (Parada: Parada) => {
    setSelectedParada(Parada.cp);
    ParadaSelecionada(Parada);
    setDropdownVisible(false);
  };

  const filteredParadas = paradas.filter((Parada) =>
    Parada.np.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <View style={{ flex: 1 }}>
      <TouchableOpacity
        style={styles.dropdownButton}
        onPress={() => setDropdownVisible(!dropdownVisible)}
      >
        <Text style={styles.dropdownButtonText}>
          {selectedParada
            ? paradas.find((Parada) => Parada.cp === selectedParada)?.np
            : 'Selecione uma Parada'}
        </Text>
      </TouchableOpacity>

      {dropdownVisible && (
        <View style={styles.dropdown}>
          <TextInput
            style={styles.searchInput}
            placeholder='Pesquisar...'
            value={searchTerm}
            onChangeText={(text) => setSearchTerm(text)}
          />
          <FlatList
            data={filteredParadas}
            keyExtractor={(item) => item.cp.toString()}
            renderItem={({ item }) => (
              <TouchableOpacity
                style={styles.dropdownItem}
                onPress={() => handleParadaChange(item)}
              >
                <Text style={styles.dropdownItemText}>
                  {item.np.length > 0 ? item.np : item.ed}
                </Text>
              </TouchableOpacity>
            )}
          />
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  dropdownButton: {
    width: '80%',
    height: 50,
    backgroundColor: '#ffffff',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#444',
    alignSelf: 'center',
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: 20,
  },
  dropdownButtonText: {
    textAlign: 'center',
    fontSize: 16,
    color: 'red',
  },
  dropdown: {
    width: '80%',
    height: 300,
    backgroundColor: '#ffffff',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#444',
    alignSelf: 'center',
    marginBottom: 20,
    zIndex: 99,
  },
  searchInput: {
    padding: 10,
    borderBottomWidth: 1,
    borderColor: '#cccccc',
  },
  dropdownItem: {
    padding: 10,
  },
  dropdownItemText: {
    fontSize: 16,
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
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
    textAlign: 'center',
  },
  closeButton: {
    backgroundColor: '#2196F3',
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  closeButtonText: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
});

export default memo(SelectParadas);
