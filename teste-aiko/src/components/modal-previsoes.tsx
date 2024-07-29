import React, { useEffect, useState } from 'react';
import { Modal, ScrollView, StyleSheet, Text, View } from 'react-native';
import { getPrevisaoChegada } from '../services/api-service';
import { Parada, PrevisaoParadaResponse } from '../types/types';
import { Button } from './Button';

type ModalProps = {
  isVisible?: boolean;
  onClose?: () => void;
  parada: Parada | null;
};

const ModalPrevisoes = ({ isVisible, onClose, parada }: ModalProps) => {
  const [previsao, setPrevisao] = useState<PrevisaoParadaResponse | null>(null);

  useEffect(() => {
    const fetchPrevisao = async () => {
      if (parada) {
        try {
          const data = await getPrevisaoChegada(parada.cp);
          setPrevisao(data);
        } catch (err) {}
      }
    };
    fetchPrevisao();
  }, [parada]);

  return (
    <Modal
      animationType='slide'
      transparent
      visible={isVisible}
      onRequestClose={onClose}
    >
      <View style={styles.centeredView}>
        <View style={styles.modalView}>
          <Text style={styles.title}>Previsão de Chegada dos Veículos</Text>

          <ScrollView contentContainerStyle={styles.scrollViewContent}>
            {previsao?.p.l?.map((linha, index) => (
              <View key={index} style={styles.lineContainer}>
                <Text style={styles.lineTitle}>
                  Linha: {linha.lt0} ({linha.c})
                </Text>
                {linha.vs?.map((veiculo, idx) => (
                  <Text key={idx} style={styles.vehicleText}>
                    Veículo: {veiculo.p}, Horário: {veiculo.t}h
                  </Text>
                ))}
              </View>
            )) || (
              <Text style={styles.noDataText}>
                Nenhuma previsão disponível.
              </Text>
            )}
          </ScrollView>

          <View style={styles.buttonGroup}>
            <Button title='Fechar' onPress={onClose} />
          </View>
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
  },
  modalView: {
    width: '80%',
    height: '80%',
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 20,
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 10,
    color: 'red',
  },
  scrollViewContent: {
    flexGrow: 1,
    paddingBottom: 20,
  },
  lineContainer: {
    marginBottom: 20,
  },
  lineTitle: {
    fontWeight: 'bold',
    fontSize: 16,
    marginBottom: 5,
  },
  vehicleText: {
    fontSize: 14,
    marginBottom: 2,
  },
  buttonGroup: {
    marginTop: 10,
  },
  noDataText: {
    textAlign: 'center',
    color: 'gray',
  },
});

export default ModalPrevisoes;
