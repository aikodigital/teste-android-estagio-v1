import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

import { ThemeProvider } from 'styled-components';
import { theme } from '../styled/theme';

import HomeScreen from "../screens/HomeScreen";  
import BusLinesScreen from "../screens/BusLinesScreen";
import BusStopsScreen from "../screens/BusStopsScreen"; 
import VehicleMap from "../screens/VehicleMap";
const Tab = createBottomTabNavigator();

const TabNavigator = () => {
  return (
    <ThemeProvider theme={theme}>
      <Tab.Navigator initialRouteName="Home"
                    screenOptions={{
                      headerShown: false,
                    }} >
        <Tab.Screen
          name="Home"
          component={HomeScreen}
          options={{ tabBarLabel: 'Home', 
                     tabBarIcon: ({ color, size }) => (
                      <FontAwesome5 name="home" color={color} size={size} />
                    ),
          }}
        />
        <Tab.Screen
          name="Lines"
          component={BusLinesScreen}
          options={{ tabBarLabel: 'Linhas' ,
                      tabBarIcon: ({ color, size }) => (
                        <FontAwesome5 name="route" color={color} size={size} />
                      ),
          }}
        />
        <Tab.Screen
          name="Parada"
          component={BusStopsScreen}
          options={{ tabBarLabel: 'Paradas',
                      tabBarIcon: ({ color, size }) => (
                        <MaterialCommunityIcons name="bus-stop-covered" color={color} size={size} />
                      ),
           }}
        />
        <Tab.Screen
          name="Veiculos"
          component={VehicleMap}
          options={{ tabBarLabel: 'Veículos',
                      tabBarIcon: ({ color, size }) => (
                        <MaterialCommunityIcons name="bus" color={color} size={size} />
                      ),
          }}
        />
      </Tab.Navigator>
    </ThemeProvider>
  );
};

export default TabNavigator;
