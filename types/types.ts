//Posicao
export type PosicaoDosVeiculos = {
  hr: string;
  l: LinhaParaPosicao<PosicaoVeiculo>[];
};

export type LinhaParaPosicao<T> = {
  c: string;
  cl: number;
  sl: number;
  lt0: string;
  lt1: string;
  qv: number;
  vs: T[];
};

export type Trajeto = { de: string | null; para: string | null };

export type Posicao = {
  py: number;
  px: number;
};

export type PosicaoVeiculo = {
  p: number | string;
  a: boolean;
  ta: string;
} & Posicao;

//Linha
export type Linha = {
  cl: number;
  lc: boolean;
  lt: string;
  sl: number;
  tl: number;
  tp: string;
  ts: string;
};

//Parada
export type Parada = {
  cp: number;
  np: string;
  ed: string;
} & Posicao;

//PrevisaoChegada
export type PrevisaoChegada = {
  hr: string;
  p: ParadaPrevisaoChegada;
};

export type ParadaPrevisaoChegada = {
  cp: number;
  np: string;
  l: LinhaParaPosicao<{ t: string } & PosicaoVeiculo & Posicao>[];
} & Posicao;

//mapa
export type MapRegion = {
  latitude: number;
  longitude: number;
  latitudeDelta: number;
  longitudeDelta: number;
};
