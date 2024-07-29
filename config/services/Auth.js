import ConfigAPI from './ConfigAPI'; 

const token = '07a3c50f3f6fd8bbe03a085d805dbc4eda5c9b5a635921f14e722eae35e7445b';

export const authenticate = async () => {
  try {
    const response = await ConfigAPI.post(`Login/Autenticar?token=${token}`);
    //console.log('Resposta da API Auth:', response);

    if (response.data) {
      console.log('Autenticado com sucesso!');
      return true;
    } else {
      console.error('Falha na autenticação. Dados da resposta:', response.data);
      return false;
    }
  } catch (error) {
    console.error('Erro ao tentar autenticar:', error.message);
    return false;
  
  }
};

export default authenticate;
