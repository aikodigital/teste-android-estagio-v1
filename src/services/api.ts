import axios from 'axios';

const base_url = 'http://api.olhovivo.sptrans.com.br/v2.1';

const aut_token =
  '9c3e6a50f978b9035f5e5962e559bfe5cab9fdb526a308cf8239c31b1b55c4bf';

const api = axios.create({
  baseURL: base_url,
});

let isAuthenticated = false;

export interface Lines {
  cl: number;
  lt: string;
  tl: number;
  sl: number;
  tp: string;
  ts: string;
}

export interface LinePrediction {
  hr: string;
  ps: Array<{
    cp: number;
    np: string;
    py: number;
    px: number;
    vs: Array<{
      p: number;
      t: string;
      a: boolean;
      ta: string;
      py: number;
      px: number;
    }>;
  }>;
}

export const authenticate = async () => {
  try {
    const response = await api.post('/Login/Autenticar', null, {
      params: {token: aut_token},
    });
    isAuthenticated = response.data;
    return isAuthenticated;
  } catch (error) {
    console.error('Erro ao autenticar', error);
    return false;
  }
};

export const getLines = async (searchTerm: string): Promise<Lines[]> => {
  if (!isAuthenticated) {
    await authenticate();
  }
  try {
    const response = await api.get('/Linha/Buscar', {
      params: {termosBusca: searchTerm},
    });
    return response.data as Lines[];
  } catch (error) {
    console.error('Erro ao buscar linhas', error);
    return [];
  }
};

export const getLinePrediction = async (
  lineId: number,
): Promise<LinePrediction | null> => {
  if (!isAuthenticated) {
    await authenticate();
  }
  try {
    const response = await api.get(`/Previsao/Linha?codigoLinha=${lineId}`);
    return response.data as LinePrediction;
  } catch (error) {
    console.error('Erro ao buscar paradas e previsões de chegada', error);
    return null;
  }
};

export default api;
