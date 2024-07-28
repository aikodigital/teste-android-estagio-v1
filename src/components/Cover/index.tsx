import { ImageBackground, Text } from "react-native";
import { LinearGradient } from "expo-linear-gradient";
import { Timer } from "./timer";

import { styles } from "@/components/Cover/styles";

export function Cover() {
  return (
    <ImageBackground
      source={require("@/assets/cover.jpg")}
      style={styles.image}
    >
      <LinearGradient
        colors={["rgba(0,0,0, 0.2)", "#000"]}
        style={styles.gradient}
      >
        <Text style={styles.timer}>
          <Timer />
        </Text>
      </LinearGradient>
    </ImageBackground>
  );
}
