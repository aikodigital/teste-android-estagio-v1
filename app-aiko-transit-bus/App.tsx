import { NavigationContainer } from "@react-navigation/native";
import { GlobalContextProvider } from "./src/context/GlobalContext";
import Routes from "./src/Routes";
import { GestureHandlerRootView } from "react-native-gesture-handler";
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
