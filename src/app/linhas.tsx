import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  FlatList,
  StyleSheet,
  TouchableOpacity,
} from "react-native";
import axios from "axios";
import { router, useLocalSearchParams, useNavigation } from "expo-router";
import { theme } from "../theme";
import { Ionicons } from "@expo/vector-icons";

interface Linha {
  cl: number;
  lc: boolean;
  lt: string;
  tl: number;
  sl: number;
  tp: string;
  ts: string;
}

const Linhas: React.FC = () => {
  const params = useLocalSearchParams();
  const [linhas, setLinhas] = useState<Linha[]>([]);
  const navigation = useNavigation();

  useEffect(() => {
    const handleBuscaPorTermo = async () => {
      try {
        const response = await axios.get<Linha[]>(
          `https://aiko-olhovivo-proxy.aikodigital.io/Linha/Buscar?termosBusca=${params.item}`,
        );
        setLinhas(response.data);
      } catch (error) {
        console.log("Erro ao buscar linhas:", error);
      }
    };
    handleBuscaPorTermo();
  }, [params.item]);

  const handleSearchParadas = (item: number) => {
    router.push({
      pathname: "/paradas",
      params: { cl: item },
    });
  };

  const renderItem = ({ item }: { item: Linha }) => (
    <TouchableOpacity
      style={styles.itemContainer}
      onPress={() => {
        handleSearchParadas(item.cl);
      }}
    >
      <Text style={[styles.itemText, { fontFamily: theme.fontFamily.bold }]}>
        Código: {item.cl}
      </Text>
      <Text style={styles.itemText}>Letreiro: {item.lt}</Text>
      <Text style={styles.itemText}>Terminal: {item.tp}</Text>
      <Text style={styles.itemText}>Sentido: {item.ts}</Text>
    </TouchableOpacity>
  );

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          style={styles.backButton}
        >
          <Ionicons name="arrow-back" size={24} color="white" />
        </TouchableOpacity>
        <Text style={styles.title}>Linhas Disponíveis para {params.item}</Text>
      </View>
      <FlatList
        data={linhas}
        renderItem={renderItem}
        keyExtractor={(item) => item.cl.toString()}
        style={styles.list}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    paddingTop: 60,
    backgroundColor: "#f0f0f0",
  },
  header: {
    flexDirection: "row",
    alignItems: "center",
    justifyContent: "space-evenly",
    marginVertical: 12,
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
  },
  backButton: {
    backgroundColor: theme.colors.dark_blue,
    padding: 6,
    borderRadius: 8,
  },
  list: {
    width: "100%",
  },
  itemContainer: {
    backgroundColor: "#fff",
    borderRadius: 8,
    padding: 16,
    marginBottom: 12,
    elevation: 4,
    width: "80%",
    alignSelf: "center",
  },
  itemText: {
    fontSize: 16,
    marginBottom: 8,
  },
});

export default Linhas;
