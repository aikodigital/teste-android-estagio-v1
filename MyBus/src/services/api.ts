const apiUrl = 'http://api.olhovivo.sptrans.com.br/v2.1';
const apiKey = process.env.API;

const authenticate = async () => {
  try {
    const response = await fetch(`${apiUrl}/Login/Autenticar?token=${apiKey}`, {
      method: 'POST',
    });

    if (!response.ok) {
      throw new Error('Erro ao autenticar na API SPTrans');
    }
    console.log('Autenticado com sucesso');
    return true;
  } catch (error) {
    console.error('Erro ao autenticar:', error);
    return false;
  }
};

export const fetchPrevisao = async (codigoParada: number, codigoLinha: number) => {
  const response = await fetch(`http://api.olhovivo.sptrans.com.br/v2.1/Previsao?codigoParada=${codigoParada}&codigoLinha=${codigoLinha}`);
  const data = await response.json();
  return data;
};

export const fetchLinesData = async () => {
  try {
    const isAuthenticated = await authenticate();
    if (!isAuthenticated) throw new Error('Autenticação falhou');

    const response = await fetch(`${apiUrl}/Posicao`);
    if (!response.ok) {
      const errorResponse = await response.text();
      console.error('Resposta da API:', errorResponse);
      throw new Error('Erro ao buscar dados de posição');
    }

    const data = await response.json();
    return data;

  } catch (error) {
    console.error('Erro ao buscar dados da API:', error);
    return null;
  }
};

export const fetchStopsByLine = async (codigoLinha: number) => {
  try {
    const isAuthenticated = await authenticate();
    if (!isAuthenticated) throw new Error('Autenticação falhou');

    const response = await fetch(`${apiUrl}/Parada/BuscarParadasPorLinha?codigoLinha=${codigoLinha}`);
    if (!response.ok) {
      const errorResponse = await response.text();
      console.error('Resposta da API para paradas:', errorResponse);
      throw new Error('Erro ao buscar paradas');
    }

    const data = await response.json();
    console.log('Paradas obtidas com sucesso:', data);
    return data;
  } catch (error) {
    console.error('Erro ao buscar paradas da API:', error);
    return null;
  }
};
