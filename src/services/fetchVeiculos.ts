import { AxiosResponse } from "axios";
import { autenticar, api } from "../api/apiAutenticar";

interface VeiculoResponse {
  a: boolean;
  p: string;
  px: number;
  py: number;
  ta: string;
}

interface PosicoesVeiculosResponse {
  hr: string;
  vs: VeiculoResponse[];
}

export async function fetchPosicoesVeiculos(
  codigoLinha: number,
): Promise<{ hr: string; veiculos: VeiculoResponse[] } | null> {
  try {
    const authResponse = await autenticar();
    if (!authResponse) {
      console.warn("Falha na autenticação.");
      return null;
    }

    const authToken = authResponse.cookies;
    const response: AxiosResponse<PosicoesVeiculosResponse> = await api.get(
      `/Posicao/Linha?codigoLinha=${codigoLinha}`,
      { headers: { Cookie: authToken } },
    );

    if (response.status === 200) {
      console.log("Posições dos veículos encontradas:", response.data);
      return { hr: response.data.hr, veiculos: response.data.vs };
    } else {
      console.log("Erro ao buscar posições dos veículos:", response.data);
      return null;
    }
  } catch (error: any) {
    console.error("Erro ao buscar posições dos veículos:", error.message);
    return null;
  }
}
