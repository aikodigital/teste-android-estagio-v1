import { SafeAreaView, StatusBar, useColorScheme } from "react-native";
import { Map } from "@/components/Map";
import { authenticate } from "../services/APIServices";
import { useEffect } from "react";

export default function Index() {
  const colorScheme = useColorScheme();

  useEffect(() => {
    authenticate()
  }, [])

  return (
    <SafeAreaView style={{
      flex: 1
    }}>
      <StatusBar 
        barStyle={colorScheme === 'dark' ? 'dark-content' : 'light-content'}
        backgroundColor="transparent"
      />
      
      <Map />
    </SafeAreaView>
  );
}
