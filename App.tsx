import { Roboto_400Regular, Roboto_700Bold } from '@expo-google-fonts/roboto';
import { ThemeProvider } from 'styled-components/native';
import { StatusBar } from 'expo-status-bar';
import { useFonts } from 'expo-font';
//
import { Routes } from './src/routes';
import theme from './src/theme';
import { ActivityIndicator } from 'react-native';


export default function App() {
  const [fontLoader] = useFonts({
    Roboto_400Regular,
    Roboto_700Bold
  });

  return (
    <ThemeProvider theme={theme}>
        {fontLoader ? <Routes /> : <ActivityIndicator />}
        <StatusBar style="auto" />
    </ThemeProvider>
  );
}

