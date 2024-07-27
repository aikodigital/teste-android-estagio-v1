import { useState, useEffect, useCallback } from 'react';
import axios from 'axios';

interface VehiclePosition {
  hr: string;
  l: Array<{
    c: string;
    cl: number;
    sl: number;
    lt0: string;
    lt1: string;
    qv: number;
    vs: Array<{
      p: number;
      a: boolean;
      ta: string;
      py: number;
      px: number;
    }>;
  }>;
}

interface ArrivalForecast {
  hr: string;
  p: {
    cp: number;
    np: string;
    py: number;
    px: number;
    l: Array<{
      c: string;
      cl: number;
      sl: number;
      lt0: string;
      lt1: string;
      qv: number;
      vs: Array<{
        p: string;
        t: string;
        a: boolean;
        ta: string;
        py: number;
        px: number;
      }>;
    }>;
  };
}

interface BusStop {
  id: number;
  name: string;
  location: {
    latitude: number;
    longitude: number;
  };
}

export interface NearestBusProps {
  number: string;
  linha: number;
  destination: string;
}

const token = '3ddea53566a1c1a55686d403261dfcca86ebefa835a5dbd3c2635fa7a05b8b52';

const useOlhoVivoAPI = () => {
  const [authenticated, setAuthenticated] = useState<boolean>(false);
  const [vehiclePositions, setVehiclePositions] = useState<VehiclePosition | null>(null);
  const [busStops, setBusStops] = useState<BusStop[]>([]);
  const [arrivalForecast, setArrivalForecast] = useState<ArrivalForecast | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<Error | null>(null);
  const [allBuses, setAllBuses] = useState<{ number: string; linha: number; destination: string }[]>([]);

  const authenticate = useCallback(async () => {
    try {
      const response = await axios.post('https://aiko-olhovivo-proxy.aikodigital.io/Login/Autenticar', null, {
        params: { token },
      });

      if (response.status === 200) {
        setAuthenticated(true);
        console.log('Autenticação bem-sucedida!');
      } else {
        setAuthenticated(false);
        console.error('Falha na autenticação: resposta da API não contém dados esperados.');
      }
    } catch (error) {
      setAuthenticated(false);
      console.error('Erro durante a autenticação:', error);
      setError(error as Error);
    }
  }, []);

  const fetchVehiclePositions = useCallback(async () => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const response = await axios.get<VehiclePosition>('https://aiko-olhovivo-proxy.aikodigital.io/Posicao', {
        headers: { 'Content-Type': 'application/json' },
      });
      setVehiclePositions(response.data);
      updateAllBuses(response.data);
    } catch (error) {
      console.error('Erro ao buscar posições dos veículos:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  }, [authenticated]);

  const fetchBusStops = useCallback(async (searchTerm: string) => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const response = await axios.get<BusStop[]>(`https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar`, {
        params: { termosBusca: searchTerm },
        headers: { 'Content-Type': 'application/json' },
      });
      setBusStops(response.data);
    } catch (error) {
      console.error('Erro ao buscar paradas:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  }, [authenticated]);

  const fetchArrivalForecast = useCallback(async (codigoParada: number | string, codigoLinha: number | string) => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const parada = typeof codigoParada === 'string' ? parseInt(codigoParada, 10) : codigoParada;
      const linha = typeof codigoLinha === 'string' ? parseInt(codigoLinha, 10) : codigoLinha;

      if (!Number.isInteger(parada) || !Number.isInteger(linha)) {
        throw new Error('Código da parada ou da linha inválido.');
      }

      const response = await axios.get<ArrivalForecast>('https://aiko-olhovivo-proxy.aikodigital.io/Previsao', {
        params: {
          codigoParada: parada,
          codigoLinha: linha
        },
        headers: {
          'Content-Type': 'application/json',
        },
      });

      setArrivalForecast(response.data);
    } catch (error) {
      console.error('Erro ao buscar previsão de chegada:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  }, [authenticated]);

  const updateAllBuses = (vehiclePositions: VehiclePosition) => {
    const buses = vehiclePositions.l.flatMap(locationData =>
      locationData.vs.map(vehicle => ({
        number: locationData.c,
        linha: locationData.cl,
        destination: locationData.lt1
      }))
    );
    setAllBuses(buses);
  };

  const findNearestBus = useCallback((location: { latitude: number; longitude: number }) => {
    if (!vehiclePositions) return null;

    const calculateDistance = (lat1: number, lon1: number, lat2: number, lon2: number) => {
      const R = 6371;
      const dLat = (lat2 - lat1) * (Math.PI / 180);
      const dLon = (lon2 - lon1) * (Math.PI / 180);
      const a =
        Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(lat1 * (Math.PI / 180)) * Math.cos(lat2 * (Math.PI / 180)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2);
      const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
      return R * c;
    };

    let nearestBus: NearestBusProps | null = null;
    let minDistance = Infinity;

    vehiclePositions.l.forEach(locationData => {
      locationData.vs.forEach(vehicle => {
        const distance = calculateDistance(location.latitude, location.longitude, vehicle.py, vehicle.px);
        if (distance < minDistance) {
          minDistance = distance;
          nearestBus = {
            number: locationData.c,
            destination: locationData.lt1,
            linha: locationData.cl
          };
        }
      });
    });

    return nearestBus;
  }, [vehiclePositions]);

  const fetchAllBuses = useCallback(async () => {
    await fetchVehiclePositions();
    // Call other API functions if needed
  }, [fetchVehiclePositions]);

  useEffect(() => {
    const initialize = async () => {
      await authenticate();
    };
    initialize();
  }, [authenticate]);

  return {
    vehiclePositions,
    busStops,
    arrivalForecast,
    loading,
    error,
    allBuses,
    fetchVehiclePositions,
    fetchBusStops,
    fetchArrivalForecast,
    findNearestBus,
    fetchAllBuses
  };
};

export default useOlhoVivoAPI;
