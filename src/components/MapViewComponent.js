import React from 'react';
import { StyleSheet } from 'react-native';
import MapView, { Marker } from 'react-native-maps';

const MapViewComponent = ({ vehicles, initialRegion }) => {
  return (
    <MapView
      style={styles.map}
      initialRegion={initialRegion} // Defina a região inicial
      showsUserLocation
    >
      {vehicles.map(vehicle => (
        <Marker
          key={vehicle.vs[0].p}
          coordinate={{ latitude: vehicle.vs[0].py, longitude: vehicle.vs[0].px }}
          title={`Linha: ${vehicle.c}`}
        />
      ))}
    </MapView>
  );
};

const styles = StyleSheet.create({
  map: {
    ...StyleSheet.absoluteFillObject,
  },
});

export default MapViewComponent;
