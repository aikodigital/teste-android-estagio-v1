import { AxiosResponse } from "axios";

import { autenticar, api } from "../api/apiAutenticar";

interface LinhaResponse {
  cl: number;
  lt: string;
  tp: string;
  ts: string;
}

async function fetchLinhas(
  termoBusca: string,
): Promise<LinhaResponse[] | null> {
  try {
    const authResponse = await autenticar();
    if (!authResponse) {
      console.warn("Falha na autenticação.");
      return null;
    }

    const authToken = authResponse.cookies;
    const response: AxiosResponse<LinhaResponse[]> = await api.get(
      `/Linha/Buscar?termosBusca=${termoBusca}`,
      { headers: { Cookie: authToken } },
    );

    if (response.status === 200) {
      console.log("Linhas encontradas:", response.data);
      return response.data;
    } else {
      console.log("Erro ao buscar linhas:", response.data);
      return null;
    }
  } catch (error: any) {
    console.error("Erro ao buscar linhas:", error.message);
    return null;
  }
}

export { fetchLinhas };
