import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import BottomTabNavigator from './BottomTabNavigator';
import CustomDrawerContent from './CustomDrawerContent';
import Corridors from '../helpers/Corridors';

const Drawer = createDrawerNavigator();

const DrawerNavigator = () => (
  <Drawer.Navigator drawerContent={(props) => <CustomDrawerContent {...props} />}>
    <Drawer.Screen name="LocaBuSP" component={BottomTabNavigator} />
    <Drawer.Screen name="Corredores" component={Corridors} />
  </Drawer.Navigator>
);

export default DrawerNavigator;