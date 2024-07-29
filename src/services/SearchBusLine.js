import ConfigAPI from './ConfigAPI'; 

export const SearchBusLine = async (bus) => {
  try {
    const response = await ConfigAPI.get(`Linha/Buscar?termosBusca=${bus}`);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar linha de ônibus:', error);
  }

};

export default SearchBusLine;
