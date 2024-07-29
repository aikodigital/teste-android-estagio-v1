// getBusArrivalPredictions.ts
import axios from "axios";

export async function getBusArrivalPredictions(codigoParada: number) {
  const response = await axios.get(
    `https://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=${codigoParada}`,
    {
      headers: {
        Authorization: `Bearer ${process.env.TOKEN}`,
      },
    }
  );
  return response.data;
}
