import React from "react";
import { useContext } from "react";
import { GlobalContext } from "../../../context/GlobalContext";

import { useNavigation } from "@react-navigation/native";

import { Text, View } from "react-native";
import {TouchableOpacity,TouchableWithoutFeedback,} from "react-native-gesture-handler";

import { Linhas } from "../../../components/Linhas";
import { Rotas } from "../../../components/Rotas";

import Icon from "react-native-vector-icons/FontAwesome5";
export default function Busca() {
  const { rowOrSearch, setRowOrSearch } = useContext(GlobalContext);

  const navigation = useNavigation();

  return (
    <View className="w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0 px-5">
      <View>
        <TouchableOpacity
          className="flex flex-row items-center gap-1 pt-[80px]"
          onPress={() => navigation.goBack()}
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
          <TouchableWithoutFeedback onPress={() => setRowOrSearch("linhas")}>
            <Text
              className={`text-xl px-4 py-1 rounded-md ${
                rowOrSearch === "linhas"
                  ? "bg-greenPrimary text-[#fff] font-semibold"
                  : "bg-transparent text-bluePrimary"
              }`}
            >
              Linhas
            </Text>
          </TouchableWithoutFeedback>
          <TouchableWithoutFeedback onPress={() => setRowOrSearch("rotas")}>
            <Text
              className={`text-xl px-4 py-1 rounded-md ${
                rowOrSearch === "rotas"
                  ? "bg-greenPrimary text-[#fff] font-semibold"
                  : "bg-transparent text-bluePrimary"
              }`}
            >
              Rotas
            </Text>
          </TouchableWithoutFeedback>
        </View>
        {rowOrSearch === "linhas" && <Linhas />}
        {rowOrSearch === "rotas" && <Rotas />}
      </View>
    </View>
  );
}
