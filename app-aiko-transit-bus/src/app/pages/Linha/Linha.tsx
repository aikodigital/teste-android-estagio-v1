import React, { useContext, useState } from "react";
import { GlobalContext } from "../../../context/GlobalContext";

import { useNavigation } from "@react-navigation/native";
import { NativeStackNavigationProp } from "@react-navigation/native-stack";
import { RootStackParamList } from "../../../Routes";

import { Text, TouchableOpacity, View } from "react-native";
import { TouchableWithoutFeedback } from "react-native-gesture-handler";

import { VeiculoInMap } from "../../../components/VeiculoInMap";
import { ParadasBusSelected } from "../../../components/ParadasBusSelected";

import Icon from "react-native-vector-icons/FontAwesome5";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

export default function Linha() {
  const {
    dataLinha,
    showBusInMap,
    setShowBusInMap,
    showParadasBusSelected,
    setShowParadasBusSelected,
  } = useContext(GlobalContext);

  const [vehicleCoords, setVehicleCoords] = useState<{ py: number; px: number; } | null>(null);
  const [selectedParadaCl, setSelectedParadaCl] = useState<number | null>(null);
  const navigation = useNavigation<NavigationProp>();


  return (
    <View
      className={`w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0 ${
        showBusInMap || showParadasBusSelected ? "" : "px-5"
      }`}
    >
      <View>
        <TouchableOpacity
          className="flex flex-row items-center gap-1 pt-[80px]"
          onPress={() => navigation.goBack()}
        >
          <Icon name="arrow-left" size={25} color="#003184" />
        </TouchableOpacity>
        <View className="flex flex-col justify-center">
          <View className="mt-[30px]">
            <View className="w-full flex flex-row px-5 pt-3 border border-[#CFCFCF] rounded-md">
              {dataLinha?.map((parada: any) => (
                <View key={parada.cl} className="flex ">
                  <View>
                    <View className="w-[80px] flex items-center px-2 bg-greenPrimary text-[18px] rounded-md">
                      <Text className="text-[#fff] text-lg font-bold">
                        {parada.cl}
                      </Text>
                    </View>
                    <View className="w-full flex flex-row justify-between items-start pt-1">
                      <View className="w-[230px] flex flex-row flex-wrap">
                        <Text className="text-grayPrimary text-sm font-bold">
                          {parada.lt0} /{" "}
                        </Text>
                        <Text className="text-grayPrimary text-sm font-bold">
                          {parada.lt1}
                        </Text>
                      </View>
                      {parada.vs.length > 0 && (
                        <View className="flex flex-row items-center justify-center">
                          <Icon name="clock" size={25} color="#404040" />
                          <Text className="text-grayPrimary pl-1 text-lg font-bold">
                            {parada.vs[0].t}
                          </Text>
                        </View>
                      )}
                    </View>
                  </View>
                  <View>
                    {parada.vs.length > 0 && (
                      <View>
                        <View className="flex flex-row ">
                          <Text className="text-xs text-[#C7C7C7]">
                            Número do veículo:{" "}
                          </Text>
                          <Text className="text-[#C7C7C7] text-xs">
                            {parada.vs[0].p}
                          </Text>
                        </View>
                        <View className="flex flex-row justify-between pt-2 pb-3">
                          <TouchableWithoutFeedback
                            className="w-[160px] flex flex-row justify-center items-center bg-bluePrimary  rounded-[30px]"
                            onPress={() => {
                              setShowBusInMap(true);
                              setVehicleCoords({
                                py: parada.vs[0].py,
                                px: parada.vs[0].px,
                              });
                            }}
                          >
                            <Icon name="map" size={18} color="#fff" />
                            <Text className="py-2 text-[#fff] pl-2 text-xs font-bold">
                              Ver no mapa
                            </Text>
                          </TouchableWithoutFeedback>
                          <TouchableOpacity
                            className="w-[160px] flex flex-row justify-center items-center bg-bluePrimary  rounded-[30px]"
                            onPress={() => {
                              setShowParadasBusSelected(true);
                              setSelectedParadaCl(parada.cl);
                            }}
                          >
                            <Icon name="map-pin" size={18} color="#fff" />
                            <Text className="py-2 text-[#fff] pl-2 text-xs font-bold">
                              Paradas
                            </Text>
                          </TouchableOpacity>
                        </View>
                      </View>
                    )}
                  </View>
                </View>
              ))}
            </View>
            <View className="pt-5">
              <View className="w-full flex flex-row py-3 border border-[#CFCFCF] rounded-md">
                {dataLinha?.map((parada: any) => (
                  <View key={parada.cl} className="w-full flex ">
                    <View>
                      <View className="border-b pb-3 border-[#F2F2F2]">
                        <Text className="text-grayPrimary font-bold px-5">
                          Proximas chegadas programadas
                        </Text>
                      </View>
                      <View className="w-full flex flex-row justify-between items-start pt-1 ">
                        {parada.vs.length > 1 && (
                          <View className="flex flex-row items-center justify-center ">
                            {parada.vs
                              .slice(1)
                              .map((vsItem: any, index: number) => (
                                <View
                                  className="border-b border-[#F2F2F2]"
                                  key={index}
                                >
                                  <View className="w-full flex flex-row items-center justify-between py-3 px-5 ">
                                    <View className="flex flex-row items-center">
                                      <Icon
                                        name="bus"
                                        size={20}
                                        color="#404040"
                                      />
                                      <Text className="text-grayPrimary text-sm font-bold pl-1">
                                        {vsItem.p}
                                      </Text>
                                    </View>
                                    <View className="flex flex-row items-center">
                                      <Icon
                                        name="clock"
                                        size={18}
                                        color="#404040"
                                      />
                                      <Text className="text-grayPrimary pl-1 font-bold">
                                        {vsItem.t}
                                      </Text>
                                    </View>
                                  </View>
                                </View>
                              ))}
                          </View>
                        )}
                      </View>
                    </View>
                    <View></View>
                  </View>
                ))}
              </View>
            </View>
          </View>
        </View>
      </View>
      {showBusInMap && vehicleCoords && (
        <VeiculoInMap py={vehicleCoords.py} px={vehicleCoords.px} />
      )}
      {showParadasBusSelected && selectedParadaCl && (
        <ParadasBusSelected  cl={selectedParadaCl}/>
      )}
    </View>
  );
}
