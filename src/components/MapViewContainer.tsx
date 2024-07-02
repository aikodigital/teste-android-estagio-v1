import React from "react";
import { ActivityIndicator, StyleSheet, Text, View } from "react-native";
import MapView, { Marker, Region } from "react-native-maps";
import { Parada } from "../types/types";
import { Ionicons } from "@expo/vector-icons";

export interface Position {
  hr: string;
  vs: {
    p: string;
    py: number;
    px: number;
  }[];
}

interface MapViewContainerProps {
  position: Position | null;
  paradas: Parada[];
  handleSelectParada: (parada: Parada) => void;
  mapRegion: Region;
  selectedParada: Parada | null;
  previsoesPorParada: any;
}

const MapViewContainer: React.FC<MapViewContainerProps> = ({
  position,
  paradas,
  handleSelectParada,
  mapRegion,
}) => (
  <View style={styles.mapContainer}>
    {position ? (
      <MapView
        style={styles.map}
        initialRegion={mapRegion}
        zoomEnabled
        testID="map-view"
      >
        {paradas.map((parada) => (
          <Marker
            key={parada.cp}
            coordinate={{ latitude: parada.py, longitude: parada.px }}
            title={`${parada.np} - ${parada.cp}`}
            onPress={() => handleSelectParada(parada)}
          >
            <Ionicons name="home-sharp" size={24} color="black" />
          </Marker>
        ))}
        {position.vs.map((bus) => (
          <Marker
            key={bus.p}
            coordinate={{ latitude: bus.py, longitude: bus.px }}
            title={`Ônibus ${bus.p}`}
            description={`Atualizado às ${position.hr}`}
          >
            <Ionicons name="bus-sharp" size={24} color="brown" />
          </Marker>
        ))}
      </MapView>
    ) : (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#0000ff" />
        <Text>Carregando...</Text>
      </View>
    )}
  </View>
);

const styles = StyleSheet.create({
  mapContainer: {
    width: "100%",
    height: 350,
    borderRadius: 8,
    overflow: "hidden",
    marginBottom: 16,
  },
  map: {
    flex: 1,
  },
  loadingContainer: {
    ...StyleSheet.absoluteFillObject,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "rgba(255, 255, 255, 0.8)",
  },
});

export default MapViewContainer;
