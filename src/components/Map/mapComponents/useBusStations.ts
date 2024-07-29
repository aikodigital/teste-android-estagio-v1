// useBusStations.ts
import { useState, useEffect, useCallback } from "react";
import { postAuth } from "../../../../api/postAuth";
import { getBusStations } from "../../../../api/getBusStations"; // Certifique-se de que você tem esta função
import { BusStation } from "../../../types/types";

export const useBusStations = () => {
  const [busStations, setBusStations] = useState<BusStation[]>([]);

  const fetchBusStations = useCallback(async () => {
    try {
      await postAuth();
      const stationsData = await getBusStations();
      setBusStations(stationsData); // Ajuste conforme a estrutura da resposta
    } catch (error) {
      console.log(error);
    }
  }, []);

  useEffect(() => {
    fetchBusStations();
    const intervalId = setInterval(fetchBusStations, 10000);

    return () => clearInterval(intervalId);
  }, [fetchBusStations]);

  return busStations;
};
