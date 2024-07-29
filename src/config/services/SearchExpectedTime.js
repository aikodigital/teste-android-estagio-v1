import ConfigAPI from './ConfigAPI'; 

export const SearchExpectedTime = async (stop) => {
  try {
    const response = await ConfigAPI.get(`Previsao/Linha?codigoLinha=${stop}`);   
    //console.log('Resposta da API SearchExpectedTime:', response);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar previsão:', error);
  }

};

export default SearchExpectedTime;