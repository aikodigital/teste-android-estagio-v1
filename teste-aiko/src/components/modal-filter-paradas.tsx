/* eslint-disable no-unused-expressions */
import React, { useState } from 'react';
import { Modal, StyleSheet, Text, View } from 'react-native';

import { Parada } from '../types/types';

import SelectParadas from './select-paradas-filter';
import { Button } from './Button';
import ModalParadas from './modal-previsoes';
type ModalProps = {
  isVisible?: boolean;
  onClose?: () => void;
  paradas: Parada[];
};
const ModalFilterParadas = ({ isVisible, onClose, paradas }: ModalProps) => {
  const [selectedParada, setSelectedParada] = useState<Parada | null>(null);
  const [openModalParadas, setOpenModalParadas] = useState(false);

  const handleSelected = (value: Parada) => {
    setSelectedParada(value);
  };

  function handleFiltered() {
    setOpenModalParadas(true);
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
          <Text style={styles.title}>Selecione uma parada de onibus</Text>
          <SelectParadas paradas={paradas} ParadaSelecionada={handleSelected} />
          <View style={styles.buttonGroup}>
            <Button title='Ver Previsão de chegada' onPress={handleFiltered} />
            <Button title='Fechar' onPress={onClose} />
          </View>
        </View>
      </View>
      <ModalParadas isVisible={openModalParadas} parada={selectedParada} onClose={()=>setOpenModalParadas(false)}/>
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
    height: 400,
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
export default ModalFilterParadas;
