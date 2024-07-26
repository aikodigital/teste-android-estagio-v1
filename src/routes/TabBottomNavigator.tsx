import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { useTheme } from "styled-components/native";
import { FontAwesome, MaterialCommunityIcons } from '@expo/vector-icons';
import { Platform } from 'react-native';
import { Home } from "../screens/Home";
import { Lines } from "../screens/Lines";

const { Navigator, Screen } = createBottomTabNavigator();
const tabBarHeight = Platform.OS === 'ios' ? 80 : 60;

export function TabBottomNavigator() {
    const { COLORS} = useTheme();

    return (
        <Navigator
            initialRouteName="Home"
            screenOptions={{
                headerShown: false,
                tabBarStyle: {
                    position: 'absolute',
                    backgroundColor: COLORS.PURPLE_600,
                    height: tabBarHeight,
                    borderTopWidth: 2,
                    paddingBottom: 5,
                    borderTopLeftRadius: 20,
                    borderTopRightRadius: 20
                },
                tabBarActiveTintColor: COLORS.TEAL_600,
                tabBarInactiveTintColor: COLORS.WHITE,
                
            }}
        >
            <Screen
                options={{
                    tabBarIcon: ({ color }) => (
                        <FontAwesome name="home" size={32} color={color}/>
                    )
                }}
                name="Home"
                component={Home}
            />
            <Screen
                options={{
                    tabBarIcon: ({ color }) => (
                        <MaterialCommunityIcons name="bus-marker" size={32} color={color}/>
                    )
                }}
                name="Linhas"
                component={Lines}
            />
        </Navigator>
    )
}
