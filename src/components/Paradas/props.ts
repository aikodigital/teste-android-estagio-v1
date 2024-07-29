export interface Parada {
  cp: number;
  np: string;
  ed: string;
  px: number; // Longitude
  py: number; // Latitude
}

export interface Veiculo {
  id: string;
  latitude: number;
  longitude: number;
  horario: string;
  deficiente: boolean;
}

export interface ParadasProps {
  paradas: Parada[];
  veiculos: Veiculo[];
  onSelectParada: (parada: Parada) => void;
  onSelectVeiculo: (veiculo: Veiculo) => void;
}
