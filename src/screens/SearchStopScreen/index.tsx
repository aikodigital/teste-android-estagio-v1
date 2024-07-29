import React, {useState} from 'react';
import {View, FlatList, Text, TouchableOpacity} from 'react-native';
import {getStops} from '../../services/api';
import {Stops} from '../../services/api';
import ButtonPrimary from '../../components/ButtonPrimary';
import InputPrimary from '../../components/InputPrimary';
import styles from './style';

const SearchStopScreen: React.FC<{navigation: any}> = ({navigation}) => {
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [stops, setStops] = useState<Stops[]>([]);

  const handleSearch = async () => {
    const result = await getStops(searchTerm);
    setStops(result);
  };

  return (
    <View style={styles.container}>
      <InputPrimary
        placeholder="Nome ou Endereço da Parada"
        value={searchTerm}
        onChangeText={setSearchTerm}
      />
      <View style={styles.containerButton}>
        <ButtonPrimary title="Realizar Busca" onPress={handleSearch} />
      </View>
      <FlatList
        data={stops}
        keyExtractor={item => item.cp.toString()}
        renderItem={({item}) => (
          <View style={styles.itemContainer}>
            <Text style={styles.itemTextMain}>{item.np || item.cp}</Text>
            <Text
              style={styles.itemText}>{`Código da Parada: ${item.cp}`}</Text>
            <Text style={styles.itemText}>{`Endereço: ${item.ed}`}</Text>
            <View style={styles.linkContainer}>
              <TouchableOpacity
                onPress={() =>
                  navigation.navigate('StopDetailsScreen', {stopId: item.cp})
                }>
                <Text style={styles.buttonText}>Ver Linhas</Text>
              </TouchableOpacity>
            </View>
          </View>
        )}
      />
    </View>
  );
};

export default SearchStopScreen;
