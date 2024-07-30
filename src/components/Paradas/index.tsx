import React from "react";
import { Text, FlatList, TouchableOpacity, View } from "react-native";
import { styles } from "./styles";
import { ParadasProps, Parada, Veiculo } from "./props";

const Paradas: React.FC<ParadasProps> = ({
  paradas,
  veiculos,
  onSelectParada,
  onSelectVeiculo,
}) => {
  const renderItem = ({ item }: { item: Parada | Veiculo }) => {
    if ("horario" in item) {
      const veiculo = item as Veiculo;
      return (
        <>
          <TouchableOpacity
            style={styles.itemVeiculo}
            onPress={() => onSelectVeiculo(veiculo)}
          >
            <Text
              style={styles.title}
            >{`VEICULO: ${veiculo.id}   Horário: ${veiculo.horario}`}</Text>
            <Text
              style={styles.description}
            >{`Deficiente: ${veiculo.deficiente ? "Sim" : "Não"}`}</Text>
          </TouchableOpacity>
        </>
      );
    } else {
      const parada = item as Parada;
      return (
        <View style={styles.backItemContainer}>
          <TouchableOpacity
            style={styles.itemContainer}
            onPress={() => onSelectParada(parada)}
          >
            <Text style={styles.title}>PARADA: {parada.np}</Text>
            <Text style={styles.description}>{parada.ed}</Text>
          </TouchableOpacity>
        </View>
      );
    }
  };

  return (
    <View style={styles.container}>
      <FlatList
        data={[...veiculos, ...paradas]} // Veículos no topo, seguidos por paradas
        renderItem={renderItem}
        keyExtractor={(item) =>
          "horario" in item ? item.id : item.cp.toString()
        }
      />
    </View>
  );
};

export default Paradas;
