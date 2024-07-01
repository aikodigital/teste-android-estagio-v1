export interface Corredor {
  cc: string;
  nc: string;
}

export type Parada = {
  cp: number;
  np: string;
  py: number;
  px: number;
};

export type PrevisaoChegada = {
  hr: string;
  p: {
    cp: number;
    np: string;
    py: number;
    px: number;
    l: {
      c: string;
      cl: number;
      sl: number;
      lt0: string;
      lt1: string;
      qv: number;
      vs: {
        p: string;
        t: string;
        a: boolean;
        ta: string;
        py: number;
        px: number;
      }[];
    }[];
  };
};

export type Posicao = {
  hr: string;
  vs: {
    p: string;
    a: boolean;
    ta: string;
    py: number;
    px: number;
  }[];
};
