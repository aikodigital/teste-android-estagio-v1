import React, { useContext } from "react";
import { Text, View } from "react-native";
import MapView from "react-native-maps";
import { styles } from "./styles";
import { useBusPositions } from "./mapComponents/useBusPositions";
import { useBusStations } from "./mapComponents/useBusStations";
import { useRegion } from "./mapComponents/useRegion";
import { BusMarker } from "./mapComponents/BusMarker";
import { BusStationsMarker } from "./mapComponents/BusStationsMarker";
import { MapContext } from "../../contexts/MapContext";
import { useUpdateHour } from "./mapComponents/lastUpdate";

export function MapRender() {
  const busPositions = useBusPositions();
  const busStations = useBusStations();
  const { region, onRegionChangeComplete } = useRegion();
  const context = useContext(MapContext);
  const lastUpdate = useUpdateHour();

  // Verifica se o contexto está definido
  if (!context) {
    throw new Error("MapRender must be used within a MapProvider");
  }

  const { showBusStations, showBuses } = context;

  const isBusWithinRegion = (bus: { py: number; px: number }) => {
    const { latitude, longitude, latitudeDelta, longitudeDelta } = region;
    const latMin = latitude - latitudeDelta / 2;
    const latMax = latitude + latitudeDelta / 2;
    const lonMin = longitude - longitudeDelta / 2;
    const lonMax = longitude + longitudeDelta / 2;

    return (
      bus.py >= latMin &&
      bus.py <= latMax &&
      bus.px >= lonMin &&
      bus.px <= lonMax
    );
  };

  const getLineDetails = (busId: number) => {
    for (const line of busPositions) {
      for (const bus of line.vs) {
        // Converter bus.p para number antes de comparar
        if (Number(bus.p) === busId) {
          return {
            lt0: line.lt0,
            lt1: line.lt1,
          };
        }
      }
    }
    return { lt0: "", lt1: "" };
  };

  const renderBusMarkers = () => {
    return busPositions.flatMap((line) =>
      line.vs.filter(isBusWithinRegion).map((bus, index) => (
        <BusMarker
          key={`${bus.p}-${index}`}
          bus={bus}
          // Converter bus.p para number antes de passar para getLineDetails
          lineDetails={getLineDetails(Number(bus.p))}
        />
      ))
    );
  };

  return (
    <View style={styles.container}>
      <Text>{`Ultima atualizacao: ${lastUpdate}`}</Text>
      <MapView
        style={styles.map}
        initialRegion={region}
        onRegionChangeComplete={onRegionChangeComplete}
      >
        {showBuses && renderBusMarkers()}

        {showBusStations &&
          busStations
            .filter((station) =>
              isBusWithinRegion({ py: station.py, px: station.px })
            ) // Apenas as paradas dentro da região
            .map((station, index) => (
              <BusStationsMarker
                key={`${station.cp}-${index}`}
                station={station}
              />
            ))}
      </MapView>
    </View>
  );
}
