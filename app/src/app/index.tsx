import { Text, View, ScrollView } from "react-native";
import {Header} from "../components/header"; 

import Constants  from "expo-constants";

const statusBar = Constants.statusBarHeight;

export default function Index() {
  return (
    <View style={{ flex: 1}} className="bg-slate-200">

        <View className="w-full px-4" style={{ marginTop: statusBar + 8}}>

          <Header />

        </View>
    </View>
  );
}
