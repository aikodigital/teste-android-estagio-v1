import { useState } from 'react';
import { View, TextInput, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';


export function Home() {

  const [ searchTerm, setSearchTerm ] = useState('');
  const navigation = useNavigation();

  const pages = 
  [
    'Linhas de Ônibus', 'Paradas de Ônibus', 
    'Mapa de Posições de Veiculos', 'Previsão de chegada'
  ];

  function handleSearch() {

    const filteredPages = pages.filter(page =>
      page.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return filteredPages.map(page => (

        <TouchableOpacity
          key={page} 
          style={styles.button}
          onPress={() => navigation.navigate(page as any)}
        >
            <Text style={styles.text}>{page}</Text>
        </TouchableOpacity>

    ));
  };

  return (
    <View style={styles.container}>

      <TextInput
        style={styles.input}
        placeholder="Procure os dados"
        placeholderTextColor="#ffffffc3"
        value={searchTerm}
        onChangeText={setSearchTerm}
      />

      <View>{handleSearch()}</View>

    </View>
  );
}

const styles = StyleSheet.create({
  container: {
   flex: 1,
   justifyContent: "center",
   backgroundColor: '#29292E', 
  },

  input: {

    height: 40,
    margin: 20,
    borderColor: 'gray',
    color: "white",
    borderWidth: 1,
    marginBottom: 12,
    paddingHorizontal: 8,
    borderRadius: 5

  },

  button:{

    backgroundColor: "#00875F",
    borderRadius: 5,
    padding: 15,
    margin: 10,
  },

  text:{
    color: "#fff",
  }
});
