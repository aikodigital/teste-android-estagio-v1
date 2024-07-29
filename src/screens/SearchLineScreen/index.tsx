import React, {useState} from 'react';
import styles from './style';
import {View, FlatList, Text, TouchableOpacity} from 'react-native';
import {getLines} from '../../services/api';
import {Lines} from '../../services/api';
import ButtonPrimary from '../../components/ButtonPrimary';
import InputPrimary from '../../components/InputPrimary';

const SearchLineScreen: React.FC<{navigation: any}> = ({navigation}) => {
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [lines, setLines] = useState<Lines[]>([]);

  const handleSearch = async () => {
    const result = await getLines(searchTerm);
    setLines(result);
  };

  return (
    <View style={styles.container}>
      <InputPrimary
        placeholder="Nome ou Código da Linha"
        value={searchTerm}
        onChangeText={setSearchTerm}
      />
      <View style={styles.containerButton}>
        <ButtonPrimary title="Realizar Busca" onPress={handleSearch} />
      </View>
      <FlatList
        data={lines}
        keyExtractor={item => item.cl.toString()}
        renderItem={({item}) => (
          <View style={styles.itemContainer}>
            <Text style={styles.itemTextMain}>
              {item.lt}-{item.tl}
            </Text>
            <Text style={styles.itemTextMain}>
              {item.sl === 1 ? `${item.tp}` : `${item.ts}`}
            </Text>
            <Text style={styles.itemText}>
              {item.tp} - {item.ts}
            </Text>
            <View style={styles.linkContainer}>
              <TouchableOpacity
                onPress={() =>
                  navigation.navigate('LineDetailsScreen', {lineId: item.cl})
                }>
                <Text style={styles.buttonText}>Ver Paradas</Text>
              </TouchableOpacity>
            </View>
            <View style={styles.linkContainer}>
              <TouchableOpacity
                onPress={() =>
                  navigation.navigate('LineMapScreen', {lineId: item.cl})
                }>
                <Text style={styles.buttonText}>Ver Mapa</Text>
              </TouchableOpacity>
            </View>
          </View>
        )}
      />
    </View>
  );
};

export default SearchLineScreen;
