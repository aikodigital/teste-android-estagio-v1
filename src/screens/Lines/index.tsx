import React, { useRef, useEffect, useState } from 'react';
import MapView, { Marker } from 'react-native-maps';
import { Container, Title, Icon, Content } from './styles';
import { Modalize } from 'react-native-modalize';
import { Items } from '../../components/Items';
import { Search } from '../../components/Search';
import useOlhoVivoAPI from '../../hooks';
import { FlatList, Text, ActivityIndicator, View } from 'react-native';
import * as Location from 'expo-location';

interface NearestStop {
  stop: { py: number; px: number; cp: number } | null;
  distance: number;
}

export function Lines() {
  const modalizeRef = useRef<Modalize>(null);
  const { vehiclePositions, fetchVehiclePositions, fetchBusStops, busStops, fetchArrivalForecast } = useOlhoVivoAPI();
  const [buses, setBuses] = useState<{ number: string; latitude: number; longitude: number; cp: number; cl: number }[]>([]);
  const [filteredBuses, setFilteredBuses] = useState<{ number: string; latitude: number; longitude: number; cp: number; cl: number }[]>([]);
  const [filteredStops, setFilteredStops] = useState<{ cp: number; np: string; py: number; px: number }[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [arrivalForecasts, setArrivalForecasts] = useState<{ number: string; time: string; destination: string; arrivalMessage: string }[]>([]);
  const [userLocation, setUserLocation] = useState<{ latitude: number; longitude: number } | null>(null);
  const [loadingBuses, setLoadingBuses] = useState(false);
  const [loadingForecast, setLoadingForecast] = useState(false);

  useEffect(() => {
    const getLocation = async () => {
      const { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        return;
      }
      const location = await Location.getCurrentPositionAsync({ accuracy: Location.Accuracy.High });
      setUserLocation({
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
      });
    };
    getLocation();
  }, []);

  useEffect(() => {
    fetchVehiclePositions();
    fetchBusStops('');
  }, [fetchVehiclePositions, fetchBusStops]);

  useEffect(() => {
    if (vehiclePositions) {
      setLoadingBuses(true);
      const newBuses = vehiclePositions.l.flatMap(line =>
        line.vs.map(vehicle => ({
          number: line.c,
          latitude: vehicle.py,
          longitude: vehicle.px,
          cp: vehicle.p,
          cl: line.cl
        }))
      );
      setBuses(newBuses);
      setLoadingBuses(false);
    }
  }, [vehiclePositions]);

  useEffect(() => {
    const calculateDistance = (lat1: number, lon1: number, lat2: number, lon2: number) => {
      const R = 6371;
      const dLat = (lat2 - lat1) * (Math.PI / 180);
      const dLon = (lon2 - lon1) * (Math.PI / 180);
      const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(lat1 * (Math.PI / 180)) * Math.cos(lat2 * (Math.PI / 180)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      return R * c;
    };

    const findNearestStop = (stops: { py: number; px: number; cp: number }[], userLoc: { latitude: number; longitude: number }) => {
      return stops.reduce<NearestStop>((nearest, stop) => {
        const distance = calculateDistance(userLoc.latitude, userLoc.longitude, stop.py, stop.px);
        if (distance < nearest.distance) {
          return { stop, distance };
        }
        return nearest;
      }, { stop: null, distance: Infinity });
    };

    const filteredBuses = buses.filter(bus => bus.number.includes(searchTerm));
    const filteredStops = busStops.filter(stop => stop.np.includes(searchTerm));
    setFilteredBuses(filteredBuses);
    setFilteredStops(filteredStops);

    if (searchTerm && userLocation) {
      const firstFilteredBus = filteredBuses[0];
      if (firstFilteredBus && filteredStops.length > 0) {
        const nearestStop = findNearestStop(filteredStops, userLocation);

        if (nearestStop.stop) {
          setLoadingForecast(true);
          fetchArrivalForecast(nearestStop.stop.cp, firstFilteredBus.cl)
            .then(() => {
              if (vehiclePositions) {
                const forecasts = vehiclePositions.l.flatMap(line =>
                  line.vs.map(vehicle => ({
                    number: line.c,
                    time: vehicle.ta,
                    destination: line.lt1
                  }))
                ).filter(forecast => forecast.number === firstFilteredBus.number);

                const arrivalMessages = forecasts.map(forecast => {
                  const minutes = parseInt(forecast.time, 10);
                  let arrivalMessage: string;

                  if (minutes <= 0) {
                    arrivalMessage = 'Chegada iminente';
                  } else if (minutes < 60) {
                    arrivalMessage = `${minutes} minutos`;
                  } else {
                    const hours = Math.floor(minutes / 60);
                    const remainingMinutes = minutes % 60;
                    arrivalMessage = `${hours}h ${remainingMinutes}m`;
                  }

                  return {
                    ...forecast,
                    arrivalMessage,
                  };
                });

                setArrivalForecasts(arrivalMessages);
                setLoadingForecast(false);
              }
            })
            .catch(err => {
              console.error('Erro ao buscar previsão de chegada:', err);
              setLoadingForecast(false);
            });
        }
      }
    }
  }, [searchTerm, buses, busStops, userLocation, fetchArrivalForecast]);

  const handleSearch = () => {
    setSearchTerm(searchTerm.trim());
  };

  return (
    <Container>
      <MapView
        style={{
          position: 'absolute',
          width: '100%',
          height: '100%'
        }}
        initialRegion={{
          latitude: -23.5505,
          longitude: -46.6333,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        <Marker coordinate={{ latitude: -23.5505, longitude: -46.6333 }} title="São Paulo" />
        {filteredBuses.map((bus, index) => (
          <Marker
            key={index}
            coordinate={{ latitude: bus.latitude, longitude: bus.longitude }}
            title={`Bus ${bus.number}`}
          >
            <Icon name="bus-marker" />
          </Marker>
        ))}
        {filteredStops.map((stop, index) => (
          <Marker
            key={index}
            coordinate={{ latitude: stop.py, longitude: stop.px }}
            title={`Parada ${stop.np}`}
          >
            <Icon type='PRIMARY' name="bus-stop-covered" />
          </Marker>
        ))}
      </MapView>
      <Content>
        <Search
          textInput={searchTerm}
          onSearch={() => handleSearch()}
          onChangeText={text => setSearchTerm(text)}
        />
      </Content>
      <Modalize
        ref={modalizeRef}
        snapPoint={300}
        alwaysOpen={300}
      >
        <Title>
          Linhas de ônibus e Paradas
        </Title>
        {loadingForecast || loadingBuses ? (
          <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <ActivityIndicator size="large" color="#0000ff" />
          </View>
        ) : (
          <>
            {arrivalForecasts.length > 0 && arrivalForecasts.map((forecast, index) => (
              <Text key={index}>{`Linha ${forecast.number}: ${forecast.arrivalMessage}`}</Text>
            ))}
            <FlatList
              data={filteredBuses.slice(0, 20)}
              keyExtractor={(item, index) => index.toString()}
              renderItem={({ item }) => (
                <Items
                  destiny={`${item.number}`}
                  number={item.number}
                  time={arrivalForecasts.find(forecast => forecast.number === item.number)?.arrivalMessage || 'Não disponível'}
                />
              )}
              ListEmptyComponent={
                <ActivityIndicator size="large" color="#0000ff" />
              }
            />
          </>
        )}
      </Modalize>
    </Container>
  );
}
