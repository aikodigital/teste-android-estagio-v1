import ConfigAPI from './ConfigAPI'; 

export const SearchBusLine = async (bus) => {
  try {
    const response = await ConfigAPI.get(`Linha/Buscar?termosBusca=${bus}`);
    //console.log('Resposta da API SearchBusLine:', response);

    return response.data;
  } catch (error) {
    console.error('Erro ao buscar linha de ônibus:', error);
  }

};

export default SearchBusLine;
