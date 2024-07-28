import { useState, useEffect, useCallback } from "react";
import { postAuth } from "../../../../api/postAuth";
import { getBusPositions } from "../../../../api/getBusPositions";
import { BusData, Line } from "../../../types/types";

export const useBusPositions = () => {
  const [busPositions, setBusPositions] = useState<Line[]>([]);

  const fetchBusPositions = useCallback(async () => {
    try {
      await postAuth();
      const busData: BusData = await getBusPositions();
      setBusPositions(busData.l);
    } catch (error) {
      console.log(error);
    }
  }, []);

  useEffect(() => {
    fetchBusPositions();
    const intervalId = setInterval(fetchBusPositions, 5000);

    return () => clearInterval(intervalId);
  }, [fetchBusPositions]);

  return busPositions;
};
