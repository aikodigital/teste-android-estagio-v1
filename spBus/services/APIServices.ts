import axios from 'axios';

const API_BASE = 'http://api.olhovivo.sptrans.com.br/v2.1';
const token = '26855297c98341fa0364a4546a7962179575481bd16ebd4aa01dba3f7b131c4c';

let isAuthenticated = false;

export const authenticate = async (): Promise<void> => {
  try {
    const response = await axios.post(`${API_BASE}/Login/Autenticar?token=${token}`);
    
    if (response.data) {
      console.log('Autenticado !');
      isAuthenticated = true;
    }
  } catch (error) {
    console.log("Falha na autenticação", error);
  }
};

export const getBusPositions = async () => {
  if (!isAuthenticated) await authenticate();
  
  try {
    const response = await axios.get(`${API_BASE}/Posicao`);
    return response.data;
  } catch (error) {
    console.log("Falha ao tentar buscar posição dos ônibus", error);
    return null;
  }
};

export const getBusStops = async (search: string): Promise<any> => {
  if (!isAuthenticated) await authenticate();

  try {
    const response = await axios.get(`${API_BASE}/Parada/Buscar?termosBusca=${search}`);
    return response.data;
  } catch (error) {
    console.log("Falha ao tentar buscar as paradas de ônibus", error);
    return null;
  }
};

export const getBusLines = async (search: string): Promise<any> => {
  if (!isAuthenticated) await authenticate();

  try {
    const response = await axios.get(`${API_BASE}/Linha/Buscar?termosBusca=${search}`);
    return response.data;
  } catch (error) {
    console.log("Falha ao tentar buscar as linhas de ônibus", error);
    return null;
  }
};

export const getArrivalPrediction = async (busStopCode: number): Promise<any> => {
  if (!isAuthenticated) await authenticate();

  try {
    const response = await axios.get(`${API_BASE}/Previsao/Parada?codigoParada=${busStopCode}`);
    return response.data;
  } catch (error) {
    console.log("Falha ao tentar buscar a previsão de chegada", error);
    return null;
  }
};

