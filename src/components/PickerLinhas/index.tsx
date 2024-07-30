import React, { useState } from "react";
import { View, Text, FlatList, TouchableOpacity } from "react-native";
import { MaterialIcons } from "@expo/vector-icons";
import { LinhaResponse, PickerLinhasProps } from "./props";
import { styles } from "./styles";

const PickerLinhas: React.FC<PickerLinhasProps> = ({
  linhas,
  onSelectLinha,
  disabled,
  error,
}) => {
  const [isOpen, setIsOpen] = useState<boolean>(false);
  const [selectedLinhaId, setSelectedLinhaId] = useState<number | null>(null);

  const handleSelectLinha = (linhaId: number, linhaNome: string) => {
    if (!disabled) {
      setSelectedLinhaId(linhaId);
      onSelectLinha(linhaId);
      setIsOpen(false);
    }
  };

  // Filtra as linhas para não mostrar a linha selecionada com base no ID
  const filteredLinhas = linhas.filter((item) => item.cl !== selectedLinhaId);

  // Encontra o nome da linha selecionada
  const selectedLinha = linhas.find((linha) => linha.cl === selectedLinhaId);
  const displayText = selectedLinha
    ? `${selectedLinha.tp} - ${selectedLinha.ts}`
    : "Selecionar Linha";

  const renderOption = ({ item }: { item: LinhaResponse }) => (
    <TouchableOpacity
      style={[styles.itemButton, disabled && styles.disabledButton]}
      onPress={() => handleSelectLinha(item.cl, `${item.tp} - ${item.ts}`)}
      disabled={disabled}
    >
      <Text style={[styles.itemText, disabled && styles.disabledText]}>
        {`${item.tp} - ${item.ts}`}
      </Text>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <TouchableOpacity
        style={[styles.button, disabled && styles.disabledButton]}
        onPress={() => !disabled && setIsOpen(!isOpen)}
        disabled={disabled}
      >
        <Text style={[styles.buttonText, disabled && styles.disabledText]}>
          {displayText}
        </Text>
        <MaterialIcons
          name={isOpen ? "keyboard-arrow-up" : "keyboard-arrow-down"}
          size={24}
          color={disabled ? "gray" : "black"}
        />
      </TouchableOpacity>
      {isOpen && (
        <FlatList
          data={filteredLinhas}
          keyExtractor={(item) => item.cl.toString()}
          renderItem={renderOption}
          style={styles.list}
        />
      )}
      {error && <Text style={styles.errorText}>{error}</Text>}
    </View>
  );
};

export default PickerLinhas;
