import React, { useEffect, useRef } from "react";
import { View } from "react-native";
import MapView, { Marker } from "react-native-maps";
import { styles } from "./styles";
import { MapComponentProps } from "./props";

const MapComponent: React.FC<MapComponentProps> = ({
  latitude,
  longitude,
  paradas,
  veiculos,
  selectedParada,
  selectedVeiculo,
}) => {
  const mapRef = useRef<MapView>(null);

  useEffect(() => {
    if (mapRef.current) {
      const target = selectedVeiculo
        ? {
            latitude: selectedVeiculo.latitude,
            longitude: selectedVeiculo.longitude,
          }
        : selectedParada
          ? { latitude: selectedParada.py, longitude: selectedParada.px }
          : { latitude, longitude };

      mapRef.current.animateToRegion({
        ...target,
        latitudeDelta: 0.01,
        longitudeDelta: 0.01,
      });
    }
  }, [selectedParada, selectedVeiculo, latitude, longitude]);

  return (
    <View style={styles.container}>
      <MapView
        ref={mapRef}
        style={styles.map}
        initialRegion={{
          latitude,
          longitude,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        {paradas.map((parada) => (
          <Marker
            key={parada.cp}
            coordinate={{ latitude: parada.py, longitude: parada.px }}
            title={parada.np}
            description={parada.ed}
          />
        ))}
        {veiculos.map((veiculo) => (
          <Marker
            key={veiculo.id}
            coordinate={{
              latitude: veiculo.latitude,
              longitude: veiculo.longitude,
            }}
            title={`${veiculo.id} - Horário: ${veiculo.horario}`}
            description={`PNE: ${veiculo.deficiente ? "Sim" : "Não"}`}
            pinColor={selectedVeiculo?.id === veiculo.id ? "blue" : "lightblue"}
          />
        ))}
        {selectedParada && (
          <Marker
            coordinate={{
              latitude: selectedParada.py,
              longitude: selectedParada.px,
            }}
            title={selectedParada.np}
            description={selectedParada.ed}
            pinColor="red"
          />
        )}
      </MapView>
    </View>
  );
};

export default MapComponent;
