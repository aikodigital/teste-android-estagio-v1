import React from "react";

import { Text, TouchableOpacity, View } from "react-native";
import { TouchableWithoutFeedback } from "react-native-gesture-handler";

import Icon from "react-native-vector-icons/FontAwesome5";

export const LinhaSelected = ()=> {
  return (
    <View className="w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0 px-5">
    <View>
      <TouchableOpacity
        className="flex flex-row items-center gap-1 pt-[80px]"
      >
        <Icon name="arrow-left" size={25} color="#003184" />
      </TouchableOpacity>
      <View className="flex flex-col justify-center items-center">
        <View className="flex flex-row justify-center pt-[50px]">
          <Text className="text-2xl font-semibold text-bluePrimary">
            Busca
          </Text>
        </View>
        <Text className="text-[#404040]">Escolha uma das opções</Text>
      </View>
      <View className="flex flex-row justify-around pt-6">
        <TouchableWithoutFeedback >
          <Text className={`text-xl px-4 py-1 rounded-md `}>Linhas</Text>
        </TouchableWithoutFeedback >
        <TouchableWithoutFeedback>
          <Text  className={`text-xl px-4 py-1 rounded-md $`}>Rotas</Text>
        </TouchableWithoutFeedback>
      </View> 
    </View>
  </View>
  );
};
