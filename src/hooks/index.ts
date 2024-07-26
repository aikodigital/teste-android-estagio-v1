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
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<Error | null>(null);

const authenticate = async () => {
  try {
    const response = await axios.post('http://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar', null, {
      params: { token },
    });

    console.log('Resposta da autenticação:', response.data);

    if (response.data && response.data.authenticated) {  
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

  const fetchVehiclePositions = async () => {
    if (!authenticated) return;

    try {
      const response = await axios.get<VehiclePosition>('http://api.olhovivo.sptrans.com.br/v2.1/Posicao', {
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

  const fetchBusStops = async (searchTerm: string) => {
    if (!authenticated) return;

    try {
      const response = await axios.get<BusStop[]>(`http://api.olhovivo.sptrans.com.br/v2.1/Parada/Buscar?termosBusca=${searchTerm}`, {
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

  const fetchArrivalForecast = async (codigoParada: number, codigoLinha: number) => {
    if (!authenticated) return;

    try {
      const response = await axios.get<ArrivalForecast>(`http://api.olhovivo.sptrans.com.br/v2.1/Previsao`, {
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

  useEffect(() => {
    const initialize = async () => {
      setLoading(true);
      await authenticate();
      await fetchVehiclePositions();
    };
    initialize();
  }, []);

  return { vehiclePositions, busStops, arrivalForecast, loading, error, fetchBusStops, fetchArrivalForecast };
};

export default useOlhoVivoAPI;
