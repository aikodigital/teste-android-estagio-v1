import React, { useState } from "react";

import axios from "axios";

import { ScrollView, Text, TouchableOpacity, View } from "react-native";
import { TextInput } from "react-native-gesture-handler";

import Icon from "react-native-vector-icons/FontAwesome5";
export const Linhas = () => {
  const [dataLinhas, setDataLinhas] = useState<[]>([]);

  const getLinha = async (value: String) => {
    const response = await axios.get(
      `http://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca=${value}`
    );
    setDataLinhas(response.data);
  };

  return (
    <View className="pt-5">
      <View>
        <View className="w-full h-[50px] flex flex-row justify-between items-center mt-[18px] bg-[#fff] border border-[#F2F2F2] rounded-[30px] pl-5 shadow-lg shadow-[#000] z-10">
          <TextInput
            className="w-[270px] font-semibold text-grayPrimary text-lg"
            placeholder="Busque a linha que desejar"
            onChangeText={(text) => getLinha(text)}
          />
          <View className="pr-4">
            <Icon name="search" size={30} color="#949494" />
          </View>
        </View>
      </View>
      <View>
        {dataLinhas.length > 0 ? (
          <View
            className={`${
              dataLinhas.length > 0 ? "h-[500px] block" : "h-0 hidden"
            } pt-6 px-5 relative bottom-[48px] rounded-[30px] border border-[#F2F2F2] bg-[#fff] shadow-lg shadow-[#000]`}
          >
            <View className="pt-5 pb-7">
              <ScrollView>
                {dataLinhas?.map((linhas: any) => (
                  <TouchableOpacity key={linhas.cl} className="pt-9">
                    <View>
                      <View className="flex flex-row flex-wrap ">
                        <Text className="text-grayPrimary text-lg font-medium">
                          {linhas.cl} -
                        </Text>
                        <Text className="text-grayPrimary text-lg font-medium">
                          {linhas.tp} /
                        </Text>
                        <Text className="text-grayPrimary text-lg font-medium">
                          {linhas.ts}{" "}
                        </Text>
                      </View>
                    </View>
                  </TouchableOpacity>
                ))}
              </ScrollView>
            </View>
          </View>
        ) : (
          <View className="flex justify-center items-center pt-2">
            <Text>Nenhuma linha encontrada</Text>
          </View>
        )}
      </View>
    </View>
  );
};
