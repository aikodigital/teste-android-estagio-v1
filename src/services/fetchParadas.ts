import { AxiosResponse } from "axios";
import { autenticar, api } from "../api/apiAutenticar";

interface ParadaResponse {
  cp: number;
  np: string;
  ed: string;
  px: number; // Longitude
  py: number; // Latitude
}

async function fetchParadas(
  codigoLinha: number,
): Promise<ParadaResponse[] | null> {
  try {
    const authResponse = await autenticar();
    if (!authResponse) {
      console.warn("Falha na autenticação.");
      return null;
    }

    const authToken = authResponse.cookies;
    const response: AxiosResponse<ParadaResponse[]> = await api.get(
      `/Parada/BuscarParadasPorLinha?codigoLinha=${codigoLinha}`,
      { headers: { Cookie: authToken } },
    );

    if (response.status === 200) {
      if (Array.isArray(response.data) && response.data.length > 0) {
        console.log("Paradas encontradas:", response.data);
        return response.data;
      } else {
        return [];
      }
    } else {
      console.log("Erro ao buscar paradas:", response.data);
      return null;
    }
  } catch (error: any) {
    console.error("Erro ao buscar paradas:", error.message);
    return null;
  }
}

export { fetchParadas };
