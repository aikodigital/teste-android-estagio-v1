import React, { useEffect } from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import LinesScreen from './src/screens/LinesScreen';
import MapScreen from './src/screens/MapScreen';
import SplashScreen from './src/screens/SplashScreen'; 

const Stack = createNativeStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Splash" screenOptions={{ headerShown: false }}>
        <Stack.Screen name="Splash" component={SplashScreen} />
        <Stack.Screen name="Lines" component={LinesScreen} options={{ title: 'Linhas' }} />
        <Stack.Screen name="Map" component={MapScreen} options={{ title: 'MyBus' }} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
