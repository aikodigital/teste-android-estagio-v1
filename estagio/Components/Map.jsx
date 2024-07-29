import React, { useContext, useEffect, useState } from 'react';
import { View, StyleSheet, Text, TouchableOpacity, Modal, ScrollView } from 'react-native';
import MapView, { Marker, Callout, PROVIDER_GOOGLE } from 'react-native-maps';
import { AppContext } from './../appContext';
import axios from 'axios';

const API_BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1";
const API_KEY = "c8927e9a1d4eb1cb2329fbfd9ca9740bf80fa72e0d28f06cb73e47045841270b";

const fetchPrevisaoTempo = async (codigoParada) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/Previsao/Parada?codigoParada=${codigoParada}`, {
      headers: {
        'Authorization': `Bearer ${API_KEY}`,
        'Content-Type': 'application/json',
      },
    });
    
    const horarioRealizadoReq = response.data.hr;
    const primeiraCamada = response.data.p;

    const onibusParada = primeiraCamada.l;

    const organizedData = {};

    onibusParada.forEach(item => {
      const letreiro = item.c;
      const onibusLinha = item.vs;

      organizedData[letreiro] = organizedData[letreiro] || [];

      onibusLinha.forEach(veiculo => {
        organizedData[letreiro].push({
          p: veiculo.p,
          t: veiculo.t,
        });
      });
    });

    return { horarioRealizadoReq, organizedData };

  } catch (error) {
    console.error('Erro ao buscar previsão:', error);
    throw error;
  }
};

const RouteCalculation = () => {
  const { vehicles, paradas } = useContext(AppContext);
  const [initialRegion, setInitialRegion] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedParada, setSelectedParada] = useState(null);


  const handlePressParada = async (parada) => {
    try {
      const previsaoData = await fetchPrevisaoTempo(parada.cp);
      setSelectedParada({ ...parada, ...previsaoData });
      setModalVisible(true);
    } catch (error) {
      console.error('Erro ao buscar previsão:', error);
    }
  };


  const vehicleMarkers = vehicles.map((position, index) => (
    <Marker
      key={`vehicle-${index}`}
      coordinate={{
        latitude: position.latitude,
        longitude: position.longitude,
      }}
   //image={require('./../icons/bus.png')}
    >
      <Callout>
        <View style={styles.infoBox}>
          <Text style={styles.infoText}>Veículo ID: {position.id}</Text>
          <Text style={styles.infoText}>Última atualização: {position.timestamp}</Text>
          <Text style={styles.infoText}>Destino: {position.destino}</Text>
          <Text style={styles.infoText}>Origem: {position.origem}</Text>
          <Text style={styles.infoText}>Linha: {position.veicLinha}</Text>
        </View>
      </Callout>
    </Marker>
  ));

  const paradaMarkers = paradas.map((parada, index) => (
    <Marker
      key={`parada-${index}`}
      coordinate={{
        latitude: parada.py,
        longitude: parada.px,
      }}
      pinColor="blue"
    >
      <Callout onPress={() => handlePressParada(parada)}>
        <View style={styles.infoBox}>

          <Text style={styles.infoText}>Pressione para mais informações</Text>
          <Text style={styles.infoText}>Parada: {parada.np}</Text>
          <Text style={styles.infoText}>Código: {parada.cp}</Text>
          <Text style={styles.infoText}>Endereço: {parada.ed}</Text>
        </View>
      </Callout>
    </Marker>
  ));

 

  return (
    <View style={styles.container}>
      <MapView
      
        style={styles.map}
        initialRegion={initialRegion}
      >
        {vehicleMarkers}
        {paradaMarkers}
      </MapView>

      <Modal
        transparent={true}
        visible={modalVisible}
        animationType="slide"
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalOverlay}>
          <View style={styles.modalContent}>
            {selectedParada && (
              <ScrollView>
                <Text style={styles.modalTitle}>Detalhes da Parada</Text>
                <Text style={styles.modalText}>Parada: {selectedParada.np}</Text>
                <Text style={styles.modalText}>Código: {selectedParada.cp}</Text>
                <Text style={styles.modalText}>Endereço: {selectedParada.ed}</Text>
                <Text style={styles.modalText}>Previsão realizada em {selectedParada.horarioRealizadoReq}:</Text>
                {Object.keys(selectedParada.organizedData).map((letreiro, index) => (
                  <View key={index}>
                    <Text style={styles.modalText}>Linha: {letreiro}</Text>
                    {selectedParada.organizedData[letreiro].map((onibus, idx) => (
                      <Text key={idx} style={styles.modalText}>
                        Veículo: {onibus.p}, Horário de chegada: {onibus.t}
                      </Text>
                    ))}
                  </View>
                ))}
              </ScrollView>
            )}
            <TouchableOpacity
              style={styles.closeButton}
              onPress={() => setModalVisible(false)}
            >
              <Text style={styles.closeButtonText}>Fechar</Text>
            </TouchableOpacity>
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  map: {
    flex:1
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  infoBox: {
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.8,
    shadowRadius: 2,
    elevation: 5,
  },
  infoText: {
    marginBottom: 5,
    fontSize: 14,
    color: '#333',
  },
  modalOverlay: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    width: '90%', 
    maxHeight: '60%', 
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    elevation: 10,
  },
  modalTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  modalText: {
    fontSize: 16,
    marginBottom: 5,
  },
  closeButton: {
    marginTop: 20,
    padding: 10,
    backgroundColor: 'blue',
    borderRadius: 5,
    alignItems: 'center',
  },
  closeButtonText: {
    color: 'white',
    fontSize: 16,
  },
});

export default RouteCalculation;
