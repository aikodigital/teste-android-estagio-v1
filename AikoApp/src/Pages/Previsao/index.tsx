// Linhas.tsx
import React, { useState, useEffect } from 'react';
import { View, Text, TextInput, StyleSheet, FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import Header from '../../components/Header';
import api from '../../services/api';
import authenticate from '../../services/auth';
import { useRoute } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';

export default function Linhas() {
  const route = useRoute();
  const [searchTerm, setSearchTerm] = useState('');
  const [lines, setLines] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    console.log('Lines state updated:', lines);
  }, [lines]);

  const handleSearch = async () => {
    setIsLoading(true);
    try {
      const authResponse = await authenticate();
      console.log('Authentication Response:', authResponse);

      if (authResponse) {
        const response = await api.get(`/Previsao/Parada?codigoParada=${searchTerm}`);
        console.log('API Response:', response.data);

        if (response.data && response.data.p && response.data.p.l) {
          const flattenedLines = response.data.p.l.flatMap(line =>
            line.vs.map(veiculo => ({
              ...veiculo,
              linha: line.c,
              nomeParada: response.data.p.np
            }))
          );

          setLines(flattenedLines);
        } else {
          console.error('Resposta da API não tem a estrutura esperada:', response.data);
          setLines([]);
        }
      } else {
        console.error('Failed to authenticate');
        setLines([]);
      }
    } catch (error) {
      setLines([]);
    }
    setIsLoading(false);
  };

  const renderItem = ({ item }) => (
    <TouchableOpacity style={styles.lineItem}>
      <View style={styles.conteudoRight}>
        <Text style={styles.textItemStrong}>Previsão de chegada: {item.t}</Text>
        <Text>Número da Linha: {item.linha}</Text>
        <Text>Parada: {item.nomeParada}</Text>
      </View>
    </TouchableOpacity>
  );

  return (
    <>
      <Header />
      <View style={styles.container}>

        <View style={styles.searchInput}>
          <View style={styles.input}>
            <TextInput
              placeholder="Digite o código de parada (ex: 700016623)"
              value={searchTerm}
              keyboardType='ascii-capable'
              onChangeText={setSearchTerm}
              onSubmitEditing={handleSearch}
            />
          </View>
          <View style={styles.search}>
            <TouchableOpacity onPress={() => handleSearch()} >
              <Ionicons name={"search"} size={30} color={'#091833'} />
            </TouchableOpacity>
          </View>
        </View>

        {isLoading ? (
          <ActivityIndicator color="#091833" size="large" />
        ) : (
          <FlatList
            data={lines}
            keyExtractor={(item, index) => index.toString()}
            renderItem={renderItem}
            ListEmptyComponent={<Text>Nenhuma previsão encontrada.</Text>}
          />
        )}
      </View>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingLeft: 10,
    paddingRight: 10,
  },
  searchInput: {
    flexDirection: 'row',
    borderColor: '#091833',
    borderWidth: 0.2,
    borderRadius: 10,
    marginBottom: 8,
    backgroundColor: '#f8f8f8',
    justifyContent: 'space-around',
    alignSelf: 'center',
    alignContent: 'center',
    alignItems: 'center',
    width: '80%',
    padding: 8,
  },
  input: {
    width: '80%',
  },
  search: {
    width: '10%',
  },
  lineItem: {
    padding: 20,
    backgroundColor: '#E2EFFF',
    borderRadius: 20,
    flexDirection: 'row',
    width: '100%',
    alignSelf: 'center',
    margin: 6,
    gap: 2,
  },
  conteudoRight: {
    width: '90%',
    gap: 6,
  },
  textItemStrong: {
    fontSize: 18,
    fontWeight: 'bold',
  },
});
