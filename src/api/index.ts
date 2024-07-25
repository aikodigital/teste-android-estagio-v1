import axios from 'axios';

const API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';
const API_KEY = '5f0a0959542a2e34e6d593fa860cb720d6a65261c9c186be3e6909c3f652a546';

const api = axios.create({
  baseURL: API_URL,
});

const login = async () => {

  try {
    const response = await api.post('/Login/Autenticar', null, {
      params: {
        token: API_KEY,
      },
    });
    return response.data;

  } catch (error) {
    console.error('Erro ao autenticar:', error);
    throw error;
  }
};

// Functions --> 

const buscarParadas = async () => {
  try {
    await login();
    const response = await api.get('/Parada/Buscar');

    return response.data;
  } catch (error) {
        console.error('Erro ao buscar paradas:', error);
    throw error;
  }
};

export { buscarParadas, login, api, API_KEY };