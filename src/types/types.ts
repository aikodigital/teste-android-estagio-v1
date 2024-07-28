export type Bus = {
  p: number; // Prefixo do veículo
  a: boolean; // Acessível
  ta: string; // Horário da última atualização
  py: number; // Latitude
  px: number; // Longitude
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
