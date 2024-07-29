// types.ts

export type Bus = {
  p: string;
  t: string;
  a: boolean;
  ta: string;
  py: number;
  px: number;
};

export type BusStation = {
  description: string | undefined;
  name: string | undefined;
  cp: number;
  np: string;
  ed: string;
  py: number;
  px: number;
};

export type Line = {
  c: string;
  cl: number;
  sl: number;
  lt0: string;
  lt1: string;
  qv: number;
  vs: Bus[];
};

export type BusData = {
  hr: string;
  l: Line[];
};

export type LineDetails = {
  lt0: string;
  lt1: string;
};

export type BusMarkerProps = {
  bus: Bus;
  lineDetails: LineDetails;
};

export type FilterState = {
  busesChecked: boolean;
  stationsChecked: boolean;
  linesChecked: boolean;
};

export type RootStackParamList = {
  Details: {
    bus: Bus; // Se precisar passar um objeto Bus
    lineDetails: {
      lt0: string;
      lt1: string;
    };
  };
};

export type BusStationsMarkerProps = {
  station: BusStation;
};

export type RootStackParamLists = {
  Lines: undefined;
  Details: { bus: Bus; lineDetails: { lt0: string; lt1: string } };
};

export type MapContextType = {
  showBusStations: boolean;
  showBuses: boolean;
  setShowBusStations: (value: boolean) => void;
  setShowBuses: (value: boolean) => void;
};
