/* eslint-disable prettier/prettier */
import React, { useEffect, useState } from 'react';
import { Modal, StyleSheet, Text, View } from 'react-native';
import MapView from 'react-native-maps';

import { Button } from '../components/Button';
import { Filters } from '../components/filters';
import ModalFilter from '../components/modalFilter';
import { getPosicoes, getAllParadas } from '../services/api-service';
import { Linha, Parada, Veiculo } from '../types/types';
import { InforsModal } from '../components/infors-modal';
import ModalVeiculos from '../components/modal-veiculos';
import ModalParadas from '../components/modal-previsoes';
import ParadaMarker from '../components/parada-marker';
import VeiculoMarker from '../components/veiculo-marker';
import ModalFilterParadas from '../components/modal-filter-paradas';

export default function Map() {
  const [linhas, setlinhas] = useState<Linha[]>([]);
  const [paradas, setParadas] = useState<Parada[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedVeiculo, setSelectedVeiculo] = useState<Veiculo | null>(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalFilterVisible, setModalFilterVisible] = useState(false);
  const [modalFilterParadas, setModalFilterParadas] = useState(false);
  const [modalParadaVisible, setModalParadaVisible] = useState(false);
  const [modalPrevisao, setModalPrevisao] = useState(false);
  const [linhaFiltrada, setLinhaFiltrada] = useState<number>(0);

  const [selectedParada, setSelectedParada] = useState<Parada | null>(null);
  const [selectedLinha, setSelectedLinha] = useState<Linha | null>(null);

  const filteredLinhas =
    linhaFiltrada > 0
      ? linhas.filter((linha) => linha.cl === linhaFiltrada)
      : linhas;

  const handleMarkerPress = React.useCallback(
    (veiculo: Veiculo, linha: Linha) => {
      setSelectedVeiculo(veiculo);
      setSelectedLinha(linha);
      setModalVisible(true);
    },
    []
  );

  const handleSync = () => {
    setLinhaFiltrada(0);
    fetchPositions();
  };

  const handleMarkerParadaPress = React.useCallback((parada: Parada) => {
    setSelectedParada(parada);
    setModalParadaVisible(true);
  }, []);

  const handleCloseModalFilter = () => {
    setModalFilterVisible(false);
  };
  const handleCloseFilterParadas = () => {
    setModalFilterParadas(false);
  };
  const handleCloseModalPrevisao = () => {
    setModalPrevisao(false);
  };

  const handlePickSelection = (value: number) => {
    setLinhaFiltrada(value);
    handleCloseModalFilter();
    if (value === 0) {
      handleSync();
    }
  };
  const fetchPositions = async () => {
    try {
      const data = await getPosicoes();
      setlinhas(data);
    } catch (err) {
      setError('Falha ao carregar as posições');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPositions();
  }, []);

  useEffect(() => {
    const fetchParadas = async () => {
      try {
        const data = await getAllParadas();
        setParadas(data);
      } catch (err) {
        setError('Falha ao carregar as paradas');
      } finally {
        setLoading(false);
      }
    };
    fetchParadas();
  }, []);

  if (loading) {
    return (
      <View>
        <Text>Carregando...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View>
        <Text>{error}</Text>
      </View>
    );
  }

  return (
    <>
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.5505,
          longitude: -46.6333,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        {filteredLinhas.flatMap((linha, linhaIndex) =>
          linha.vs.map((veiculo, veiculoIndex) => (
            <VeiculoMarker
              key={`${linhaIndex}-${veiculoIndex}`}
              veiculo={veiculo}
              linha={linha}
              onPress={handleMarkerPress}
              index={veiculoIndex}
            />
          ))
        )}
        {paradas.map((parada, index) => (
          <ParadaMarker
            key={index}
            parada={parada}
            onPress={handleMarkerParadaPress}
          />
        ))}
      </MapView>
      <InforsModal />

      <ModalVeiculos
        isVisible={modalVisible}
        onClose={() => {
          setModalVisible(false);
          setSelectedVeiculo(null);
        }}
        veiculo={selectedVeiculo}
        linha={selectedLinha?.cl}
      />
      <Modal
        animationType='slide'
        transparent
        visible={modalParadaVisible}
        onRequestClose={() => {
          setModalParadaVisible(!modalParadaVisible);
        }}
      >
        <ModalParadas
          isVisible={modalPrevisao}
          onClose={handleCloseModalPrevisao}
          parada={selectedParada}
        />
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            {selectedParada && (
              <>
                <Text style={styles.modalTextParada}>
                  PARADA {selectedParada.np}
                </Text>
              </>
            )}
            <View style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>
              <Button
                title='Ver Previsão de chegada'
                onPress={() => setModalPrevisao(true)}
              />
              <Button
                title='Fechar'
                onPress={() => {
                  setModalParadaVisible(!modalParadaVisible);
                  setSelectedParada(null);
                }}
              />
            </View>
          </View>
        </View>
      </Modal>

      <Filters
        onOpen={() => setModalFilterVisible(true)}
        onSync={handleSync}
        onOpenFilterParadas={() => setModalFilterParadas(true)}
      />

      <ModalFilter
        isVisible={modalFilterVisible}
        onClose={handleCloseModalFilter}
        linhas={linhas}
        linhaFiltrada={handlePickSelection}
      />

      <ModalFilterParadas
        paradas={paradas}
        isVisible={modalFilterParadas}
        onClose={handleCloseFilterParadas}
      />
    </>
  );
}

const styles = StyleSheet.create({
  filters: {
    position: 'absolute',
    top: 50,
    right: 10,
    width: '70%',
    height: 40,
    backgroundColor: 'white',
    borderRadius: 50,
    borderWidth: 1,
  },
  container: {
    flex: 1,
  },
  map: {
    width: '100%',
    height: '100%',
  },
  imgContent: {
    width: 10,
    height: 10,
    backgroundColor: 'red',
    borderRadius: 50,
  },
  paradaStyle: {
    width: 10,
    height: 10,
    backgroundColor: 'blue',
    borderRadius: 50,
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
    marginBottom: 10,
    fontSize: 15,
    textAlign: 'center',
    fontWeight: '600',
  },
  modalTextParada: {
    marginBottom: 50,
    fontSize: 20,
    textAlign: 'center',
    fontWeight: '600',
  },
});
