import React from "react";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Home from "./app/pages/Home/Home";
import Busca from "./app/pages/Busca/Busca";
import Linha from "./app/pages/Linha/Linha";

export type RootStackParamList = {
  Home: undefined;
  Busca: undefined;
  Linha: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();

function Routes() {
  return (
    <Stack.Navigator>
      <Stack.Screen
        name="Home"
        component={Home}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="Busca"
        component={Busca}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="Linha"
        component={Linha}
        options={{ headerShown: false }}
      />
    </Stack.Navigator>
  );
}

export default Routes;
