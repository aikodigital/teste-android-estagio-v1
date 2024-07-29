import React from 'react';
import { View, Text } from 'react-native';
import { DrawerContentScrollView, DrawerItemList } from '@react-navigation/drawer';

const CustomDrawerContent = (props) => (
  <DrawerContentScrollView {...props}>
    <View>
      <Text style={{ fontSize: 20, fontWeight: 'bold', margin: 10 }}>Menu</Text>
    </View>
    <DrawerItemList {...props} />
  </DrawerContentScrollView>
);

export default CustomDrawerContent;