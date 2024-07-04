import React from 'react';
import { View } from 'react-native';
import { MaterialCommunityIcons, FontAwesome6 } from '@expo/vector-icons';
import { style } from './style';

export default function IconComponent({ eventPress, iconName, size = 24, color = 'black' }) {
    const materialCommunityIcons = {
        PointMenu: "map-marker-multiple",
        BusStop: "bus-stop-covered",
        Timer: "av-timer"
    };

    const fontAwesome6 = {
        Lines: "lines-leaning",
        PointBus: "bus",
    };

    if (!iconName) {
        return <View></View>;
    }

    if (materialCommunityIcons[iconName]) {
        return (
            <MaterialCommunityIcons name={materialCommunityIcons[iconName]} size={size} color={color} onPress={eventPress} />
        );
    }

    if (fontAwesome6[iconName]) {
        return (
            <FontAwesome6 name={fontAwesome6[iconName]} size={size} color={color} onPress={eventPress} />
        );
    }
}