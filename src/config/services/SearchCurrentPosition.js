import ConfigAPI from './ConfigAPI'; 

export const SearchCurrentPosition = async (bus) => {
  try {
    const response = await ConfigAPI.get(`Posicao/Linha?codigoLinha=${bus}`);   
    //console.log('Resposta da API SearchCurrentPosition:', response);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar previsão:', error);
  }

};

export default SearchCurrentPosition;
