import React from 'react';
import { useEffect, useState } from 'react';
import { View, Text, Image } from 'react-native';

// Bibliotecas nativas para rotas
import { NavigationContainer } from '@react-navigation/native';
import AppNavigation from './src/routes/AppNavigation';

import authenticate from './src/config/services/Auth';

import styles from './src/styles/PagesStyle';

// Configuração da tela de splash
const SplashScreen = () => {
  return (
    <View style={styles.splashLogo}>
      <Image source={require('./src/assets/logos/aiko.png')} />
      <Text style={styles.splashTitle}>SPTrans - Olho Vivo</Text>
      <Text style={styles.splashFooter}>por Fernando Santiago</Text>
    </View>
  );
};

function App() {
  // Verifica se a tela de splash está em execução
  const [isLoading, setIsLoading] = useState(true);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Timer para tela de splash durar 2 segundos ao iniciar
    const timeoutId = setTimeout(() => {
      setIsLoading(false);
    }, 2000);

    // Autenticacao
    const auth = async () => {
      const result = await authenticate();
      setIsAuthenticated(result);
    };
    auth();

    return () => {
      clearTimeout(timeoutId);
    };
  }, []);

  // Após splash navega para página inicial
  if (isLoading) return <SplashScreen />;

  return <AppNavigation initialRoute={'Page1'} />;
}

export default App;
