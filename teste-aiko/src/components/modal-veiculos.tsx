import React, { memo, useState } from 'react';
import PropTypes from 'prop-types';
import { Modal, StyleSheet, Text, View } from 'react-native';
import { Button } from './Button';
import { Veiculo } from '../types/types';

type Props = {
  isVisible: boolean;
  onClose: () => void;
  veiculo: Veiculo | null;
  linha?: number;
};

const ModalVeiculos = ({ isVisible, onClose, veiculo, linha }: Props) => {
  return (
    <Modal
      animationType='slide'
      transparent
      visible={isVisible}
      onRequestClose={onClose}
    >
      <View style={styles.centeredView}>
        <View style={styles.modalView}>
          {veiculo && (
            <>
              <Text style={styles.modalTitle}>Informações do Veículo</Text>
              <View style={styles.infoContainer}>
                <Text style={styles.modalText}>
                  <Text style={styles.infoLabel}>Veículo: </Text>
                  {veiculo.p}
                </Text>
                <Text style={styles.modalText}>
                  <Text style={styles.infoLabel}>Linha: </Text>
                  {linha}
                </Text>
                <Text style={styles.modalText}>
                  <Text style={styles.infoLabel}>Última atualização: </Text>
                  {new Date(veiculo.ta).toLocaleString('pt-br')}
                </Text>
                <Text style={styles.modalText}>
                  <Text style={styles.infoLabel}>
                    Veículo acessível a pessoa com deficiência?{' '}
                  </Text>
                  {veiculo.a ? 'Sim' : 'Não'}
                </Text>
              </View>
              <Button title='Fechar' onPress={onClose} />
            </>
          )}
        </View>
      </View>
    </Modal>
  );
};
const styles = StyleSheet.create({
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
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  infoContainer: {
    marginBottom: 20,
  },
  modalText: {
    fontSize: 16,
    marginBottom: 10,
  },
  infoLabel: {
    fontWeight: 'bold',
  },
});

export default memo(ModalVeiculos);
