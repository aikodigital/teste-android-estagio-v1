import React, { useRef, useEffect, useState } from 'react';
import MapView, { Marker } from 'react-native-maps';
import { Container, Title, Icon, Content } from './styles';
import { Modalize } from 'react-native-modalize';
import { Items } from '../../components/Items';
import { Search } from '../../components/Search';
import useOlhoVivoAPI from '../../hooks';
import { FlatList, Text } from 'react-native';
import * as Location from 'expo-location';

export function Lines() {
  const modalizeRef = useRef<Modalize>(null);
  const { vehiclePositions, fetchVehiclePositions, fetchBusStops, busStops, fetchArrivalForecast } = useOlhoVivoAPI();
  const [buses, setBuses] = useState<{ number: string; latitude: number; longitude: number; cp: number; cl: number }[]>([]);
  const [filteredBuses, setFilteredBuses] = useState<{ number: string; latitude: number; longitude: number; cp: number; cl: number }[]>([]);
  const [filteredStops, setFilteredStops] = useState<{ cp: number; np: string; py: number; px: number }[]>([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [arrivalTime, setArrivalTime] = useState<string | null>(null);
  const [userLocation, setUserLocation] = useState<{ latitude: number; longitude: number } | null>(null);

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

    const findNearestStop = (stops: { py: number; px: number }[], userLoc: { latitude: number; longitude: number }) => {
      return stops.reduce((nearest, stop) => {
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
        console.log(nearestStop);
        console.log(firstFilteredBus);

        if (nearestStop.stop) {
          fetchArrivalForecast(nearestStop.stop.cp, firstFilteredBus.cl)
            .then(() => {
              if (vehiclePositions) {
                const forecasts = vehiclePositions.l.flatMap(line =>
                  line.vs.map(vehicle => ({
                    number: line.c,
                    time: veh.t,
                    destination: line.lt1
                  }))
                ).filter(forecast => forecast.number === nearestBus.number);

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
              }
            })
            .catch(err => {
              console.error('Erro ao buscar previsão de chegada:', err);
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
            <Icon name="bus-stop-covered" />
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
        {arrivalTime && <Text>Tempo de chegada: {arrivalTime}</Text>}
        <FlatList
          data={filteredBuses.slice(0, 20)}
          keyExtractor={(item, index) => index.toString()}
          renderItem={({ item }) => (
            <Items
              destiny={`${item.number}`}
              number={item.number}
              time={arrivalTime || 'Não disponível'}
            />
          )}
        />
      </Modalize>
    </Container>
  );
}