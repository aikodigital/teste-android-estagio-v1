import React, { useState } from 'react';
import { View, Text, TextInput, Button, FlatList, ActivityIndicator } from 'react-native';
import useOlhoVivoAPI from './src/hooks';

const BusStopsScreen: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const { busStops, loading, error, fetchBusStops } = useOlhoVivoAPI();

  const handleSearch = () => {
    fetchBusStops(searchTerm);
  };

  if (loading) {
    return <ActivityIndicator size="large" color="#0000ff" />;
  }

  if (error) {
    return <Text>Erro: {error.message}</Text>;
  }

  return (
    <View>
      <TextInput
        value={searchTerm}
        onChangeText={setSearchTerm}
        placeholder="Buscar paradas"
        style={{ height: 40, borderColor: 'gray', borderWidth: 1, marginBottom: 10 }}
      />
      <Button title="Buscar" onPress={handleSearch} />
      <FlatList
        data={busStops}
        keyExtractor={(item) => item.cp.toString()}
        renderItem={({ item }) => (
          <View>
            <Text>Nome: {item.np}</Text>
            <Text>Endereço: {item.ed}</Text>
            <Text>Latitude: {item.py}</Text>
            <Text>Longitude: {item.px}</Text>
          </View>
        )}
      />
    </View>
  );
};

export default BusStopsScreen;
