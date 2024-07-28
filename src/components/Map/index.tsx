import React from "react";
import { View } from "react-native";
import MapView from "react-native-maps";
import { styles } from "./styles";
import { useBusPositions } from "./mapComponents/useBusPositions";
import { useRegion } from "./mapComponents/useRegion";
import { BusMarker } from "./mapComponents/BusMarker";
import { Line, Bus } from "../../types/types";

export function MapRender() {
  const busPositions = useBusPositions();
  const { region, onRegionChangeComplete } = useRegion();

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
        if (bus.p === busId) {
          return {
            lt0: line.lt0,
            lt1: line.lt1,
          };
        }
      }
    }
    return { lt0: "", lt1: "" };
  };

  return (
    <View style={styles.container}>
      <MapView
        style={styles.map}
        initialRegion={region}
        onRegionChangeComplete={onRegionChangeComplete}
      >
        {busPositions.flatMap((line: Line) =>
          line.vs
            .filter(isBusWithinRegion)
            .map((bus: Bus, index) => (
              <BusMarker
                key={`${bus.p}-${index}`}
                bus={bus}
                lineDetails={getLineDetails(bus.p)}
              />
            ))
        )}
      </MapView>
    </View>
  );
}
