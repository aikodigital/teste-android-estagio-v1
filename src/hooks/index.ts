import { useState, useEffect } from 'react';
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

interface BusStop {
  cp: number;
  np: string;
  ed: string;
  py: number;
  px: number;
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

const token = '3ddea53566a1c1a55686d403261dfcca86ebefa835a5dbd3c2635fa7a05b8b52';

const useOlhoVivoAPI = () => {
  const [authenticated, setAuthenticated] = useState<boolean>(false);
  const [vehiclePositions, setVehiclePositions] = useState<VehiclePosition | null>(null);
  const [busStops, setBusStops] = useState<BusStop[]>([]);
  const [arrivalForecast, setArrivalForecast] = useState<ArrivalForecast | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<Error | null>(null);

  const authenticate = async () => {
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
  };

  // Função que trás a posição dos ônibus
  const fetchVehiclePositions = async () => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const response = await axios.get<VehiclePosition>('https://aiko-olhovivo-proxy.aikodigital.io/Posicao', {
        headers: { 'Content-Type': 'application/json' },
      });
      setVehiclePositions(response.data);
    } catch (error) {
      console.error('Erro ao buscar posições dos veículos:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  };

  // função que tras as paradas do ônibus

  const fetchBusStops = async (searchTerm: string) => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const response = await axios.get<BusStop[]>(`https://aiko-olhovivo-proxy.aikodigital.io/Parada/Buscar?termosBusca=${searchTerm}`, {
        headers: { 'Content-Type': 'application/json' },
      });
      setBusStops(response.data);
    } catch (error) {
      console.error('Erro ao buscar paradas:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  };

  // Função que tras a parada e a linha do onibus

  const fetchArrivalForecast = async (codigoParada: number, codigoLinha: number) => {
    if (!authenticated) return;

    setLoading(true);
    try {
      const response = await axios.get<ArrivalForecast>('https://aiko-olhovivo-proxy.aikodigital.io/Previsao', {
        params: { codigoParada, codigoLinha },
        headers: { 'Content-Type': 'application/json' },
      });
      setArrivalForecast(response.data);
    } catch (error) {
      console.error('Erro ao buscar previsão de chegada:', error);
      setError(error as Error);
    } finally {
      setLoading(false);
    }
  };

  const findNearestBus = (location: { latitude: number; longitude: number }) => {
    if (!vehiclePositions) return null;

    // Função para calcular a distância entre a localização da pessoa a o ônibus mais próximo
    
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
  
    let nearestBus = null;
    let minDistance = Infinity;
  
    vehiclePositions.l.forEach(locationData => {
      locationData.vs.forEach(vehicle => {
        const distance = calculateDistance(location.latitude, location.longitude, vehicle.py, vehicle.px);
        if (distance < minDistance) {
          minDistance = distance;
          nearestBus = {
            number: locationData.c,
            destination: locationData.lt1
          };
        }
      });
    });
  
    return nearestBus;
  };

  
  useEffect(() => {
    const initialize = async () => {
      await authenticate();
    };
    initialize();
  }, []);

  return { vehiclePositions, busStops, arrivalForecast, loading, error, fetchVehiclePositions, fetchBusStops, fetchArrivalForecast, findNearestBus };
};

export default useOlhoVivoAPI;
