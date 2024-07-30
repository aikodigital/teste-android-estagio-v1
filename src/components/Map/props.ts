export interface Parada {
  cp: number;
  np: string;
  ed: string;
  px: number; // Longitude
  py: number; // Latitude
}

export interface Veiculo {
  id: number;
  latitude: number;
  longitude: number;
  horario: string;
  deficiente: boolean;
}

export interface MapComponentProps {
  latitude: number;
  longitude: number;
  paradas: Parada[];
  veiculos: Veiculo[];
  selectedParada?: Parada;
  selectedVeiculo?: Veiculo;
}
