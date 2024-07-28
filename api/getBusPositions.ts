import axios from "axios";

export async function getBusPositions() {
  const response = await axios.get(
    "http://api.olhovivo.sptrans.com.br/v2.1/Posicao",
    {
      headers: {
        Authorization: `Barier ${process.env.TOKEN}`,
      },
    }
  );
  const data = response.data;
  return data;
}
