import React from "react";
import { Slot } from "expo-router";
import { StatusBar, View } from "react-native";
import * as SplashScreen from "expo-splash-screen";

import {
  useFonts,
  Roboto_400Regular,
  Roboto_500Medium,
  Roboto_700Bold,
} from "@expo-google-fonts/roboto";

import "react-native-reanimated";
import "react-native-gesture-handler";

SplashScreen.preventAutoHideAsync();
export default function Layout() {
  const [fontsLoaded] = useFonts({
    Roboto_400Regular,
    Roboto_500Medium,
    Roboto_700Bold,
  });

  if (fontsLoaded) {
    SplashScreen.hideAsync();
  }
  return (
    <View style={{ flex: 1 }}>
      <StatusBar barStyle={"light-content"} />
      {fontsLoaded && <Slot />}
    </View>
  );
}
