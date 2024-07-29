import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import theme from '../global/theme';
import MainScreen from '../screens/MainScreen';
import SearchLineScreen from '../screens/SearchLineScreen';
import LineDetailsScreen from '../screens/LineDetailsScreen';
import LineMapScreen from '../screens/LineMapScreen';
import SearchStopScreen from '../screens/SearchStopScreen';
import StopDetailsScreen from '../screens/StopDetailsScreen';

export type RootStackParamList = {
  MainScreen: undefined;
  SearchLineScreen: undefined;
  LineDetailsScreen: {lineId: number};
  LineMapScreen: {lineId: number};
  SearchStopScreen: undefined;
  StopDetailsScreen: {stopId: number};
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
        <Stack.Screen
          name="SearchStopScreen"
          component={SearchStopScreen}
          options={{
            headerTitle: 'Buscar Paradas',
          }}
        />
        <Stack.Screen
          name="StopDetailsScreen"
          component={StopDetailsScreen}
          options={{
            headerTitle: 'Linhas da Parada',
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default Navigation;
