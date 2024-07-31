import axios from 'axios';

const API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';
const API_KEY = 'b7c59cb9ae96c66937e6c61c274206493316c5c21105db7b64cbcbd0672cdbc4';

const apiClient = axios.create({
  baseURL: API_URL,
});

// Função de autenticação
const authenticate = async () => {
  try {
    const response = await apiClient.post(`/Login/Autenticar?token=${API_KEY}`);
    if (response.status === 200) {
      return true;
    } else {
      throw new Error('Falha na autenticação');
    }
  } catch (error) {
    console.error('Erro ao autenticar:', error);
    throw error;
  }
};

// Função para buscar linhas de ônibus
export const fetchBusLines = async (searchTerm) => {
  try {
    await authenticate(); 
    const response = await apiClient.get('/Linha/Buscar', {
      params: { termosBusca: searchTerm, token: API_KEY },
    });
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar linhas:', error);
    throw error;
  }
};

// Função para buscar paradas
export const fetchStops = async (searchTerm) => {
  try {
    await authenticate(); 
    const response = await apiClient.get('/Parada/Buscar', {
      params: { termosBusca: searchTerm, token: API_KEY },
    });
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar paradas:', error);
    throw error;
  }
};

// Função para buscar previsão de chegada
export const fetchArrivalPrediction = async (codigoParada) => {
  try {
    await authenticate(); 
    const response = await apiClient.get(`/Previsao/Parada`, {
      params: { codigoParada, token: API_KEY },
    });
    if (response.data && response.data.p) {
      return response.data;
    } else {
      throw new Error('Dados da previsão não encontrados');
    }
  } catch (error) {
    console.error('Erro ao buscar previsão de chegada:', error);
    throw error;
  }
};
