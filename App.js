import React from 'react';
import { useEffect, useState } from 'react';
import { View, Image } from 'react-native'
import AppNavigation from './src/screens/AppNavigation';
import authenticate from './src/services/API_OlhoVivo';
import styles from './src/styles/PagesStyle';

const SplashScreen = () => {
  return( 
    <View style={styles.splashLogo}>
      <Image source={require('./assets/logos/aiko.png')} />
    </View>
)};

function App() {
  const [isLoading, setIsLoading] = useState(true) 
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
      
    const timeout = setTimeout(() => {setIsLoading(false)}, 2000)
    
    const auth = async () => {
      const result = await authenticate();
      setIsAuthenticated(result);
    };
    auth();

    return () => { clearTimeout(timeout) }    
  }, []);

  if (isLoading)
    return <SplashScreen />

  return <AppNavigation initialRoute={'Page1'} />
};

export default App;