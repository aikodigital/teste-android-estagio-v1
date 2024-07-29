import React, { useState, useEffect } from "react";
import { View, Text, TextInput } from "react-native";
import EvilIcons from "@expo/vector-icons/EvilIcons";
import MapComponent from "../../components/Map";
import { Parada } from "../../components/Map/props";
import Paradas from "../../components/Paradas";
import PickerLinhas from "../../components/PickerLinhas";
import { fetchLinhasWithParadas } from "../../services/fetchLinhasWithParadas";
import { fetchParadas } from "../../services/fetchParadas";
import { fetchPosicoesVeiculos } from "../../services/fetchVeiculos";
import { LinhaResponse, ParadaResponse } from "./props";
import { styles } from "./styles";

export default function MenuScreen() {
  const [, setLinhas] = useState<LinhaResponse[]>([]);
  const [filteredLinhas, setFilteredLinhas] = useState<LinhaResponse[]>([]);
  const [paradas, setParadas] = useState<ParadaResponse[]>([]);
  const [veiculos, setVeiculos] = useState<any[]>([]);
  const [selectedLine, setSelectedLine] = useState<number | null>(null);
  const [selectedParada, setSelectedParada] = useState<Parada | undefined>(
    undefined,
  );
  const [latitude, setLatitude] = useState<number>(-23.532847);
  const [longitude, setLongitude] = useState<number>(-46.657404);
  const [isDisabled, setIsDisabled] = useState<boolean>(false);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const [error, setError] = useState<string | null>(null);
  const [horario, setHorario] = useState<string>("");

  const loadLinhas = async (searchTerm: string) => {
    try {
      const response = await fetchLinhasWithParadas(searchTerm, 5);
      if (response) {
        setLinhas(response);
        setFilteredLinhas(response);
        setError(null);
      } else {
        setFilteredLinhas([]);
        setError("Nenhuma linha encontrada para o termo de pesquisa.");
      }
    } catch (error: any) {
      console.error("Erro ao buscar linhas com paradas:", error.message);
      setError("Erro ao buscar linhas.");
    }
  };

  const loadParadas = async (codigoLinha: number) => {
    try {
      const response = await fetchParadas(codigoLinha);
      if (response) {
        setParadas(response);
        setError(null);
      } else {
        setParadas([]);
        setError("Nenhuma parada encontrada para a linha.");
      }
    } catch (error: any) {
      console.error("Erro ao buscar paradas:", error.message);
      setError("Erro ao buscar paradas.");
    }
  };

  const loadVeiculos = async (codigoLinha: number) => {
    try {
      const response = await fetchPosicoesVeiculos(codigoLinha);
      if (response) {
        setVeiculos(
          response.veiculos.map((v: any) => ({
            id: v.p,
            latitude: v.py,
            longitude: v.px,
            horario: v.ta,
            deficiente: v.a,
          })),
        );
        setHorario(response.hr); // Definindo o estado do horário
        setError(null);
      } else {
        setVeiculos([]);
        setHorario(""); // Limpando o horário se nenhum veículo for encontrado
        setError("Nenhuma posição de veículo encontrada para a linha.");
      }
    } catch (error: any) {
      console.error("Erro ao buscar posições dos veículos:", error.message);
      setError("Erro ao buscar posições dos veículos.");
    }
  };

  useEffect(() => {
    loadLinhas("8000");
  }, []);

  useEffect(() => {
    if (searchTerm) {
      loadLinhas(searchTerm);
      setSelectedLine(null);
      setParadas([]);
      setVeiculos([]);
    }
  }, [searchTerm]);

  useEffect(() => {
    if (selectedLine !== null) {
      setIsDisabled(true);
      setSelectedParada(undefined);
      loadParadas(selectedLine);
      loadVeiculos(selectedLine);
      const timer = setTimeout(() => {
        setIsDisabled(false);
      }, 5000);

      return () => clearTimeout(timer);
    } else {
      setIsDisabled(false);
    }
  }, [selectedLine]);

  useEffect(() => {
    if (selectedParada) {
      setLatitude(selectedParada.py);
      setLongitude(selectedParada.px);
    } else if (veiculos.length > 0) {
      setLatitude(veiculos[0].latitude);
      setLongitude(veiculos[0].longitude);
    }
  }, [selectedParada, veiculos]);

  return (
    <View style={styles.container}>
      <PickerLinhas
        linhas={filteredLinhas}
        onSelectLinha={setSelectedLine}
        disabled={isDisabled}
        error={error}
      />
      <MapComponent
        latitude={latitude}
        longitude={longitude}
        paradas={paradas.map((p) => ({
          cp: p.cp,
          np: p.np,
          ed: p.ed,
          px: p.px,
          py: p.py,
        }))}
        veiculos={veiculos.map((v) => ({
          id: v.id,
          latitude: v.latitude,
          longitude: v.longitude,
          horario: horario,
          deficiente: v.deficiente,
        }))}
        selectedParada={selectedParada}
      />
      <View style={styles.searchContainer}>
        <TextInput
          style={[
            styles.searchInput,
            error ? { borderColor: "red", borderWidth: 1 } : {},
          ]}
          placeholder="Buscar linhas..."
          value={searchTerm}
          onChangeText={(text) => {
            setSearchTerm(text);
            if (text === "") {
              setFilteredLinhas([]);
              setError(null);
            }
          }}
        />
        <EvilIcons
          style={styles.searchIcon}
          name="search"
          size={30}
          color="#ccc"
        />
      </View>
      {selectedLine !== null ? (
        <Paradas
          paradas={paradas}
          veiculos={veiculos}
          onSelectParada={setSelectedParada}
          onSelectVeiculo={(veiculo) => {
            setLatitude(veiculo.latitude);
            setLongitude(veiculo.longitude);
            setSelectedParada(undefined);
          }}
        />
      ) : (
        <View style={styles.noContainer}>
          <Text style={styles.noText}>Nenhuma linha selecionada</Text>
        </View>
      )}
    </View>
  );
}
