export interface LinhaResponse {
  cl: number;
  lt: string;
  tp: string;
  ts: string;
}

export interface PickerLinhasProps {
  linhas: LinhaResponse[];
  onSelectLinha: (codigoLinha: number) => void;
  disabled: boolean;
  error?: string | null;
}
