// Home.tsx

import React from "react";
import { View } from "react-native";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import { Cover } from "../../components/Cover";
import { Filter } from "../../components/Filter";
import { Lines } from "../Nav/Lines";
import { Details } from "../Nav/Details";
import { styles } from "./styles";
import { Bus } from "../../types/types";

type RootStackParamList = {
  Lines: undefined;
  Details: { bus: Bus; lineDetails: { lt0: string; lt1: string } };
};

const Stack = createStackNavigator<RootStackParamList>();

export function Home() {
  return (
    <View style={styles.container}>
      <Cover />
      <NavigationContainer>
        <Stack.Navigator initialRouteName="Lines">
          <Stack.Screen name="Lines" component={Lines} />
          <Stack.Screen name="Details" component={Details} />
        </Stack.Navigator>
      </NavigationContainer>
      <Filter />
    </View>
  );
}
