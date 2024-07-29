import axios from 'axios';

const API_URL = 'http://api.olhovivo.sptrans.com.br/v2.1';

const authenticate = async () => {
  const response = await axios.post(`${API_URL}/Login/Autenticar`, null, {
    params: { token: '9fdeec300d5efd3b8fa3d2ec79b7a59b0af6b3b44947f5594090ea66512530e8' }
  });
  return response.data;
};

const getVehicles = async () => {
  const response = await axios.get(`${API_URL}/Posicao`);
  return response.data;
};

export { authenticate, getVehicles };
