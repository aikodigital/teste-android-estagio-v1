import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import HomeScreen from './src/screens/HomeScreen';
import VehiclePositionScreen from './src/screens/VehiclePositionScreen';

const Stack = createStackNavigator();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="VehiclePositions" component={VehiclePositionScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
