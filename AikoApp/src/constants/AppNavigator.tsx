// AppNavigator.tsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { Ionicons } from '@expo/vector-icons';
import Home from '../Pages/Home';
import Linhas from '../Pages/Linhas';
import Paradas from '../Pages/Paradas';
import Positions from '../Pages/Positions';
import Previsao from '../Pages/Previsao';

const Tab = createBottomTabNavigator();

function AppNavigator() {
    return (
        <NavigationContainer>
            <Tab.Navigator
                screenOptions={({ route }) => ({
                    tabBarStyle: {
                        backgroundColor: '#111',
                        borderTopLeftRadius: 14,
                        borderTopRightRadius: 14,
                        height: '10%',
                        padding: 6,
                    },
                    tabBarActiveTintColor: '#ffffff',
                    tabBarInactiveTintColor: 'grey',
                    tabBarIcon: ({ color, size }) => {
                        let iconName;

                        if (route.name === 'Home') {
                            iconName = 'home';
                        } else if (route.name === 'Positions') {
                            iconName = 'navigate';
                        } else if (route.name === 'Linhas') {
                            iconName = 'bus';
                        } else if (route.name === 'Paradas') {
                            iconName = 'pin';
                        } else if (route.name === 'Previsao') {
                            iconName = 'time';
                        }
                        return <Ionicons name={iconName} size={size} color={color} />;
                    },
                })}>
                <Tab.Screen name="Home" component={Home} options={{
                    title: 'Home',
                    headerStyle: {
                        backgroundColor: '#091833',
                    },
                    headerTintColor: '#fff',
                    headerTitleStyle: {
                        fontWeight: '600',
                    },
                }} />
                <Tab.Screen name="Positions" component={Positions} options={{
                    title: 'Posições',
                    headerStyle: {
                        backgroundColor: '#091833',
                    },
                    headerTintColor: '#fff',
                    headerTitleStyle: {
                        fontWeight: '600',
                    },
                }} />
                <Tab.Screen name="Linhas" component={Linhas} options={{
                    title: 'Linhas',
                    headerStyle: {
                        backgroundColor: '#091833',
                    },
                    headerTintColor: '#fff',
                    headerTitleStyle: {
                        fontWeight: '600',
                    },
                }} />
                <Tab.Screen name="Paradas" component={Paradas} options={{
                    title: 'Paradas',
                    headerStyle: {
                        backgroundColor: '#091833',
                    },
                    headerTintColor: '#fff',
                    headerTitleStyle: {
                        fontWeight: '600',
                    },
                }} />
                <Tab.Screen name="Previsao" component={Previsao} options={{
                    title: 'Previsão',
                    headerStyle: {
                        backgroundColor: '#091833',
                    },
                    headerTintColor: '#fff',
                    headerTitleStyle: {
                        fontWeight: '600',
                    },
                }} />
            </Tab.Navigator>
        </NavigationContainer>
    );
}

export default AppNavigator;
