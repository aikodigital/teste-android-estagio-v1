import React, { useEffect, useState } from 'react';
import { ThemeProvider } from 'styled-components/native';
import { StatusBar } from 'expo-status-bar';
import { useFonts } from 'expo-font';
import { Roboto_400Regular, Roboto_700Bold } from '@expo-google-fonts/roboto';
import { ActivityIndicator, Alert } from 'react-native';
import * as Location from 'expo-location';
import theme from './src/theme';
import { Routes } from './src/routes';
import { GestureHandlerRootView } from 'react-native-gesture-handler';

export default function App() {
  const [fontLoader] = useFonts({
    Roboto_400Regular,
    Roboto_700Bold,
  });

  const [locationPermission, setLocationPermission] = useState<boolean | null>(null);

  useEffect(() => {
    const requestLocationPermission = async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        Alert.alert('Permissão de localização não concedida', 'O aplicativo precisa de permissão de localização para funcionar corretamente.');
        setLocationPermission(false);
      } else {
        setLocationPermission(true);
      }
    };

    requestLocationPermission();
  }, []);

  if (!fontLoader) {
    return <ActivityIndicator />;
  }

  if (locationPermission === null) {
    return <ActivityIndicator />;
  }

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <ThemeProvider theme={theme}>
        <Routes />
        <StatusBar style="auto" />
      </ThemeProvider>
    </GestureHandlerRootView>
  );
}
