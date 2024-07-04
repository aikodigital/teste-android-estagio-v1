import React, { useEffect, useState } from 'react';
import { View, Text, Modal, TouchableOpacity, FlatList, ActivityIndicator } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import { fetchStopsByLine, fetchPrevisao, fetchLinesData } from '../services/api';
import BusIcon from '../assets/busIcone.png';
import StopBus from '../assets/stopbus.png';
import { Line, Stop, Prediction, MapScreenProps, Vehicle } from '../types/interfaces';
import styles from '../styles/MapScreenStyles';
import PredictionItem from '../components/PredictionItem';
import { linestyles } from '../styles/LinesScreen';


const MapScreen: React.FC<MapScreenProps> = ({ route }) => {
  const { line } = route.params;
  const [stops, setStops] = useState<Stop[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [refreshTime, setRefreshTime] = useState<string | null>(null); 
  const [selectedStop, setSelectedStop] = useState<Stop | null>(null); 
  const [predictions, setPredictions] = useState<Prediction[]>([]); 
  const [modalVisible, setModalVisible] = useState<boolean>(false); 
  const [vehicles, setVehicles] = useState<Vehicle[]>(line.vs || []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const data = await fetchStopsByLine(line.cl);
      if (data) {
        setStops(data);
      } else {
        setError('Erro ao carregar as paradas');
      }
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
      updateRefreshTime(); 
    }
  };

  const updateRefreshTime = () => {
    const now = new Date();
    const hour = now.getHours();
    const minute = now.getMinutes();
    const second = now.getSeconds();
    setRefreshTime(`${hour}:${minute}:${second}`);
  };

  const fetchPredictions = async (codigoParada: number) => {
    try {
      const data = await fetchPrevisao(codigoParada, line.cl);
      if (data && data.p && data.p.l.length > 0) {
        setPredictions(data.p.l);
      } else {
        setPredictions([]); 
      }
      setModalVisible(true); 
      await fetchVehiclesPosition();
    } catch (error) {
      setError('Erro ao carregar previsões de chegada');
    }
  };

  const fetchVehiclesPosition = async () => {
    try {
      const data = await fetchLinesData();
      if (data && data.l) {
        const lineData = data.l.find((l: Line) => l.cl === line.cl);
        if (lineData) {
          setVehicles(lineData.vs);
        }
      }
    } catch (error) {
      console.error('Erro ao atualizar as posições dos veículos:', error);
    }
  };

  useEffect(() => {
    const interval = setInterval(() => {
      fetchVehiclesPosition();
      fetchData(); 
    }, 5000); 

    return () => clearInterval(interval); 
  }, [line.cl]);

  useEffect(() => {
    fetchVehiclesPosition();
    fetchData(); 
  }, []);

  const handleStopPress = async (stop: Stop) => {
    setSelectedStop(stop);
    await fetchPredictions(stop.cp); 
  };

  if (error) {
    return (
      <View style={linestyles.center}>
        <Text style={linestyles.error}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Linha: {line.cl} {line.lt0} ↔ {line.lt1} Horário de atualização: {refreshTime}</Text>
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: vehicles[0]?.py || -23.55052,
          longitude: vehicles[0]?.px || -46.633308,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        {vehicles.map((vehicle) => (
          <Marker
            key={vehicle.p}
            coordinate={{ latitude: vehicle.py, longitude: vehicle.px }}
            title={`Veículo ${vehicle.p}`}
            description={`Acessível: ${vehicle.a ? 'Sim' : 'Não'}`}
            image={BusIcon}
          />
        ))}
        {stops.map((stop) => (
          <Marker
            key={stop.cp}
            coordinate={{ latitude: stop.py, longitude: stop.px }}
            title={`Parada ${stop.np}`}
            description={stop.ed}
            image={StopBus}
            onPress={() => handleStopPress(stop)}
          />
        ))}
      </MapView>

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Previsão de Chegada</Text>
            {selectedStop && (
              <>
                <Text style={styles.modalSubTitle}>{selectedStop.np}</Text>
                {predictions.length === 0 ? (
                  <Text style={styles.modalSubTitle}>Não há informações de previsão de chegada para esta parada.</Text>
                ) : (
                  <FlatList
                    data={predictions}
                    renderItem={({ item }) => <PredictionItem item={item} />}
                    keyExtractor={(item, index) => `${item.c}-${index}`}
                  />
                )}
                <TouchableOpacity style={styles.closeButton} onPress={() => setModalVisible(false)}>
                  <Text style={styles.closeButtonText}>Fechar</Text>
                </TouchableOpacity>
              </>
            )}
          </View>
        </View>
      </Modal>
    </View>
  );
};

export default MapScreen;
