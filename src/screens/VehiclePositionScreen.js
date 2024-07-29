import React, { useEffect, useState } from 'react';
import { View, StyleSheet, TextInput } from 'react-native';
import MapViewComponent from '../components/MapViewComponent';
import { authenticate, getVehicles } from '../services/OlhoVivoAPI';

const REFRESH_INTERVAL = 30000; // 30 segundos

const VehiclePositionScreen = () => {
  const [vehicles, setVehicles] = useState([]);
  const [filteredVehicles, setFilteredVehicles] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [mapRegion, setMapRegion] = useState({
    latitude: -23.5505,
    longitude: -46.6333,
    latitudeDelta: 0.0922,
    longitudeDelta: 0.0421,
  });

  useEffect(() => {
    const fetchData = async () => {
      await authenticate();
      const data = await getVehicles();
      setVehicles(data.l);
      setFilteredVehicles(data.l);
    };

    fetchData();
    const interval = setInterval(fetchData, REFRESH_INTERVAL);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (searchQuery === '') {
      setFilteredVehicles(vehicles);
    } else {
      const filtered = vehicles.filter(vehicle =>
        vehicle.c.includes(searchQuery)
      );
      setFilteredVehicles(filtered);
    }
  }, [searchQuery, vehicles]);

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.searchInput}
        placeholder="Buscar por linha"
        value={searchQuery}
        onChangeText={setSearchQuery}
      />
      <MapViewComponent vehicles={filteredVehicles} initialRegion={mapRegion} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  searchInput: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    margin: 10,
    paddingHorizontal: 10,
  },
});

export default VehiclePositionScreen;
