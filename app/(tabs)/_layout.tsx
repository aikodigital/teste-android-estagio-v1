import { Tabs } from "expo-router";
import React from "react";

import { TabBarIcon } from "@/components/navigation/TabBarIcon";
import { Colors } from "@/constants/Colors";
import { useColorScheme } from "@/hooks/useColorScheme";

export default function TabLayout() {
  const colorScheme = useColorScheme();

  return (
    <Tabs
      screenOptions={{
        tabBarActiveTintColor: Colors[colorScheme ?? "light"].tint,
        headerShown: false,
      }}
    >
      <Tabs.Screen
        name="index"
        options={{
          title: "Mapa",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon name={focused ? "map" : "map-outline"} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="explore"
        options={{
          title: "Linhas",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon name={focused ? "bus" : "bus-outline"} color={color} />
          ),
        }}
      />
      <Tabs.Screen
        name="station"
        options={{
          title: "Paradas",
          tabBarIcon: ({ color, focused }) => (
            <TabBarIcon
              name={focused ? "flag" : "flag-outline"}
              color={color}
            />
          ),
        }}
      />
    </Tabs>
  );
}
