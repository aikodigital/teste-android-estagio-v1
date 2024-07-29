import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, FlatList, StyleSheet } from 'react-native';
import { useNavigation } from '@react-navigation/native';

import { SearchBusLine } from '../config/services/SearchBusLine'; 

const BusLines = () => {
  const navigation = useNavigation(); //Sera usada pra navegar ate o menu Paradas
  const [searchTerm, setSearchTerm] = useState('');
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (searchTerm.trim() === '') {
      setError('Digite uma linha de ônibus:');
      return;
    }
    
    setLoading(true);
    setError('');
    
    try {
      const data = await SearchBusLine(searchTerm);
      //console.log(data);
      setResults(data); 
    } catch (err) {
      setError('Erro ao buscar linhas de ônibus.');
      setResults([]);
    } finally {
      setLoading(false);
    }
  };

  //Trata opções do campo "TL" conforme descrito na documentação da API
  const getModeDescription = (tl) => {
    switch (tl) {
      case 10:
        return 'Base';
      case 41:
        return 'Atendimento';
      default:
        return 'Desconhecido';
    }
  };

  //Trata opções do campo "SL" conforme descrito na documentação da API
  const getTerminalDescription = (sl) => {
    switch (sl) {
      case 1:
        return 'Terminal Principal -> Terminal Secundário';
      case 2:
        return 'Terminal Secundário -> Terminal Principal';
      default:
        return 'Desconhecido'; 
    }
  };

  //Define formato de como os dados da linha pesquisada serão apresentados na tela
  const renderItem = ({ item }) => (
    <View style={styles.item}>
      <Text style={styles.itemText}>Código da Linha: <Text style={styles.boldText}>{item.cl}</Text></Text>
      <Text style={styles.itemText}>Nº da Linha: <Text style={styles.boldText}>{item.lt}</Text></Text>
      <Text style={styles.itemText}>Circular: <Text style={styles.boldText}>{item.lc ? 'Circular' : 'Não-Circular'}</Text></Text>
      <Text style={styles.itemText}>Sentido: <Text style={styles.boldText}>{getTerminalDescription(item.sl)}</Text></Text>
      <Text style={styles.itemText}>Modo: <Text style={styles.boldText}>{getModeDescription(item.tl)}</Text></Text>
      <Text style={styles.itemText}>Letreiro Prin/Sec: <Text style={styles.boldText}>{item.tp}</Text></Text>
      <Text style={styles.itemText}>Letreiro Sec/Princ: <Text style={styles.boldText}>{item.ts}</Text></Text>

      <TouchableOpacity 
        onPress={() => navigation.navigate('Paradas', { lineCode: item.cl })}
      >
        <Text style={styles.buttonTextStops}>Ver Paradas</Text>

      </TouchableOpacity>
    </View>
  );

  return (
    <View style={styles.container}>
      <TextInput
        placeholder="Digite o número da linha."
        value={searchTerm}
        onChangeText={setSearchTerm}
        style={styles.input}
      />
      <TouchableOpacity style={styles.button} onPress={handleSearch}>
        <Text style={styles.buttonText}>Buscar Linha</Text>
      </TouchableOpacity>
      
      {loading && <Text style={styles.loading}>Buscando...</Text>}
      
      {error ? (
        <Text style={styles.error}>{error}</Text>
      ) : results.length > 0 ? (
        <FlatList
          data={results}
          keyExtractor={(item) => item.cl.toString()}
          renderItem={renderItem}
        />
      ) : (
        <Text style={styles.placeholderText}>
          Utilize a caixa de pesquisa acima para buscar por uma linha de ônibus da cidade de São Paulo. As informações da linha escolhida serão exibidas aqui!
        </Text>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#fff',
  },
  input: {
    marginBottom: 10,
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 10,
    borderRadius: 5,
  },
  button: {
    backgroundColor: '#003184',
    borderRadius: 25,
    paddingVertical: 10,
    paddingHorizontal: 20,
    marginBottom: 20,
    alignItems: 'center',
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    fontWeight: 'bold',
  },
  loading: {
    marginTop: 10,
    textAlign: 'center',
  },
  error: {
    color: 'red',
    textAlign: 'center',
    marginTop: 10,
  },
  item: {
    padding: 15,
    borderRadius: 10,
    borderWidth: 1,
    borderColor: '#ddd',
    marginBottom: 10,
    backgroundColor: '#f9f9f9',
  },
  itemText: {
    fontSize: 14,
    marginBottom: 5,
  },
  boldText: {
    fontWeight: 'bold',
  },
  placeholderText: {
    marginTop: 20,
    fontSize: 16,
    color: '#888',
    textAlign: 'center',
  },
  buttonTextStops: {
    backgroundColor: '#d4edda', 
    borderRadius: 5,
    paddingVertical: 8,
    paddingHorizontal: 12,
    marginTop: 10,
    alignItems: 'center',
  }
});

export default BusLines;
