import { NavigationContainer } from "@react-navigation/native";
import {
  GlobalContext,
  GlobalContextProvider,
} from "./src/context/GlobalContext";
import Routes from "./src/Routes";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { Splash } from "./src/pages/Splash/Splash";
import { useEffect, useState } from "react";

export default function App() {
  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
        <NavigationContainer>
          <GlobalContextProvider>
            <Routes />
          </GlobalContextProvider>
        </NavigationContainer>
    </GestureHandlerRootView>
  );
}
