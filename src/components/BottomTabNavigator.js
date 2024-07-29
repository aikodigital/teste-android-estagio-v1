import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Image } from 'react-native';
import { createStackNavigator } from '@react-navigation/stack';

import RealTime from '../../layouts/RealTime';
import BusLines from '../layouts/BusLines';
import LineStops from '../../layouts/LineStops';
import ExpectedTime from '../../layouts/ExpectedTime';
import Corridors from '../../layouts/Corridors';
import RealTimeIcon from '../assets/images/realtime.png';
import BusLinesIcon from '../assets/images/lines.png';
import LineStopsIcon from '../assets/images/stops.png';
import ExpectedTimeIcon from '../assets/images/clock.png';


const Tab = createBottomTabNavigator(); 
const Stack = createStackNavigator(); 

const HomeStack = () => (
  <Stack.Navigator screenOptions={{ headerShown: false }}>
    <Stack.Screen name="Velocidade" component={Speed} />
    <Stack.Screen name="Corredores" component={Corridors} />
  </Stack.Navigator>
);

export default function BottomTabNavigator() {
  return (
    <Tab.Navigator initialRouteName="RealTime" screenOptions={{ headerShown: false }}>
      <Tab.Screen name="Tempo Real"
        component={RealTime}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Image source={RealTimeIcon} style={{ width: 35, height: 35, tintColor: 'black' }} />
          ),
        }}
      />
      <Tab.Screen
        name="Linhas"
        component={BusLines}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Image source={BusLinesIcon} style={{ width: 24, height: 24, tintColor: 'black' }} />
          ),
        }}
      />
      <Tab.Screen
        name="Paradas"
        component={LineStops}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Image source={LineStopsIcon} style={{ width: 24, height: 24, tintColor: 'black' }} />
          ),
        }}
      />
      <Tab.Screen
        name="Previsão"
        component={ExpectedTime}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Image source={ExpectedTimeIcon} style={{ width: 24, height: 24, tintColor: 'black' }} />
          ),
        }}
      />
    </Tab.Navigator>
  );
}