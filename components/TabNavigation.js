import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeScreen from "../../RotaMobile/screens/HomeScreen";  
import LinesScreen from "../../RotaMobile/screens/LinesScreen"; 
import ParadaScreen from "../../RotaMobile/screens/ParadaScreen"; 
const Tab = createBottomTabNavigator();

const TabNavigation = () => {
  return (
    <Tab.Navigator initialRouteName="Inicio"
                   screenOptions={{
                    headerShown: false,
                   }} >
      <Tab.Screen
        name="Home"
        component={HomeScreen}
        options={{ tabBarLabel: 'Inicio' }}
      />
      <Tab.Screen
        name="Lines"
        component={LinesScreen}
        options={{ tabBarLabel: 'Linhas' }}
      />
      <Tab.Screen
        name="Parada"
        component={ParadaScreen}
        options={{ tabBarLabel: 'Paradas' }}
      />
    </Tab.Navigator>
  );
};

export default TabNavigation;
