// getBusArrivalPrediction.ts
import axios from "axios";

export async function getBusArrivalPrediction(codigoParada: number) {
  try {
    const response = await axios.get(
      `https://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=${codigoParada}`,
      {
        headers: {
          Authorization: `Bearer ${process.env.TOKEN}`,
        },
      }
    );
    const data = response.data;
    console.log("Dados retornados pela API:", data); // Verifique a estrutura completa dos dados
    return data;
  } catch (error) {
    console.error("Erro ao obter a previsão de chegada:", error);
    throw error;
  }
}
