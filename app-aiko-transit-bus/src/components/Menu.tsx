import { Text, View, Image, Touchable } from "react-native";
import { GlobalContext } from "../context/GlobalContext";
import { useContext } from "react";

import aiko from "../assets/aiko.png";
import close from "../assets/close.png";
import Icon from "react-native-vector-icons/FontAwesome";
import { TouchableOpacity } from "react-native-gesture-handler";
import { useNavigation } from "@react-navigation/native";
import { NativeStackNavigationProp } from "@react-navigation/native-stack";
import { RootStackParamList } from "../Routes";

type NavigationProp = NativeStackNavigationProp<RootStackParamList, "Home">;

export const Menu = () => {
  const { setShowMenu, setRowOrSearch } = useContext(GlobalContext);

  const navigation = useNavigation<NavigationProp>();

  return (
    <View className="w-full h-full flex bg-[#fff] absolute top-0 bottom-0 left-0 right-0">
      <View className="h-[160px] border-b border-[#F2F2F2] px-5">
        <View className="pt-[80px] ">
          <View className="flex flex-row justify-between items-end">
            <View className="flex flex-row items-end ">
              <Image source={aiko} className="w-[114px] h-[57px]" />
              <Text className="font-bold text-bluePrimary pr-1 relative bottom-[2px]">
                Transit
              </Text>
              <View className="relative bottom-[3px]">
                <Icon name="bus" size={14} color="#00DF00" />
              </View>
            </View>
            <TouchableOpacity onPress={() => setShowMenu(false)}>
              <Image source={close} className="w-[40px]" />
            </TouchableOpacity>
          </View>
        </View>
        <View className=" pt-[40px]">
          <View className="flex flex-col gap-[44px]">
            <TouchableOpacity className="flex flex-row gap-4 items-center" onPress={()=>  setShowMenu(false)}>
              <Icon name="map" size={36} color="#003184" />
              <Text className="text-xl text-[#404040]">Mapa</Text>
            </TouchableOpacity>
            <TouchableOpacity
              className="flex flex-row gap-4 items-center"
              onPress={() => {
                setRowOrSearch("rotas");
                navigation.navigate("Busca");
              }}
            >
              <Icon name="road" size={36} color="#003184" />
              <Text className="text-xl text-[#404040]">Rotas</Text>
            </TouchableOpacity>
            <TouchableOpacity
              className="flex flex-row gap-4 items-center"
              onPress={() => {
                setRowOrSearch("linhas");
                navigation.navigate("Busca");
              }}
            >
              <Icon name="search" size={36} color="#003184" />
              <Text className="text-xl text-[#404040]">Buscar linha</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
    </View>
  );
};
