import ConfigAPI from './ConfigAPI'; 

export const SearchStops = async (bus) => {
  try {
    const endpoint = bus ? `/Parada/Buscar?termosBusca=${bus}` : '/Parada/Buscar?termosBusca=';
    const response = await ConfigAPI.get(endpoint);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar paradas:', error);
    return [];
  }
};

export default SearchStops;
