import axios from "axios";

export async function getBusStations() {
  const response = await axios.get(
    `https://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=`,
    {
      headers: {
        Authorization: `Barier ${process.env.TOKEN}`,
      },
    }
  );
  const data = response.data;
  return data;
}
