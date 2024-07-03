import axios from "axios";

const baseURL = "https://aiko-olhovivo-proxy.aikodigital.io";

// Função para autenticar com token
export const authenticate = async (token) => {
  try {
    const response = await axios.post(
      `${baseURL}/Login/Autenticar?token=${token}`
    );
    return response.data; // Retorna os dados de autenticação
  } catch (error) {
    console.error("Erro na autenticação:", error.message);
    throw error;
  }
};

// Função para obter posições dos veículos
export const getVehiclesPositions = async (token) => {
  try {
    const authSuccess = await authenticate(token);

    if (authSuccess) {
      const response = await axios.get(`${baseURL}/Posicao`);
      return response.data; // Retorna as posições dos veículos
    } else {
      throw new Error("Falha na autenticação");
    }
  } catch (error) {
    console.error("Erro ao buscar posições dos veículos:", error.message);
    throw error;
  }
};

// Função para buscar linhas de ônibus
export const getBusLines = async (token, searchTerm = "") => {
  try {
    const response = await axios.get(`${baseURL}/Linha/Buscar`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        termosBusca: searchTerm,
      },
    });

    // Retornar os detalhes completos das linhas de ônibus
    return response.data.map((line) => ({
      cl: line.cl, // Código da linha
      lt: line.lt, // Letreiro da linha
      tp: line.tp, // Tipo de veículo
      lo: line.lo, // Operadora
      ts: line.ts, // Sentido de operação
      // Adicione mais campos conforme necessário
    }));
  } catch (error) {
    // Tratamento de erro ao buscar linhas de ônibus
    console.error("Erro na função getBusLines:", error.message);
    throw error;
  }
};

// Função para buscar paradas de ônibus
export const getBusStops = async (token) => {
  try {
    const authSuccess = await authenticate(token);

    if (authSuccess) {
      const response = await axios.get(`${baseURL}/Parada/Buscar?termosBusca=`);
      if (Array.isArray(response.data)) {
        return response.data; // Retorna as paradas de ônibus
      } else {
        throw new Error("Dados da API inválidos: não é um array");
      }
    } else {
      throw new Error("Falha na autenticação");
    }
  } catch (error) {
    console.error("Erro ao buscar paradas de ônibus:", error.message);
    throw error;
  }
};

// Função para obter previsões de chegada por parada e linha
export const getArrivalPredictions = async (token, stopId, lineId) => {
  try {
    const response = await fetch(
      `${baseURL}/Previsao/Parada?codigoParada=${stopId}&codigoLinha=${lineId}`,
      {
        method: "GET",
        headers: {
          Accept: "application/json",
          Cookie: `APIOlhoVivoToken=${token}`,
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data.p; // Retorna as previsões de chegada
  } catch (error) {
    console.error("Erro ao buscar previsões de chegada:", error);
    throw error;
  }
};

// Função para exibir detalhes da linha em um modal
const showLineDetailsModal = async (token, line) => {
  try {
    // Obter o caminho da rota da linha
    const routePath = await getRoutePath(token, line.cl);
    if (routePath && routePath.length > 0) {
      // Formatar coordenadas para exibição
      const coordinates = routePath.map((point) => ({
        latitude: point.py || 0,
        longitude: point.px || 0,
      }));
      setSelectedLineDetails({
        ...line,
        route: coordinates,
      });
      setRouteCoordinates(coordinates);
      setShowLineDetails(true); // Exibir modal de detalhes da linha
    } else {
      console.log(`Não foram encontradas coordenadas para a linha ${line.cl}`);
    }
  } catch (error) {
    console.error("Erro ao buscar detalhes da linha:", error);
  }
};

// Função para obter a frota por linha
export const getFleetByLine = async (token, lineId) => {
  try {
    const response = await fetch(`${baseURL}/fleet/${lineId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    const data = await response.json();
    return data; // Retorna os dados da frota por linha
  } catch (error) {
    console.error("Erro ao buscar frota por linha:", error);
    throw error;
  }
};

// Função para buscar linhas de ônibus
export const searchBusLines = async (token, searchTerm) => {
  try {
    const response = await axios.get(`${baseURL}/Linha/Buscar`, {
      params: {
        termosBusca: searchTerm,
      },
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response.data); // Exibir resultado da busca no console
    return response.data; // Retorna resultados da busca de linhas de ônibus
  } catch (error) {
    throw error;
  }
};

// Função para buscar paradas de ônibus
export const searchBusStops = async (token, searchTerm) => {
  try {
    const response = await axios.get(`${baseURL}/Parada/Buscar?termosBusca=${searchTerm}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
      },
    });
    return response.data; // Retorna resultados da busca de paradas de ônibus
  } catch (error) {
    console.error('Erro ao buscar paradas:', error);
    throw error;
  }
};

// Função para obter previsões de chegada por parada e linha
const getArrivalPredictionsByStopAndLine = async (codigoParada, codigoLinha) => {
  try {
    const response = await axios.get(`/Previsao?codigoParada=${codigoParada}&codigoLinha=${codigoLinha}`);
    return response.data; // Retorna as previsões de chegada por parada e linha
  } catch (error) {
    throw error;
  }
};

// Função para obter previsões de chegada por linha
export const getArrivalPredictionsByLine = async (token, codigoLinha) => {
  try {
    const response = await axios.get(
      `${baseURL}/Previsao/Linha?codigoLinha=${codigoLinha}`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
    return response.data.ps.map(prediction => ({
      linha: prediction.c,
      destino: prediction.lt0,
      origem: prediction.lt1,
      previsao: prediction.vs[0]?.t, // Considerando apenas a primeira previsão
      acessivel: prediction.vs[0]?.a,
      horarioAtualizacao: prediction.vs[0]?.ta,
      latitude: prediction.vs[0]?.py,
      longitude: prediction.vs[0]?.px
    })); // Retorna as previsões de chegada por linha
  } catch (error) {
    console.error('Erro ao buscar previsões de chegada por linha:', error);
    throw error;
  }
};

// Função para obter previsões de chegada por parada
export const getArrivalPredictionsByStop = async (token, codigoParada, codigoLinha) => {
  try {
    const authSuccess = await authenticate(token);

    if (authSuccess) {
      const response = await axios.get(
        `${baseURL}/Previsao?codigoParada=${codigoParada}&codigoLinha=${codigoLinha}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const { hr, p } = response.data;
      const arrivalPredictions = [];

      p.l.forEach((linha) => {
        linha.vs.forEach((veiculo) => {
          arrivalPredictions.push({
            linha: linha.c,
            destino: linha.lt0,
            origem: linha.lt1,
            previsao: veiculo.t,
            acessivel: veiculo.a,
            horarioAtualizacao: veiculo.ta,
            latitude: veiculo.py,
            longitude: veiculo.px,
          });
        });
      });

      return arrivalPredictions; // Retorna as previsões de chegada por parada
    } else {
      throw new Error('Falha na autenticação');
    }
  } catch (error) {
    console.error('Erro ao buscar previsões de chegada por parada:', error.message);
    throw error;
  }
};

// Função para lidar com pressionar de uma parada
const handleStopPress = async (stop) => {
  setSelectedStop(stop); // Define a parada selecionada

  try {
    const stopId = stop.cp;
    const response = await fetch(`${baseURL}/Previsao/Parada?codigoParada=${stopId}`);
    const result = await response.json();

    if (result && result.p && result.p.l) {
      const predictions = result.p.l.map(line => ({
        c: line.c,
        vs: line.vs.map(vehicle => ({
          p: vehicle.p,
          t: vehicle.t,
          a: vehicle.a,
          ta: vehicle.ta,
          py: vehicle.py,
          px: vehicle.px
        }))
      }));

      if (predictions.length > 0) {
        setArrivalPredictions(predictions);
        setIsModalVisible(true); // Exibir modal de previsões de chegada
        sendArrivalPredictionNotification(predictions);
      } else {
        setArrivalPredictions([]);
        setIsModalVisible(false); // Ocultar modal se não houver previsões
        console.log(`Não foram encontradas previsões para a parada ${stopId}`);
      }
    } else {
      console.log(`Não foram encontradas previsões para a parada ${stopId}`);
      setArrivalPredictions([]);
      setIsModalVisible(false); // Ocultar modal se não houver previsões
    }
  } catch (error) {
    console.error("Erro ao buscar previsões de chegada:", error.message);
    setArrivalPredictions([]);
    setIsModalVisible(false); // Ocultar modal em caso de erro
  }
};