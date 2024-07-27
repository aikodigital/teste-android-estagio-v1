import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Home from "../src/pages/Home/Home";
import Busca from "./pages/Busca/Busca";
import {  Splash } from "./pages/Splash/Splash";

export type RootStackParamList = {
  Splash: undefined,
  Home: undefined;
  Busca: undefined;
};


const Stack = createNativeStackNavigator<RootStackParamList>();

function Routes() {
  return (
    <Stack.Navigator>
      <Stack.Screen name="Home" component={Home} options={{ headerShown: false }}/>
      <Stack.Screen name="Busca" component={Busca} options={{ headerShown: false }}/>
    </Stack.Navigator>
  );
}

export default Routes;
