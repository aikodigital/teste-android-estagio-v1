import { Text, View, ScrollView } from "react-native";
import {Header} from "../components/header"; 
import { BuscarLinha } from "../components/buscarLinha"
import Constants  from "expo-constants";
import { GestureHandlerRootView } from 'react-native-gesture-handler';
import MapScreen from "../components/mapCoponent";

const statusBar = Constants.statusBarHeight;

export default function Index() {
  return (

    <GestureHandlerRootView style={{ flex: 1 }}>
      <View style={{ flex: 1}} className="bg-slate-200">
          
          <View className="w-full px-4" style={{ marginTop: statusBar + 8}}>
            <Header />    
            <BuscarLinha />
            <MapScreen />            
          </View>
      </View>
    </GestureHandlerRootView>
  );
}
