import React from 'react';
import styled from 'styled-components/native';
import Icon from 'react-native-vector-icons/FontAwesome5';



const HomeScreen = () => {
  return (
    <Container>
      <IconContainer>
        <Icon name="bus-alt" size={80} color={colors.secondary} />
      </IconContainer>
      <Title>RotaMobile</Title>
      <Description>
        Encontre as melhores rotas e horários para sua viagem.
      </Description>
      <AppDescription>
        Este aplicativo é específico para a cidade de São Paulo, oferecendo informações detalhadas sobre as linhas e paradas.
      </AppDescription>
    </Container>
  );
};

const colors = {
  primary: '#003366',
  secondary: '#FFCC00',
  background: '#F5F5F5',
  text: '#333333',
  white: '#FFFFFF',
};

const Container = styled.View`
  flex: 1;
  justify-content: center;
  align-items: center;
  background-color: ${colors.background}; 
  padding: 16px;
`;

const IconContainer = styled.View`
  margin-bottom: 24px;
  background-color: ${colors.primary};
  border-radius: 50px;
  padding: 16px;
  justify-content: center;
  align-items: center;
`;

const Title = styled.Text`
  font-size: 28px;
  font-weight: bold;
  color: ${colors.primary};
  margin-bottom: 16px;
`;

const Description = styled.Text`
  font-size: 16px;
  color: ${colors.text};
  margin-bottom: 16px;
  text-align: center;
`;

const AppDescription = styled.Text`
  font-size: 14px;
  color: ${colors.text}; 
  margint-top: 50px; 
  margin-bottom: 24px;
  text-align: center;
  padding: 0 20px;
`;


export default HomeScreen;
