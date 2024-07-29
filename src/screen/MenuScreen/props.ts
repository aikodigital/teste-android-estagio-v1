export interface LinhaResponse {
  cl: number;
  lt: string;
  tp: string;
  ts: string;
}

export interface ParadaResponse {
  cp: number;
  np: string;
  ed: string;
  px: number;
  py: number;
}

export interface Veiculo {
  id: number;
  latitude: number;
  longitude: number;
  horario: string;
  deficiente: boolean;
}

// Actions

export interface LoadLinhasProps {
  fetchLinhasWithParadas: (searchTerm: string, limit: number) => Promise<any[]>;
  searchTerm: string;
  setLinhas: (linhas: any[]) => void;
  setFilteredLinhas: (linhas: any[]) => void;
  setError: (error: string | null) => void;
}

export interface LoadParadasProps {
  fetchParadas: (codigoLinha: number) => Promise<any[]>;
  codigoLinha: number;
  setParadas: (paradas: any[]) => void;
  setError: (error: string | null) => void;
}

export interface LoadVeiculosProps {
  fetchPosicoesVeiculos: (
    codigoLinha: number,
  ) => Promise<{ veiculos: any[]; hr: string }>;
  codigoLinha: number;
  setVeiculos: (veiculos: Veiculo[]) => void;
  setHorario: (horario: string) => void;
  setError: (error: string | null) => void;
}

export interface VeiculoResponse {
  p: any;
  cp: string;
  py: number;
  px: number;
  ta: string;
  a: boolean;
}
