import axios from 'axios';

const API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';
const API_KEY = 'b7c59cb9ae96c66937e6c61c274206493316c5c21105db7b64cbcbd0672cdbc4';

export const authenticate = async () => {
  try {
    const response = await axios.post(`${API_URL}/Login/Autenticar`, null, {
      params: { token: API_KEY },
    });

    if (response.data) {
      return true;
    }
  } catch (error) {
    console.error('Erro na autenticação:', error);
  }
  
  return false;
};
