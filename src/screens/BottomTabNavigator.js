import React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Image } from 'react-native';
import { createStackNavigator } from '@react-navigation/stack';
import RealTime from '../screens/RealTime';
import BusLines from '../helpers/BusLines';
import LineStops from '../helpers/LineStops';
import ExpectedTime from '../helpers/ExpectedTime';
import Corridors from '../helpers/Corridors';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';


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
            <Icon name="clock-outline" size={25} color="black" />
          ),
        }}
      />
      <Tab.Screen
        name="Linhas"
        component={BusLines}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Icon name="bus-side" size={25} color="black" />
          ),
        }}
      />
      <Tab.Screen
        name="Paradas"
        component={LineStops}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Icon name="bus-stop" size={25} color="black" />
          ),
        }}
      />
      <Tab.Screen
        name="Previsão"
        component={ExpectedTime}
        options={{
          tabBarIcon: ({ color, size }) => (
            <Icon name="clock-check" size={25} color="black" />
          ),
        }}
      />
    </Tab.Navigator>
  );
}