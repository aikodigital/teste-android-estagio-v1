import { NavigationContainer } from "@react-navigation/native";
import { useTheme } from "styled-components/native";
import { View } from "react-native";
import { TabBottomNavigator } from "./TabBottomNavigator";


export function Routes() {
    const { COLORS } = useTheme();

    return (
        <View style={{ backgroundColor: COLORS.WHITE, flex: 1 }}>
            <NavigationContainer>
               <TabBottomNavigator/>
            </NavigationContainer>
        </View>
    );
}
