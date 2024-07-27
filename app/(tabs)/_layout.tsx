import { Tabs } from 'expo-router';
import React from 'react';
import { Colors } from '@/constants/Colors';
import { useColorScheme } from '@/hooks/useColorScheme';
import { BusIcon } from '@/components/navigation/BusIcon';
import { BusStopIcon } from '@/components/navigation/BusStopIcon';
import { BusClockIcon } from '@/components/navigation/BusClockIcon';
import { BusMarkerIcon } from '@/components/navigation/BusMarkerIcon';

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? 'light'].tint,
        headerShown: false,
        tabBarStyle: {
          backgroundColor: Colors[colorScheme ?? 'light'].background,
          borderTopWidth: 0,
          shadowColor: '#000',
          shadowOffset: { width: 0, height: 2 },
          shadowOpacity: 0.1,
          shadowRadius: 4,
          elevation: 5,
        },
        tabBarLabelStyle: {
          fontSize: 12,
          fontWeight: 'bold',
        },
        tabBarIconStyle: {
          marginTop: 4,
        },
      }}
    >
      <Tabs.Screen
        name='index'
        options={{
          title: 'Posição dos Veículos',
          tabBarIcon: BusMarkerIcon,
        }}
      />
      <Tabs.Screen
        name='buscar-linha-de-onibus'
        options={{
          title: 'Linha de Ônibus',
          tabBarIcon: BusIcon,
        }}
      />
      <Tabs.Screen
        name='buscar-parada-de-onibus'
        options={{
          title: 'Parada de Ônibus',
          tabBarIcon: BusStopIcon,
        }}
      />
      <Tabs.Screen
        name='previsao-de-chegada'
        options={{
          title: 'Previsão de Chegada',
          tabBarIcon: BusClockIcon,
        }}
      />
    </Tabs>
  );
}
