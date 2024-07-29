import React, { createContext, useContext, useState } from 'react';
import axios from 'axios';

const API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';
const API_KEY = 'b7c59cb9ae96c66937e6c61c274206493316c5c21105db7b64cbcbd0672cdbc4';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const authenticate = async () => {
    try {
      const response = await axios.post(`${API_URL}/Login/Autenticar`, null, {
        params: { token: API_KEY },
      });
      if (response.status === 200) {
        setIsAuthenticated(true);
        return true;
      } else {
        throw new Error('Falha na autenticação');
      }
    } catch (error) {
      console.error('Erro ao autenticar:', error);
      setIsAuthenticated(false);
      return false;
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, authenticate }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
