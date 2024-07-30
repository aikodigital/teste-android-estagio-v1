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
        const response = await api.get(`/Linha/Buscar?termosBusca=${searchTerm}`);
        console.log('API Response:', response.data);
        setLines(response.data);
      } else {
        console.error('Failed to authenticate');
      }
    } catch (error) {
    }
    setIsLoading(false);
  };

  return (
    <>
      <Header />
      <View style={styles.container}>
        <View style={styles.searchInput}>
          <View style={styles.input}>
            <TextInput
              placeholder="Digite o nome da Linha (ex: Lapa)"
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
            keyExtractor={(item) => item.cl.toString()}
            ListEmptyComponent={<Text>Nenhuma linha encontrada.</Text>}
            renderItem={({ item }) => (
              <TouchableOpacity style={styles.lineItem}>
                <View style={styles.conteudoLeft}>
                  <Ionicons name={"bus"} size={20} color={'#091833'} />
                </View>
                <View style={styles.conteudoRight}>
                  <Text style={styles.textItemStrong}>{item.lt}-{item.tl}</Text>
                  <Text>Número da Linha: {item.cl}</Text>
                  <Text>Terminal Principal: {item.tp}</Text>
                  <Text>Terminal Secundário: {item.ts}</Text>
                </View>

              </TouchableOpacity>
            )}
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
    margin: 6,
    paddingTop: 14,
    paddingBottom: 14,
    paddingLeft: 6,
    paddingRight: 6,
    backgroundColor: '#E2EFFF',
    borderRadius: 20,
    flexDirection: 'row',
    gap: 2,
  },

  conteudoLeft: {
    width: '8%',
  },

  conteudoRight: {
    width: '90%',
  },

  textItemStrong: {
    fontSize: 16,
    fontWeight: 'bold',
  },
});
