import React from "react";
import { TouchableOpacity, StyleSheet, Text, View } from "react-native";
import { FontAwesome } from "@expo/vector-icons";
import { theme } from "../theme";

interface Props {
  iconName: "bus" | "road" | "building";
  isSelected: boolean;
  title: string;
  onPress: () => void;
}

const OptionButton: React.FC<Props> = ({
  iconName,
  isSelected,
  onPress,
  title,
}) => {
  return (
    <View style={styles.optionButtonContainer}>
      <Text style={styles.title}>{title}</Text>
      <TouchableOpacity
        style={[styles.optionButton, isSelected && styles.selectedOption]}
        onPress={onPress}
        testID="option-button"
      >
        <FontAwesome name={iconName} size={24} color="white" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  optionButtonContainer: {
    alignItems: "center",
  },
  title: {
    fontSize: 13,
    fontFamily: theme.fontFamily.medium,
    marginBottom: 10,
  },
  optionButton: {
    width: 50,
    height: 50,
    borderRadius: 25,
    backgroundColor: theme.colors.dark_green,
    justifyContent: "center",
    alignItems: "center",
    elevation: 5,
    shadowColor: "#000000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.3,
    shadowRadius: 2,
  },
  selectedOption: {
    backgroundColor: theme.colors.light_blue,
  },
});

export default OptionButton;
