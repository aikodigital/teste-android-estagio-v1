import React, {useEffect, useState} from 'react';
import {View, Text, FlatList, ActivityIndicator} from 'react-native';
import {fetchLinesData} from '../services/api';
import {linestyles} from '../styles/LinesScreen';
import LineButton from '../components/LineButton';
import SearchBar from '../components/SearchBar';
import {Line} from '../types/interfaces';

const LinesScreen = ({navigation}: any) => {
  const [lines, setLines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState<string>('');
  const [filteredLines, setFilteredLines] = useState<Line[]>([]);

  const fetchData = async () => {
    try {
      const data = await fetchLinesData();
      if (data) {
        setLines(data.l);
        setFilteredLines(data.l);
      } else {
        setError('Erro ao carregar dados das linhas');
      }
    } catch (error: any) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    if (searchQuery.length > 0) {
      const filtered = lines.filter(
        (line: Line) =>
          line.c.toString().toLowerCase().includes(searchQuery.toLowerCase()) ||
          line.cl
            .toString()
            .toLowerCase()
            .includes(searchQuery.toLowerCase()) ||
          line.lt0.toLowerCase().includes(searchQuery.toLowerCase()) ||
          line.lt1.toLowerCase().includes(searchQuery.toLowerCase()),
      );
      setFilteredLines(filtered);
    } else {
      setFilteredLines(lines);
    }
  }, [searchQuery, lines]);

  const handlePress = (line: Line) => {
    navigation.navigate('Map', {line, fetchLines: fetchData});
  };

  if (loading) {
    return (
      <View style={linestyles.center}>
        <ActivityIndicator size="large" color="red" />
      </View>
    );
  }

  if (error) {
    return (
      <View style={linestyles.center}>
        <Text style={linestyles.error}>{error}</Text>
      </View>
    );
  }

  return (
    <View style={linestyles.container}>
      <SearchBar
        value={searchQuery}
        onChange={(newValue: string) => {
          setSearchQuery(newValue);
        }}
      />

      <FlatList
        data={filteredLines}
        renderItem={({item}) => (
          <LineButton line={item} onPress={handlePress} />
        )}
        keyExtractor={item => item.cl.toString()}
      />
    </View>
  );
};

export default LinesScreen;
