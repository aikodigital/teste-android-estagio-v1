/* eslint-disable no-unused-expressions */
import React, { useState } from 'react';
import { Modal, StyleSheet, Text, View } from 'react-native';

import { Linha } from '../types/types';
import SelectComponent from './select-component';
import { Button } from './Button';
type ModalProps = {
  isVisible?: boolean;
  onClose?: () => void;
  linhas: Linha[];

  linhaFiltrada: (e: number) => void;
};
const ModalFilter = ({
  isVisible,
  onClose,
  linhas,
  linhaFiltrada,
}: ModalProps) => {
  const [selectedLinha, setSelectedLinha] = useState<number>(0);

  const handleSelected = (value: number) => {
    setSelectedLinha(value);
  };

  function handleFiltered() {
    onClose;
    linhaFiltrada(selectedLinha);
    setSelectedLinha(0);
  }

  return (
    <Modal
      animationType='slide'
      transparent
      visible={isVisible}
      onRequestClose={onClose}
    >
      <View style={styles.centeredView}>
        <View style={styles.modalView}>
          <Text style={styles.title}>Selecione uma linha de onibus</Text>
          <SelectComponent linhas={linhas} linhaSelecionada={handleSelected} />
          <View style={styles.buttonGroup}>
            <Button title='Filtrar' onPress={handleFiltered} />
            <Button title='Fechar' onPress={onClose} />
          </View>
        </View>
      </View>
    </Modal>
  );
};
const styles = StyleSheet.create({
  container: {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'red',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  buttonGroup: {
    display: 'flex',
    flexDirection: 'column',
    gap: 5,
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalView: {
    width: '80%',
    height: 500,
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  picker: {
    height: 50,
    width: 250,
    alignSelf: 'center',
    marginVertical: 20,
  },
});
export default ModalFilter;
