import React, { useState, useEffect } from 'react';
import { FlatList, ActivityIndicator, TouchableOpacity } from 'react-native';
import styled from 'styled-components/native';
import axios from 'axios';
import { authenticate } from '../api/authContext'; 

const API_URL = 'https://api.olhovivo.sptrans.com.br/v2.1';

const BusLinesScreen = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [busLines, setBusLines] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [inputValue, setInputValue] = useState('');
  const [error, setError] = useState(null); 

  useEffect(() => {
    const authenticateUser = async () => {
      setLoading(true); 
      try {
        const auth = await authenticate();
        setIsAuthenticated(auth);
        if (auth) {
          console.log('Autenticado com sucesso!');
        }
      } catch (e) {
        console.error('Erro na autenticação:', e);
        setError('Erro na autenticação. Tente novamente.'); 
      } finally {
        setLoading(false);
      }
    };

    authenticateUser();
  }, []);

  const fetchBusLines = async () => {
    if (!isAuthenticated || !searchTerm) return;

    setLoading(true);
    try {
      const response = await axios.get(`${API_URL}/Linha/Buscar`, {
        params: { termosBusca: searchTerm },
      });

      setBusLines(response.data);
      // console.log('Linhas de ônibus:', response.data);
    } catch (error) {
      console.error('Erro ao buscar linhas:', error);
      setError('Erro ao buscar linhas. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = () => {
    setSearchTerm(inputValue.trim()); 
    if (inputValue.trim()) {
      fetchBusLines();
    }
  };

  return (
    <Container>
      {loading && (
        <LoadingOverlay>
          <ActivityIndicator size="large" color={colors.primary} />
        </LoadingOverlay>
      )}

      <Header>Linhas de Ônibus</Header>
      
      {error && <ErrorText>{error}</ErrorText>}

      <InputContainer>
        <Input
          placeholder="Digite o código da linha"
          value={inputValue}
          onChangeText={setInputValue}
        />
        <SearchButton onPress={handleSearch}>
          <ButtonText>Buscar</ButtonText>
        </SearchButton>
      </InputContainer>

      <FlatList
        data={busLines}
        keyExtractor={(item) => item.cl.toString()}
        renderItem={({ item }) => (
          <ItemContainer>
            <ItemText>
              {`Cod: ${item.cl} - OP: ${item.tl} - Linha: ${item.lt}\n`}
              {`Terminal: ${item.tp}\n`}
              {`Secundário: ${item.ts}\n`}
              {`Sentido: ${item.sl}\n`}
            </ItemText>
          </ItemContainer>
        )}
        initialNumToRender={10}
        windowSize={5}
        removeClippedSubviews={true}
      />
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
  line-height: 24px; 
  flex-wrap: wrap; 
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

const SearchButton = styled(TouchableOpacity)`
  background-color: ${colors.primary};
  border-radius: 4px;
  padding: 10px;
  align-items: center;
`;

const ButtonText = styled.Text`
  color: ${colors.background};
  font-size: 16px;
`;

const LoadingOverlay = styled.View`
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(255, 255, 255, 0.8);
  justify-content: center;
  align-items: center;
`;


const ErrorText = styled.Text`
  color: ${colors.error};
  text-align: center;
  margin-bottom: 10px;
`;

export default BusLinesScreen;
