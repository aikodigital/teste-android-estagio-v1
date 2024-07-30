import axios, { AxiosResponse } from "axios";
// eslint-disable-next-line import/no-unresolved
import { API_BASE_URL, API_TOKEN_KEY } from "@env";

interface AuthResponse {
  cookies: string;
}

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: { "Content-Type": "application/json" },
});

async function autenticar(): Promise<AuthResponse | null> {
  try {
    const response: AxiosResponse<AuthResponse> = await api.post(
      `Login/Autenticar?token=${API_TOKEN_KEY}`,
    );
    if (response.status === 200 && response.data) {
      console.log("Autenticação bem-sucedida!");
      return response.data;
    } else {
      console.log("Erro na autenticação:", response.data);
      return null;
    }
  } catch (error: any) {
    console.error("Erro ao autenticar:", error.message);
    return null;
  }
}

export { autenticar, api };
