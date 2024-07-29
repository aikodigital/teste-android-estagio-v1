// components/navigation/TabBarIcon.tsx
import React from 'react';
import Ionicons from '@expo/vector-icons/Ionicons';
import { type IconProps } from '@expo/vector-icons/build/createIconSet';
import { type ComponentProps } from 'react';

type TabBarIconProps = IconProps<ComponentProps<typeof Ionicons>['name']> & {
  style?: object;
};

export function TabBarIcon({ style, ...rest }: TabBarIconProps) {
  return <Ionicons size={28} style={[{ marginBottom: -3 }, style]} {...rest} />;
}
