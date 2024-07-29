import React, { createContext, useState, ReactNode } from "react";

interface MapContextType {
  showBusStations: boolean;
  showBuses: boolean;
  setShowBusStations: (value: boolean) => void;
  setShowBuses: (value: boolean) => void;
}

export const MapContext = createContext<MapContextType | undefined>(undefined);

export const MapProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [showBusStations, setShowBusStations] = useState(true);
  const [showBuses, setShowBuses] = useState(true);

  return (
    <MapContext.Provider
      value={{ showBusStations, showBuses, setShowBusStations, setShowBuses }}
    >
      {children}
    </MapContext.Provider>
  );
};
