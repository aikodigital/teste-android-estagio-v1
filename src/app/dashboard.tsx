import React, { useState, useEffect } from "react";
import {
  Text,
  BackHandler,
  StyleSheet,
  KeyboardAvoidingView,
  Platform,
  Keyboard,
} from "react-native";
import { MotiText, MotiView } from "moti";
import { theme } from "../theme";
import axios from "axios";
import { router } from "expo-router";

import OptionButton from "../components/OptionButton";
import SearchInput from "../components/SearchInput";
import { Corredor } from "../types/types";

interface Props {}

const Dashboard: React.FC<Props> = () => {
  const [selectedOption, setSelectedOption] = useState<"linha" | "corredor">(
    "",
  );
  const [inputValue, setInputValue] = useState<string>("");
  const [showInput, setShowInput] = useState<boolean>(false);
  const [corredores, setCorredores] = useState<Corredor[]>([]);

  useEffect(() => {
    getCorredores();
    const keyboardDidHideListener = Keyboard.addListener(
      "keyboardDidHide",
      () => setShowInput(false),
    );
    const backAction = () => {
      return true;
    };
    const backHandler = BackHandler.addEventListener(
      "hardwareBackPress",
      backAction,
    );

    return () => {
      keyboardDidHideListener.remove();
      backHandler.remove();
    };
  }, []);

  const getCorredores = async () => {
    try {
      const response = await axios.get(
        "https://aiko-olhovivo-proxy.aikodigital.io/Corredor",
      );

      if (response.data) {
        const corredoresOrdenados = response.data.sort((a, b) => a.cc - b.cc);
        setCorredores(corredoresOrdenados);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleOptionChange = (option: "linha" | "corredor") => {
    setShowInput(false);

    setTimeout(() => {
      setSelectedOption(option);
      setInputValue("");
      setShowInput(true);
    }, 200);
  };

  const handleSearchLinha = (inputValue: string) => {
    if (!inputValue) return;
    router.navigate({
      pathname: "/linhas",
      params: { item: inputValue, type: "Linha" },
    });
  };
  const handleSearchCorredor = (inputValue: string) => {
    router.navigate({
      pathname: "/corredores",
      params: { nc: inputValue, type: "Corredor" },
    });
  };

  return (
    <KeyboardAvoidingView
      style={{ flex: 1 }}
      behavior={Platform.OS === "ios" ? "padding" : undefined}
      keyboardVerticalOffset={Platform.OS === "ios" ? 0 : 20}
    >
      <MotiView
        from={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        transition={{ type: "timing", duration: 1000 }}
        style={styles.container}
      >
        <Text style={styles.title}>
          Bem-vindo(a) ao Sistema de Transporte 🚌
        </Text>
        <MotiText
          from={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ type: "timing", duration: 1000 }}
          style={{
            marginVertical: 20,
            textAlign: "center",
            fontFamily: theme.fontFamily.medium,
          }}
        >
          Selecione uma opção abaixo para que possamos te ajudar a encontrar seu
          ônibus:
        </MotiText>

        <MotiView
          from={{ opacity: 0, translateY: -20 }}
          animate={{ opacity: 1, translateY: 0 }}
          delay={500}
          style={styles.optionContainer}
        >
          <OptionButton
            title="Rotas"
            iconName="bus"
            isSelected={selectedOption === "linha"}
            onPress={() => handleOptionChange("linha")}
          />
          <OptionButton
            title="Corredores"
            iconName="road"
            isSelected={selectedOption === "corredor"}
            onPress={() => handleOptionChange("corredor")}
          />
        </MotiView>

        {showInput && (
          <MotiView
            from={{ opacity: 0, translateY: -20 }}
            animate={{ opacity: 1, translateY: 0 }}
            delay={50}
            style={{ width: "100%" }}
          >
            <SearchInput
              selectedOption={selectedOption}
              inputValue={inputValue}
              setInputValue={setInputValue}
              handleSearchLinha={handleSearchLinha}
              handleSearchCorredor={handleSearchCorredor}
              corredores={corredores}
            />
          </MotiView>
        )}
      </MotiView>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: 20,
    paddingTop: 80,
    backgroundColor: theme.colors.white,
    alignItems: "center",
  },
  title: {
    fontSize: 24,
    fontFamily: theme.fontFamily.bold,
    marginBottom: 20,
    textAlign: "center",
  },
  optionContainer: {
    flexDirection: "row",
    justifyContent: "space-evenly",
    marginVertical: 20,
    width: "100%",
  },
});

export default Dashboard;
