  // AppContext.js
  import React, { createContext, useState } from 'react';
import axios from 'axios'
  // Crie o contexto
  export const AppContext = createContext();

  // Crie um provedor de contexto
  export const AppProvider = ({ children }) => {
    const [state, setState] = useState("oi");
    const [vehicles, setVehicles] = useState([]);
    const [paradas,setParadas]= useState([])

    const API_BASE_URL = "https://api.olhovivo.sptrans.com.br/v2.1";
    const API_KEY = "c8927e9a1d4eb1cb2329fbfd9ca9740bf80fa72e0d28f06cb73e47045841270b";
    





    return (
      <AppContext.Provider value={{ state, setState ,vehicles, setVehicles,paradas,setParadas,API_BASE_URL,API_KEY }}>
        {children}
      </AppContext.Provider>
    );
  };
