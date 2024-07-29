import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';
import AuthTest from '../components/AuthTest';
import BusLinesScreen from '../components/BusLinesAndForecast';
import TrackingBus from '../components/TrackingBus';

const Stack = createStackNavigator();

const Navigation = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="AuthTest"
        screenOptions={{
          headerStyle: {
            backgroundColor: '#f4511e',
          },
          headerTintColor: '#fff',
          headerTitleStyle: {
            fontWeight: 'bold',
          },
        }}
      >
        <Stack.Screen 
          name="AuthTest" 
          component={AuthTest} 
          options={{ title: 'Authentication' }}
        />
        <Stack.Screen 
          name="BusLines" 
          component={BusLinesScreen} 
          options={{ title: 'Bus Lines and Forecast' }}
        />
        <Stack.Screen 
          name="TrackingBus" 
          component={TrackingBus} 
          options={{ title: 'Tracking Bus' }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default Navigation;
