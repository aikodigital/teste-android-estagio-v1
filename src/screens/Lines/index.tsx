import React, { useRef, useEffect, useState } from 'react';
import MapView, { Marker } from 'react-native-maps';
import { Container, Content, Title } from './styles';
import { Modalize } from 'react-native-modalize';
import { Items } from '../../components/Items';
import { Search } from '../../components/Search';
import useOlhoVivoAPI from '../../hooks';
import { FlatList } from 'react-native';

export function Lines() {
  const modalizeRef = useRef<Modalize>(null);
  const { vehiclePositions, fetchVehiclePositions, fetchBusStops } = useOlhoVivoAPI();
  const [buses, setBuses] = useState<{ number: string; latitude: number; longitude: number; }[]>([]);
  
  useEffect(() => {
    fetchVehiclePositions();
  }, [fetchVehiclePositions]);

  useEffect(() => {
    if (vehiclePositions) {
      const newBuses = vehiclePositions.l.flatMap(line =>
        line.vs.map(vehicle => ({
          number: line.c,
          latitude: vehicle.py,
          longitude: vehicle.px,
        }))
      );
      setBuses(newBuses);
    }
  }, [vehiclePositions]);

  return (
    <Container>
      <Content>
        <Search />
      </Content>
      <MapView
        style={{
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
        {buses.map((bus, index) => (
          <Marker
            key={index}
            coordinate={{ latitude: bus.latitude, longitude: bus.longitude }}
            title={`Bus ${bus.number}`}
          />
        ))}
      </MapView>
      <Modalize
        ref={modalizeRef}
        snapPoint={300}
        alwaysOpen={300}
      >
        <Title>
          Linhas de ônibus e Paradas
        </Title>
        <FlatList
          data={buses}
          keyExtractor={(item, index) => index.toString()}
          renderItem={({ item }) => (
            <Items
              destiny={`${item.number}`}
              number={item.number}
              time={'Não disponível'} 
            />
          )}
        />
      </Modalize>
    </Container>
  );
}
