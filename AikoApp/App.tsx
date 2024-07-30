import React from "react";

import { createStackNavigator } from "@react-navigation/stack";
import AppNavigator from "./src/constants/AppNavigator";

const Stack = createStackNavigator();

export default function App() {
  return (
    <AppNavigator />
  );
}
