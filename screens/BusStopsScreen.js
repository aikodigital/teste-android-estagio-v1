import React, { useState, useEffect, useCallback } from 'react';
import { FlatList, ActivityIndicator, TouchableOpacity, View, Text, ScrollView, Alert, Dimensions } from 'react-native';
import styled from 'styled-components/native';
import { authenticate } from '../api/authContext';
import { fetchStops, fetchArrivalPrediction } from '../api/Service';

const { width: screenWidth } = Dimensions.get('window');

const BusStopsScreen = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [stops, setStops] = useState([]);
  const [loading, setLoading] = useState(false);
  const [inputValue, setInputValue] = useState('');
  const [selectedStop, setSelectedStop] = useState(null);
  const [arrivalPrediction, setArrivalPrediction] = useState(null);
  const [isPredictionVisible, setIsPredictionVisible] = useState(false);

  useEffect(() => {
    const authenticateUser = async () => {
      const auth = await authenticate();
      setIsAuthenticated(auth);
      if (auth) {
        console.log('Autenticado com sucesso!');
      } else {
        console.error('Falha na autenticação');
      }
    };

    authenticateUser();
  }, []);

  const handleSearchStops = useCallback(async () => {
    if (!isAuthenticated) {
      Alert.alert('Erro', 'Usuário não autenticado');
      return;
    }

    if (!inputValue) {
      Alert.alert('Erro', 'Por favor, insira um código de parada');
      return;
    }

    setLoading(true);
    try {
      const data = await fetchStops(inputValue);
      setStops(data);
    } catch (error) {
      Alert.alert('Erro', 'Erro ao buscar paradas.');
    } finally {
      setLoading(false);
    }
  }, [isAuthenticated, inputValue]);

  const handleStopPress = useCallback(async (stop) => {
    setSelectedStop(stop);
    setIsPredictionVisible(true); 
    try {
      const prediction = await fetchArrivalPrediction(stop.cp);
      setArrivalPrediction(prediction);
    } catch (error) {
      Alert.alert('Erro', 'Não foi possível obter a previsão de chegada.');
    }
  }, []);

  const togglePredictionVisibility = useCallback(() => {
    setIsPredictionVisible(prev => !prev);
  }, []);

  const renderListItem = ({ item }) => {
    const { cp, ed, np } = item; 

    return (
      <ItemContainer>
        <TouchableOpacity onPress={() => handleStopPress(item)}>
          <ItemText>
            {`Código Parada: ${cp || 'N/A'}\n`}
            {`Nome: ${np || 'N/A'}\n`}
            {`Endereço: ${ed || 'N/A'}\n`}
          </ItemText>
        </TouchableOpacity>
      </ItemContainer>
    );
  };

  const keyExtractor = (item, index) => {
    return item.cp !== undefined ? item.cp.toString() : index.toString();
  };

  if (loading) {
    return (
      <Container>
        <ActivityIndicator size="large" color={colors.primary} />
      </Container>
    );
  }

  return (
    <Container>
      <Header>Paradas de Ônibus</Header>
      <InputContainer>
        <Input
          placeholder="Código da Parada"
          value={inputValue}
          onChangeText={setInputValue}
        />
        <SearchButton onPress={handleSearchStops}>
          <ButtonText>Buscar Paradas</ButtonText>
        </SearchButton>
      </InputContainer>
      {stops.length === 0 ? (
        <View style={{ alignItems: 'center', marginTop: 20 }}>
          <Text style={{ color: colors.text }}>Nenhuma parada encontrada</Text>
        </View>
      ) : (
        <FlatList
          data={stops}
          keyExtractor={keyExtractor}
          renderItem={renderListItem}
        />
      )}
      {isPredictionVisible && arrivalPrediction && selectedStop && (
        <PredictionContainer>
          <TouchableOpacity onPress={togglePredictionVisibility}>
            <CloseButtonText>Fechar</CloseButtonText>
          </TouchableOpacity>
          <ScrollView>
            <PredictionContent>
              <Text style={styles.predictionHeader}>Previsão de Chegada:</Text>
              <Text style={[styles.predictionText, { maxWidth: screenWidth - 40 }]}>
                {`Código Parada: ${selectedStop.cp}\n`}
                {`Nome: ${selectedStop.np}\n`}
                {`Endereço: ${selectedStop.ed}\n`}
                {`Horário Atual: ${arrivalPrediction.hr || 'Não disponível'}\n`}
                {`Linhas:\n`}
                {arrivalPrediction.p.l.map((line, index) => (
                  <View key={index}>
                    <Text>{`Linha: ${line.c} - ${line.lt0} / ${line.lt1}`}</Text>
                    {line.vs.map((vehicle, vIndex) => (
                      <Text key={vIndex}>
                        {`Veículo: ${vehicle.p} - Previsão: ${vehicle.t}`}
                      </Text>
                    ))}
                  </View>
                ))}
              </Text>
            </PredictionContent>
          </ScrollView>
        </PredictionContainer>
      )}
    </Container>
  );
};

const colors = {
  primary: '#007bff',
  secondary: '#6c757d',
  background: '#f8f9fa',
  text: '#212529',
  border: '#ccc',
  accent: '#28a745',
  error: '#dc3545',
};

const Container = styled.View`
  flex: 1;
  padding: 20px;
  background-color: ${colors.background};
`;

const Header = styled.Text`
  font-size: 24px;
  margin-bottom: 20px;
  font-weight: bold;
  text-align: center;
  color: ${colors.primary};
`;

const ItemContainer = styled.View`
  padding: 10px;
  border-bottom-width: 1px;
  border-bottom-color: ${colors.border};
`;

const ItemText = styled.Text`
  font-size: 16px;
  color: ${colors.text};
`;

const InputContainer = styled.View`
  margin-bottom: 20px;
`;

const Input = styled.TextInput`
  height: 40px;
  border-color: ${colors.border};
  border-width: 1px;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 10px;
`;

const SearchButton = styled.TouchableOpacity`
  background-color: ${colors.primary};
  border-radius: 4px;
  padding: 10px;
  align-items: center;
`;

const ButtonText = styled.Text`
  color: ${colors.background};
  font-size: 16px;
`;

const PredictionContainer = styled.View`
  margin-top: 20px;
  padding: 10px;
  border-width: 1px;
  border-color: ${colors.border};
  border-radius: 4px;
  max-height: 400px; /* Ajuste conforme necessário */
`;

const PredictionContent = styled.View`
  flex-direction: column;
`;

const CloseButtonText = styled.Text`
  font-size: 16px;
  color: ${colors.primary};
  text-align: right;
  margin-bottom: 10px;
`;

const styles = {
  predictionHeader: {
    fontSize: 18,
    fontWeight: 'bold',
    color: colors.primary,
  },
  predictionText: {
    fontSize: 16,
    color: colors.text,
    flexWrap: 'wrap', // Permite a quebra de linha
  },
};

export default BusStopsScreen;
