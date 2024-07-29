import { useState, useEffect } from 'react';
import { postAuth } from '../../../../api/postAuth';
import { getBusPositions } from '../../../../api/getBusPositions';

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
        setLastUpdate(response.hr);
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, []);

  return lastUpdate;
};
