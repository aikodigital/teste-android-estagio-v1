const BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1";

export const authenticate = async (token) => {
  try {
    const response = await fetch(
      `${BASE_URL}/Login/Autenticar?token=${token}`,
      {
        method: "POST",
      }
    );
    const result = await response.json();
    console.log("Authentication result:", result);
    return result;
  } catch (error) {
    console.error("Error authenticating:", error);
    throw error;
  }
};

export const fetchBusLines = async (token, lineCode) => {
  try {
    console.log("Token:", token);
    console.log("Line Code:", lineCode);

    const authResult = await authenticate(token);
    console.log("Authentication result:", authResult);

    const response = await fetch(
      `${BASE_URL}/Linha/Buscar?termosBusca=${lineCode}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    console.log("Response status:", response.status);
    console.log("Response headers:", response.headers);

    if (!response.ok) {
      throw new Error("Failed to fetch bus lines");
    }

    const data = await response.json();
    console.log("Fetched bus lines data:", data);

    if (!Array.isArray(data)) {
      throw new Error("Unexpected data format");
    }

    data.forEach((item, index) => {
      console.log(`Bus Line ${index} data:`, item);
    });

    return data;
  } catch (error) {
    console.error("Error fetching bus lines:", error);
    throw error;
  }
};

export const fetchVehiclePositions = async (token) => {
  try {
    const authResult = await authenticate(token);
    console.log("Authentication result:", authResult);

    const response = await fetch(`${BASE_URL}/Posicao`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Response status:", response.status);
    console.log("Response headers:", response.headers);

    if (!response.ok) {
      throw new Error("Failed to fetch vehicle positions");
    }

    const data = await response.json();
    console.log("Fetched vehicle positions data:", data);

    return data;
  } catch (error) {
    console.error("Error fetching vehicle positions:", error);
    throw error;
  }
};

export const fetchStops = async (token) => {
  try {
    const authResult = await authenticate(token);
    console.log("Authentication result:", authResult);

    const response = await fetch(`${BASE_URL}/Parada/Buscar?termosBusca=`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Response status:", response.status);
    console.log("Response headers:", response.headers);

    if (!response.ok) {
      throw new Error("Failed to fetch stops");
    }

    const data = await response.json();
    console.log("Fetched stops data:", data);

    return data;
  } catch (error) {
    console.error("Error fetching stops:", error);
    throw error;
  }
};

export const fetchArrivalForecast = async (token, lineCode) => {
  try {
    console.log("Token:", token);
    console.log("Line Code:", lineCode);

    const authResult = await authenticate(token);
    console.log("Authentication result:", authResult);

    const response = await fetch(
      `${BASE_URL}/Previsao/Linha?codigoLinha=${lineCode}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    console.log("Response status:", response.status);

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    console.log("Fetched arrival forecast data:", data);

    return data;
  } catch (error) {
    console.error("Error fetching arrival forecasts:", error);
    throw error;
  }
};
