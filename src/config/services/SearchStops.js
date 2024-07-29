import ConfigAPI from './ConfigAPI'; 

export const SearchStops = async (bus) => {
  try {
    const response = await ConfigAPI.get(`/Parada/BuscarParadasPorLinha?codigoLinha=${bus}`);
    //console.log('Resposta da API SearchStops:', response);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar paradas:', error);
  }

};

export default SearchStops;
