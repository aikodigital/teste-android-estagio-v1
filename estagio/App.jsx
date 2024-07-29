import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import OlhoVivo from './components/OlhoVivo';
import Map from './components/Map';
import { AppProvider } from './appContext';

export default function App() {
  return (
    <AppProvider>
      <View style={styles.container}>
        <View style={styles.mapContainer}>
    <Map/>
        </View>
        <View style={styles.contentContainer}>
          <OlhoVivo />
        </View>
        <StatusBar style="auto" />
      </View>
    </AppProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    flexDirection: 'column',
  },
  mapContainer: {
    flex: 1,
  
  },
  contentContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'flex-end',

  },
});
