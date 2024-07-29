import { useState, useEffect } from "react";
import { getBusArrivalPredictions } from "../../../../api/getBusArrivalPrediction";
import { ArrivalPredictions } from "../../../types/types";
import { postAuth } from "../../../../api/postAuth";

export const useBusArrivalPredictions = (codigoParada: number) => {
  const [arrivalPredictions, setArrivalPredictions] =
    useState<ArrivalPredictions | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    const fetchBusArrivalPredictions = async () => {
      try {
        await postAuth();
        const data = await getBusArrivalPredictions(codigoParada);
        setArrivalPredictions(data);
      } catch (err) {
        setError(err as Error);
      } finally {
        setLoading(false);
      }
    };

    fetchBusArrivalPredictions();
  }, [codigoParada]);

  return { arrivalPredictions, loading, error };
};
