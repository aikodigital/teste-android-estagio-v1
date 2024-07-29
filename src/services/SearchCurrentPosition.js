import ConfigAPI from './ConfigAPI'; 

export const SearchCurrentPosition = async (bus) => {
  try {
    const endpoint = bus ? `/Posicao/Linha?codigoLinha=${bus}` : '/Posicao';
    const response = await ConfigAPI.get(endpoint);

    console.log(response.data.l)
    return response.data.l;
  } catch (error) {
    console.error('Erro ao buscar previsão:', error);
    return [];
  }
};

export default SearchCurrentPosition;
