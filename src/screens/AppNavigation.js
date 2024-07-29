import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import DrawerNavigator from './DrawerNavigator';
import LineStops from '../helpers/LineStops';
import OnboardingCarousel from '../helpers/Onboarding';

const Stack = createNativeStackNavigator();

const AppNavigation = ({ initialRoute }) => (
  <NavigationContainer>
    <Stack.Navigator initialRouteName={initialRoute} screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Onboarding" component={OnboardingCarousel} />
      <Stack.Screen name="Paradas" component={LineStops} />
      <Stack.Screen name="RealTime" component={DrawerNavigator}/>
    </Stack.Navigator>
  </NavigationContainer>
);

export default AppNavigation;