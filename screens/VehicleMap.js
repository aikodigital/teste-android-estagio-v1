import React, { useState, useEffect, useCallback, useMemo, useRef } from 'react';
import { ActivityIndicator, Alert, Text, View } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import axios from 'axios';
import Icon from 'react-native-vector-icons/FontAwesome5';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import styled from 'styled-components/native';


const OLHO_VIVO_API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';
const API_KEY = 'b7c59cb9ae96c66937e6c61c274206493316c5c21105db7b64cbcbd0672cdbc4'; 


const VehicleMap = () => {
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [lastUpdated, setLastUpdated] = useState(null);
  const [refreshing, setRefreshing] = useState(false);
  const [refreshStatus, setRefreshStatus] = useState('');

  const abortControllerRef = useRef(null);

  const formatDateTime = useCallback((date) => {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    let hours = date.getHours();
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    const ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12;
    const formattedHours = String(hours).padStart(2, '0');
    return `${day}.${month}.${year} ${formattedHours}:${minutes}:${seconds} ${ampm}`;
  }, []);

  const fetchVehiclePositions = useCallback(async (signal) => {
    try {
      if (abortControllerRef.current) {
        abortControllerRef.current.abort();
      }

      const abortController = new AbortController();
      abortControllerRef.current = abortController;

      setRefreshStatus('Atualizando...');

      const authResponse = await axios.post(`${OLHO_VIVO_API_URL}/Login/Autenticar?token=${API_KEY}`, {
        signal: abortController.signal
      });

      if (authResponse.status !== 200) {
        throw new Error('Erro na autenticação');
      }

      const start = Date.now();
      const positionResponse = await axios.get(`${OLHO_VIVO_API_URL}/Posicao`, { signal: abortController.signal });
      const data = positionResponse.data;

      if (data && data.l) {
        const end = Date.now();
        console.log(`Tempo para processar dados: ${end - start}ms`);
        setVehicles(data.l);
        setLastUpdated(formatDateTime(new Date()));
        setRefreshStatus('Atualização concluída');
      } else {
        throw new Error('Dados inesperados da API: ' + JSON.stringify(data));
      }
    } catch (error) {
      if (axios.isCancel(error)) {
        console.log('Requisição cancelada');
      } else {
        Alert.alert('Erro', 'Erro ao buscar posições dos veículos');
        console.error('Erro ao buscar posições dos veículos:', error);
        setRefreshStatus('Erro na atualização');
      }
    } finally {
      setLoading(false);
      setRefreshing(false);
      setTimeout(() => setRefreshStatus(''), 3000); 
    }
  }, [formatDateTime]);

  useEffect(() => {
    const abortController = new AbortController();
    abortControllerRef.current = abortController;
    
    fetchVehiclePositions(abortController.signal);

    return () => {
      abortControllerRef.current?.abort();
    };
  }, [fetchVehiclePositions]);

  const handleRefresh = () => {
    if (!refreshing && !loading) {
      setRefreshing(true);
      console.log('Iniciando atualização...');
      fetchVehiclePositions(new AbortController().signal);
    } else {
      console.log('Atualização já em andamento ou carregamento em curso');
    }
  };

  const renderMarkers = useMemo(() => {
    return vehicles.flatMap((line, lineIndex) =>
      line.vs.map((vehicle, vehicleIndex) => (
        <Marker
          key={`${vehicle.p}-${vehicleIndex}`}
          coordinate={{
            latitude: vehicle.py,
            longitude: vehicle.px,
          }}
          title={`Linha: ${line.c}`}
          description={`Veículo: ${vehicle.p} • Sentido: ${vehicle.a ? line.lt0 : line.lt1}`}
        >
          <Icon name="bus" size={24} color="#007bff" />
        </Marker>
      ))
    );
  }, [vehicles]);

  return (
    <Container>
      {loading ? (
        <ActivityIndicator size="large" color="#007bff" />
      ) : (
        <>
          <MapView
            style={styles.map}
            initialRegion={{
              latitude: -23.55052,
              longitude: -46.633308,
              latitudeDelta: 0.0922,
              longitudeDelta: 0.0421,
            }}
            showsUserLocation={true}
            showsMyLocationButton={true}
          >
            {renderMarkers}
          </MapView>
          <Footer>
            <LastUpdatedText>
              Última atualização: {lastUpdated ? lastUpdated : 'Nunca'}
            </LastUpdatedText>
            <RefreshButton onPress={handleRefresh}>
              <MaterialCommunityIcons name="refresh" size={24} color="#003366" />
            </RefreshButton>
            {refreshStatus ? (
              <StatusText>{refreshStatus}</StatusText>
            ) : null}
          </Footer>
        </>
      )}
    </Container>
  );
};

const Container = styled.View`
  flex: 1;
`;

const Footer = styled.View`
  padding: 5px;
  background-color: #f1f1f1;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;

const LastUpdatedText = styled.Text`
  font-size: 12px;
  color: #007bff;
`;

const RefreshButton = styled.TouchableOpacity`
  flex-direction: row;
  align-items: center;
  padding: 5px;
  border-radius: 5px;
`;

const StatusText = styled.Text`
  font-size: 12px;
  color: #007bff;
  margin-left: 10px;
`;

const styles = {
  map: {
    flex: 1,
  },
};

export default VehicleMap;
