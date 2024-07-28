import React from "react";

import { View } from "react-native";
import Icon from "react-native-vector-icons/FontAwesome";

export const IconParadas = () => {
  return (
    <View className="flex flex-col items-center">
      <View className="w-[24px] h-[30px] justify-center items-center bg-[#fff] border-2 border-bluePrimary rounded-md">
        <Icon name="bus" size={16} color="#003184" />
      </View>
      <View className="border border-bluePrimary h-2"></View>
    </View>
  );
};
