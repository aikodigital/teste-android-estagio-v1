import { fetchLinhas } from "./fetchLinhas";
import { fetchParadas } from "./fetchParadas";

interface LinhaResponse {
  cl: number;
  lt: string;
  tp: string;
  ts: string;
}

async function fetchLinhasWithParadas(
  termoBusca: string,
  limite: number = 5,
): Promise<LinhaResponse[] | null> {
  try {
    const linhas = await fetchLinhas(termoBusca);
    if (!linhas || linhas.length === 0) {
      console.log("Nenhuma linha encontrada.");
      return null;
    }

    // Limitando o número de linhas a serem processadas
    const linhasLimitadas = linhas.slice(0, limite);

    // Obtendo paradas para cada linha e filtrando as que têm paradas
    const linhasComParadas = await Promise.all(
      linhasLimitadas.map(async (linha) => {
        try {
          const paradas = await fetchParadas(linha.cl);
          if (paradas && paradas.length > 0) {
            return linha;
          }
        } catch (paradasError: any) {
          console.error(
            `Erro ao buscar paradas para a linha ${linha.cl}:`,
            paradasError.message,
          );
        }
        return null;
      }),
    );

    // Filtrando as linhas que possuem paradas
    const linhasFiltradas = linhasComParadas.filter(
      (linha): linha is LinhaResponse => linha !== null,
    );

    console.log("Linhas filtradas com paradas:", linhasFiltradas);
    return linhasFiltradas;
  } catch (error: any) {
    console.error("Erro ao buscar linhas com paradas:", error.message);
    return null;
  }
}

export { fetchLinhasWithParadas };
