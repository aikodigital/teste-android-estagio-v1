/* eslint-disable import/order */
/* eslint-disable prettier/prettier */
import axios from 'axios';
import { OLHO_VIVO_BASE_URL, OLHO_VIVO_API_KEY } from '@env';
import { Linha, Parada, Posicao, PrevisaoParadaResponse } from '../types/types';

const api = axios.create({
  baseURL: OLHO_VIVO_BASE_URL,
});

export const login = async (): Promise<void> => {
  const response = await api.post('/Login/Autenticar', null, {
    params: { token: OLHO_VIVO_API_KEY },
  });
  if (!response.data) {
    throw new Error('Autenticação falhou');
  }
};

export const getLinhas = async (): Promise<Linha[]> => {
  await login();

  const response = await api.get('/Linha/Buscar');
  return response.data;
};
export const getPosicoes = async (): Promise<Linha[]> => {
  await login();

  const response = await api.get('/Posicao');
  return response.data.l;
};
export const getParadasByLine = async (linha: number): Promise<Parada[]> => {
  await login();

  const response = await api.get(`/BuscarParadasPorLinha?codigoLinha=${linha}`);
  return response.data;
};
export const getAllParadas = async (): Promise<Parada[]> => {
  await login();

  const response = await api.get(`/Parada/Buscar?termosBusca=`);
  return response.data;
};
export const getPrevisaoChegada = async (
  paradaId: number
): Promise<PrevisaoParadaResponse> => {
  await login();

  const response = await api.get(`/Previsao/Parada?codigoParada=${paradaId}`);
  return response.data;
};
