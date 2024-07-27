import { EvilIcons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, TextInput, View } from 'react-native';

type Props = {
  inputDePesquisa: string;
  setInputDePesquisa: React.Dispatch<React.SetStateAction<string>>;
  lidarComPesquisa: () => void;
};

const SearchInput = ({
  inputDePesquisa,
  setInputDePesquisa,
  lidarComPesquisa,
}: Props) => {
  return (
    <View style={styles.container}>
      <EvilIcons style={styles.icon} name='search' size={24} color='black' />
      <TextInput
        style={styles.input}
        placeholder='Ex: 8000'
        value={inputDePesquisa}
        onChangeText={setInputDePesquisa}
        onChange={lidarComPesquisa}
      />
    </View>
  );
};

export default SearchInput;

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 10,
    paddingHorizontal: 10,
    marginVertical: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 2,
  },
  icon: {
    marginBottom: 4,
    marginRight: 8,
  },
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#333',
  },
});
