import axios from 'axios';

const API_BASE_URL = 'http://api.olhovivo.sptrans.com.br/v2.1';
const API_TOKEN = '903ce7789c0e18966229c1552921a6d6dea439adef8739c7ad3490adc9798151'; 

const api = axios.create({
  baseURL: API_BASE_URL,
});

export const authenticate = () => api.post(`/Login/Autenticar?token=${API_TOKEN}`);
export const getVehiclePositions = () => api.get('/Posicao');
export const getLines = () => api.get('/Linha');
export const getStops = () => api.get('/Parada');
export const getArrivalPredictions = (stopId) => api.get(`/Previsao/Parada?codigoParada=${stopId}`);

export default api;
