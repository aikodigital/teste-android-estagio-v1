export type Line = {
  cl: number;
  lt0: string;
  lt1: string;
  vs: Vehicle[];
  c: string;
};

export type Vehicle = {
  p: string;
  py: number;
  px: number;
  a: boolean;
};

export type Stop = {
  cp: number;
  np: string;
  ed: string;
  py: number;
  px: number;
};

export type Prediction = {
  c: string;
  vs: {
    p: string;
    t: string;
  }[];
};

export type RouteParams = {
  line: Line;
};

export type MapScreenProps = {
  route: {
    params: RouteParams;
  };
};
