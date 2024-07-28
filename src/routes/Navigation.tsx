import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import MainScreen from '../screens/MainScreen';
import SearchLineScreen from '../screens/SearchLineScreen';
import LineDetailsScreen from '../screens/LineDetailsScreen';
import LineMapScreen from '../screens/LineMapScreen';

export type RootStackParamList = {
  AuthenticationScreen: undefined;
  MainScreen: undefined;
  SearchLineScreen: undefined;
  LineDetailsScreen: {lineId: number};
  LineMapScreen: {lineId: number};
};

const Stack = createNativeStackNavigator<RootStackParamList>();

const Navigation: React.FC = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="MainScreen">
        <Stack.Screen
          name="MainScreen"
          component={MainScreen}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="SearchLineScreen"
          component={SearchLineScreen}
          options={{title: 'Buscar por Linha'}}
        />
        <Stack.Screen
          name="LineDetailsScreen"
          component={LineDetailsScreen}
          options={{title: 'Detalhes da Linha'}}
        />
        <Stack.Screen
          name="LineMapScreen"
          component={LineMapScreen}
          options={{title: 'Mapa da Linha'}}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default Navigation;
