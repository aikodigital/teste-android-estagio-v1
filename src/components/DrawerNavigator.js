import React from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import BottomTabNavigator from './BottomTabNavigator';
import CustomDrawerContent from '../routes/CustomDrawerContent';
import Corridors from '../../layouts/Corridors';

const Drawer = createDrawerNavigator();

const DrawerNavigator = () => (
  <Drawer.Navigator drawerContent={(props) => <CustomDrawerContent {...props} />}>
    <Drawer.Screen name="LocaBusSP" component={BottomTabNavigator} />
    <Drawer.Screen name="Corredores" component={Corridors} />
  </Drawer.Navigator>
);

export default DrawerNavigator;