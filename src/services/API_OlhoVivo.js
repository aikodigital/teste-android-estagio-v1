import Config from 'react-native-config';
import ConfigAPI from './ConfigAPI'; 

export const authenticate = async () => {
  try {
    const response = await ConfigAPI.post(`Login/Autenticar?token=${Config.APY_TOKEN}`);

    if (response.data) {
      return true;
    } else {
      console.error('Falha na autenticação:', response.data);
      return false;
    }
  } catch (error) {
    console.error('Erro ao autenticar:', error.message);
    return false;
  }};

export default authenticate;
