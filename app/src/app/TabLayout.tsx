// components/TabLayout.tsx
import React from 'react';
import { Tabs } from 'expo-router';
import { TabBarIcon } from '../components/navigation/TabBarIcon';

type TabLayoutProps = {
  children?: React.ReactNode;
};

export default function TabLayout({ children }: TabLayoutProps) {
  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: 'blue',  // Cor ativa dos ícones
        tabBarInactiveTintColor: 'gray', // Cor inativa dos ícones
        headerShown: false,
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: 'Home',
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon name={focused ? 'home' : 'home-outline'} color={color} />
          ),
        }}
        
      />
      <Tabs.Screen
        name="explore"
        options={{
          title: 'Explore',
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon name={focused ? 'search' : 'search-outline'} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="profile"
        options={{
          title: 'Profile',
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon name={focused ? 'person' : 'person-outline'} color={color} />
          ),
        }}
      />
    </Tabs>
  );
}
