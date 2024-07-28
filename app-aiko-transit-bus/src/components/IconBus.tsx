import React from "react";
import { View } from "react-native";

import Icon from "react-native-vector-icons/FontAwesome5";

export const IconBus = () => {
  return (
    <View className="flex flex-col items-center">
      <View className="w-[40px] h-[40px] justify-center items-center bg-[#fff] border-2 border-bluePrimary rounded-full">
        <Icon name="bus" size={20} color="#003184" />
      </View>
    </View>
  );
};
