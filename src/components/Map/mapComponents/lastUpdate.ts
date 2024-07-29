import { useState, useEffect } from "react";
import { postAuth } from "../../../../api/postAuth";
import { getBusPositions } from "../../../../api/getBusPositions";

interface BusPositionsResponse {
  hr: string[];
}

export const useUpdateHour = () => {
  const [lastUpdate, setLastUpdate] = useState<string[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        await postAuth();
        const response: BusPositionsResponse = await getBusPositions();
        setLastUpdate(response.hr); // Define lastUpdate com a propriedade hr
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []); // Array de dependências vazio para executar uma vez ao montar

  return lastUpdate;
};
