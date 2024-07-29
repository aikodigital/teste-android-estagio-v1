import '../styles/global.css'
import { Slot } from "expo-router";
import TabLayout from './TabLayout';

export default function RootLayout() {
  return ( 
  <TabLayout>
    <Slot />
  </TabLayout>
  );  
}
