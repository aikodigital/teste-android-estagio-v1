import { Request, Response } from "express";
import axios from "axios";
import dotenv from "dotenv";
import { wrapper } from "axios-cookiejar-support";
import { CookieJar } from "tough-cookie";

dotenv.config();

const API_URL = process.env.PUBLIC_API_URL as string;
const TOKEN = process.env.PUBLIC_API_TOKE as string;

if (!API_URL || !TOKEN) {
  throw new Error("API_URL and TOKEN must be provided");
}

const jar = new CookieJar();
const client = wrapper(axios.create({ jar }));

async function authenticate() {
  try {
    const response = await client.post(`${API_URL}/Login/Autenticar`, null, {
      params: { token: TOKEN },
    });
    return response.data === true;
  } catch (error) {
    console.error("Authentication error:", error);
    throw new Error("Authentication failed");
  }
}

export const getPositionsController = async (req: Request, res: Response) => {
  const isAuthenticated = await authenticate();
  if (!isAuthenticated) {
    return res
      .status(401)
      .json({ success: false, message: "Authentication failed" });
  }

  try {
    const response = await client.get(`${API_URL}/Posicao`);
    res.json(response.data);
  } catch (error) {
    console.error("Error fetching positions:", error);
    res.status(500).json({ error: "Failed to get positions" });
  }
};

export const getLines = async (req: Request, res: Response) => {
  const isAuthenticated = await authenticate();
  if (!isAuthenticated) {
    return res
      .status(401)
      .json({ success: false, message: "Authentication failed" });
  }

  const code = req.query.termosBusca as string;

  if (!code) {
    return res.status(400).json({ error: "termosBusca parameter is required" });
  }

  try {
    const response = await client.get(
      `${API_URL}/Linha/Buscar?termosBusca=${code}`,
    );
    res.json(response.data);
  } catch (error) {
    console.error("Error fetching lines:", error);
    res.status(500).json({ error: "Failed to get lines" });
  }
};

export const getParadeByLine = async (req: Request, res: Response) => {
  const isAuthenticated = await authenticate();
  if (!isAuthenticated) {
    return res
      .status(401)
      .json({ success: false, message: "Authentication failed" });
  }

  const code = req.query.codigoLinha as string;

  if (!code) {
    return res.status(400).json({ error: "codigoLinha parameter is required" });
  }

  try {
    const response = await client.get(
      `${API_URL}/Parada/BuscarParadasPorLinha?codigoLinha=${code}`,
    );
    res.json(response.data);
  } catch (error) {
    console.error("Error fetching paradas:", error);
    res.status(500).json({ error: "Failed to get paradas" });
  }
};

export const getArrivalForecast = async (req: Request, res: Response) => {
  const isAuthenticated = await authenticate();
  if (!isAuthenticated) {
    return res
      .status(401)
      .json({ success: false, message: "Authentication failed" });
  }
  const codeLine = req.query.codigoLinha as string;
  const codeParada = req.query.codigoParada as string;

  if (!codeLine || !codeParada) {
    return res
      .status(400)
      .json({ error: "codigoLinha and codigoParada parameters are required" });
  }

  try {
    const response = await client.get(
      `${API_URL}/Previsao?codigoParada=${codeParada}&codigoLinha=${codeLine}`,
    );
    res.json(response.data);
  } catch (error) {
    console.error("Error fetching previsao:", error);
    res.status(500).json({ error: "Failed to get previsao" });
  }
};
