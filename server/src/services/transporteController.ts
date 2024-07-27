import { Request, Response } from 'express';
import axios from 'axios';
import dotenv from 'dotenv';
import { wrapper } from 'axios-cookiejar-support';
import { CookieJar } from 'tough-cookie';
import  termosBuscaLinha from "./../loaders/getuserLine"

dotenv.config();

const API_URL = process.env.PUBLIC_API_URL as string;
const TOKEN = process.env.PUBLIC_API_TOKE as string;

if (!API_URL || !TOKEN) {
  throw new Error('API_URL and TOKEN must be provided');
}

const jar = new CookieJar();
const client = wrapper(axios.create({ jar }));

export const autenticationController = async (req: Request, res: Response) => {
  try {
    const response = await client.post(`${API_URL}/Login/Autenticar`, null, {
      params: { token: TOKEN }
    });
    if (response.data === true) {
      res.json({ success: true, message: 'Authenticated successfully' });
    } else {
      res.status(401).json({ success: false, message: 'Authentication failed' });
    }
  } catch (error) {
    console.error('Authentication error:', error);
    res.status(500).json({ error: 'Authentication failed' });
  }
};

export const getPositionsController = async (req: Request, res: Response) => {
  try {
    const response = await client.get(`${API_URL}/Posicao`)
    res.json(response.data);
  } catch (error) {
    console.error('Error fetching positions:', error);
    res.status(500).json({ error: 'Failed to get positions' });
  }
};


export const getLines = async (req: Request, res: Response) => {
    
    const code = termosBuscaLinha(); 
    
    if (!code) {
      return res.status(400).json({ error: 'codeLinha parameter is required' });
    }

    try {
      const response = await client.get(`${API_URL}Linha/Buscar?termosBusca=${code}`, {
    });
    
    res.json(response.data);
    } catch (error) {
      console.error('Error fetching lines:', error);
      res.status(500).json({ error: 'Failed to get lines' });
    }
  };

export const getParadeByLine = async (req: Request, res: Response) => {

    const code = "1273";

    try{
        const response = await client.get(`${API_URL}/Parada/BuscarParadasPorLinha?codigoLinha=${code}`);
        res.json(response.data);
    }catch(error){
        console.error('Error fetching paradas:', error);
        res.status(500).json({ error: 'Failed to get paradas'});
    }
}


export const getArrivalForecast = async (req: Request, res: Response) => {

    const codeLine = "1273";
    const codeParada = "1";

    try{
        const response = await client.get(`${API_URL}/Previsao?codigoParada=${codeParada}&codigoLinha=${codeLine}`);
        res.json(response.data);
    }catch(error){
        console.error('Error fetching previsao:', error);
        res.status(500).json({ error: 'Failed to get previsao'});
    }
}