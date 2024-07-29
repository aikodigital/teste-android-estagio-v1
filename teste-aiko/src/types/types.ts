/* eslint-disable prettier/prettier */
export type Veiculo = {
  p: number;
  t: string;
  a: boolean;
  ta: string;
  py: number;
  px: number;
};

export type Linha = {
  c: string;
  cl: number;
  sl: number;
  lt0: string;
  lt1: string;
  qv: number;
  vs: Veiculo[];
};

export type Posicao = {
  hr: string;
  l: Linha[];
};

export type Parada = {
  cp: number;
  np: string;
  ed: string,
  py: number;
  px: number;
  l: Linha[];
};
export type ParadaUnity = {
  cp: number;
  np: string;
  ed: string,
  py: number;
  px: number;
  l: number;
};

export type PrevisaoParadaResponse = {
  hr: string 
  p: Parada 
}
