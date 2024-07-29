import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import MainScreen from '../screens/MainScreen';
import SearchLineScreen from '../screens/SearchLineScreen';
import LineDetailsScreen from '../screens/LineDetailsScreen';
import LineMapScreen from '../screens/LineMapScreen';
import theme from '../global/theme';

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
      <Stack.Navigator
        initialRouteName="MainScreen"
        screenOptions={{
          headerStyle: {
            backgroundColor: theme.colors.primary,
          },
          headerTintColor: theme.colors.backgroundColor,
          headerTitleStyle: {
            fontWeight: 'bold',
          },
          headerShadowVisible: false,
          headerTitleAlign: 'center',
          statusBarColor: theme.colors.primary,
        }}>
        <Stack.Screen
          name="MainScreen"
          component={MainScreen}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="SearchLineScreen"
          component={SearchLineScreen}
          options={{
            headerTitle: 'Buscar Linhas',
          }}
        />
        <Stack.Screen
          name="LineDetailsScreen"
          component={LineDetailsScreen}
          options={{
            headerTitle: 'Paradas da Linha',
          }}
        />
        <Stack.Screen
          name="LineMapScreen"
          component={LineMapScreen}
          options={{
            headerTitle: 'Mapa da Linha',
            headerStyle: {
              backgroundColor: theme.colors.secondary,
            },
            statusBarColor: theme.colors.secondary,
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default Navigation;
