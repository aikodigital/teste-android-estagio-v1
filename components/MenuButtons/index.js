import { View, Text } from "react-native";
import ExpoIcons from "../ExpoIcons";
import { width } from "../../utils/GlobalVal";

import { style } from "./style";

export default ({ iconName, text }) => {
    const imgSize = width * 0.095

    return (
        <View style={style.container}>
            <View style={style.img}>
                <ExpoIcons size={imgSize} iconName={iconName}></ExpoIcons>
            </View>
            <Text style={style.text}>{text}</Text>
        </View>
    )
};
