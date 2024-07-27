import {  View } from "react-native";
import { TextInput  } from "react-native-gesture-handler";
import Icon from "react-native-vector-icons/FontAwesome5";

export const Rotas = () => {
  return (
    <View className="pt-5">
      <View>
        <View className="w-full h-[50px] flex flex-row justify-between items-center mt-[18px] bg-[#fff] border border-[#F2F2F2] rounded-[30px] pl-5 shadow-lg shadow-[#000]">
          <TextInput
            className="w-[300px] font-semibold text-grayPrimary text-lg"
            placeholder="Para onde deseja ir?"
          />
          <View className="pr-4">
            <Icon name="search" size={30} color="#949494" />
          </View>
        </View>
      </View>
    </View>
  );
};
