import { StatusBar, StyleSheet, Text, View  } from 'react-native';

import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import { Home } from './src/screens/Home';
import { BusLines } from './src/screens/BusLines';
import { BusStopMap } from './src/screens/BusStopMap';
import { VehicleMapPositionsMap } from './src/screens/VehiclePositionsMap';
import { EstimatedArrival } from './src/screens/EstimatedArrival';


const Stack = createStackNavigator();


export default function App() {
  return (

    <View style={styles.container}>

      <StatusBar 
        barStyle='light-content'
        backgroundColor='transparent'
        translucent
      />

      <NavigationContainer>
        <Stack.Navigator screenOptions={{ headerShown: false  }} initialRouteName="Home">

          <Stack.Screen
           name="Home" 
           component={Home} />

          <Stack.Screen 
            name="Linhas de Ônibus" 
            component={BusLines} />

          <Stack.Screen 
            name="Paradas de Ônibus" 
            component={BusStopMap} />

            <Stack.Screen 
            name="Mapa de Posições de Veiculos" 
            component={VehicleMapPositionsMap} />

            <Stack.Screen 
            name="Previsão de chegada" 
            component={EstimatedArrival} />

        </Stack.Navigator>
      </NavigationContainer>
      
    </View>
  );
}

const styles = StyleSheet.create({ container: { flex: 1 } });