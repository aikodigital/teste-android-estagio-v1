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
  Lines: undefined;
  Details: { codigoParada?: number; lt0?: string; lt1?: string };
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
export interface Veiculo {
  p: string;
  t: string;
  a: boolean;
  ta: string;
  py: number;
  px: number;
}

export interface Linha {
  c: string;
  cl: number;
  sl: number;
  lt0: string;
  lt1: string;
  qv: number;
  vs: Veiculo[];
}
export interface Parada {
  cp: number;
  np: string;
  py: number;
  px: number;
  l: Linha[];
}
export interface ArrivalPredictions {
  hr: string;
  p: Parada;
}
