import React, { FunctionComponent, useContext, useEffect, useState,} from "react";
import axios from "axios";

import { GlobalContext } from "../context/GlobalContext";

import { useNavigation } from "@react-navigation/native";
import { NativeStackNavigationProp } from "@react-navigation/native-stack";
import { RootStackParamList } from "../Routes";

import { Text, TouchableOpacity, View } from "react-native";

import { ScrollView } from "react-native-gesture-handler";

import Icon from "react-native-vector-icons/FontAwesome5";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

type ParadaBusSelectedprops = {
  cl: number;
};

export const ParadasBusSelected: FunctionComponent<ParadaBusSelectedprops> = ({
  cl,
}) => {
  const { setShowParadasBusSelected } = useContext(GlobalContext);

  const [dataParadas, setDataParadas] = useState<[]>([]);

  const navigation = useNavigation<NavigationProp>();

  useEffect(() => {
    const getParadasOfLinha = async () => {
      const response = await axios.get(
        ` https://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha=${cl}`
      );
      setDataParadas(response.data);
    };

    getParadasOfLinha();
  }, []);

  return (
    <View className="w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0 z-10 ">
      <View className="pl-5 pb-5 bg-bluePrimary">
        <TouchableOpacity
          className="flex flex-row items-center gap-1 pt-[80px]"
          onPress={() => {
            setShowParadasBusSelected(false);
            navigation.navigate("Linha");
          }}
        >
          <Icon name="arrow-left" size={25} color="#fff" />
        </TouchableOpacity>
      </View>
      <View>
        <Text className="font-bold text-bluePrimary text-xl pl-5 pt-2">Paradas da linha</Text>
          <View className="pb-[220px]">
              <ScrollView>
                {dataParadas?.map((paradas: any, index: number) => (
                  <View key={index} className="px-5 pt-6">
                    <View className="flex flex-row items-center">
                      <Icon name="map-pin" size={18} color="#003184" />
                      <Text className="text-[#C9C9C9] text-sm font-semibold pl-1">
                        {paradas.np}
                      </Text>
                    </View>
                  </View>
                ))}
              </ScrollView>
          </View>
      </View>
    </View>
  );
};
