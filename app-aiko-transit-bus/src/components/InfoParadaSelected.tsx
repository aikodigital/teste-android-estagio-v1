import React, { useContext, useState } from "react";
import { Text, View, TouchableOpacity, ScrollView } from "react-native";
import { GlobalContext } from "../context/GlobalContext";
import { NativeStackNavigationProp } from "@react-navigation/native-stack";
import { RootStackParamList } from "../Routes";
import Icon from "react-native-vector-icons/FontAwesome5";
import { useNavigation } from "@react-navigation/native";
import { TouchableWithoutFeedback } from "react-native-gesture-handler";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

const InfoParadaSelected = () => {
  const { setRowOrSearch, infoParada, paradaAtualData } =
    useContext(GlobalContext);
  const navigation = useNavigation<NavigationProp>();

  return (
    <View
      className={`w-full ${
        infoParada ? "h-[400px]" : "h-[100px]"
      } absolute bottom-0 flex px-5 rounded-t-[20px] bg-[#fff] border border-[#F2F2F2] shadow-lg shadow-[#000]`}
    >
      <View>
        <TouchableOpacity
          className="w-full h-[50px] flex flex-row items-center mt-[18px] bg-[#fff] border border-[#F2F2F2] rounded-[30px] pl-5 shadow-lg shadow-[#000]"
          onPress={() => {
            setRowOrSearch("linhas");
            navigation.navigate("Busca");
          }}
        >
          <Icon name="search" size={30} color="#949494" />
          <Text className="pl-3 text-[#949494] font-semibold text-xl">
            Linhas e destinos
          </Text>
        </TouchableOpacity>
      </View>
      {infoParada ? (
        <View className="pt-3 flex-1">
          <View className="pt-[10px] h-[300px] px-2 border border-[#F2F2F2] bg-[#fff] shadow-lg shadow-[#000] rounded-md pb-2">
            <View className="flex flex-row gap-3 items-center">
              <View className="bg-bluePrimary px-1 py-1 rounded-md">
                <Icon name="bus" size={20} color="#fff" />
              </View>
              <Text className="w-[320px] text-grayPrimary font-semibold text-[18px]">
                {paradaAtualData?.p?.np}
              </Text>
            </View>
            <ScrollView>
              <Text className="text-grayPrimary font-semibold text-xl pt-3">
                Chegadas:
              </Text>
              {paradaAtualData?.p?.l.map((parada: any) => (
                <View key={parada.cl} className="pt-3 border-b border-[#F2F2F2] pb-2 ">
                  <TouchableWithoutFeedback className="w-[70px] flex items-center px-3 py-1 bg-greenPrimary text-[18px] rounded-full">
                    <Text className="text-[#fff] font-bold">{parada.cl}</Text>
                  </TouchableWithoutFeedback>
                  <View className="flex flex-row items-center pt-2 pl-3">
                    <View className="flex-1 flex flex-row items-center flex-wrap">
                      <Text className="text-grayPrimary">{parada.lt0} /</Text>
                      <Text className="text-grayPrimary">
                        {parada.lt1}
                      </Text>
                    </View>
                    {parada.vs.length > 0 && (
                      <View className="flex flex-row items-center pl-2 pr-2">
                        <Icon name="clock" size={20} color="#404040" />
                        <Text className="text-grayPrimary pl-1">
                          {parada.vs[0].t}
                        </Text>
                      </View>
                    )}
                  </View>
                </View>
              ))}
            </ScrollView>
          </View>
        </View>
      ) : null}
    </View>
  );
};

export default InfoParadaSelected;
