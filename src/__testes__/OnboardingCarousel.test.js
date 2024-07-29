import React from 'react';
import { render, fireEvent } from '@testing-library/react-native';
import { NavigationContainer } from '@react-navigation/native';
import OnboardingCarousel from '../src/layouts/Onboarding'; // Atualize o caminho conforme necessário

test('deve navegar para a tela RealTime ao finalizar o onboarding', () => {
  const { getByText } = render(
    <NavigationContainer>
      <OnboardingCarousel />
    </NavigationContainer>
  );

  fireEvent.press(getByText('Seguinte')); // Pressione o botão Seguinte na primeira página
  fireEvent.press(getByText('Prosseguir para o app')); // Pressione o botão Prosseguir na última página
  
  // Verificar se a navegação ocorreu corretamente
  expect(mockNavigate).toHaveBeenCalledWith('RealTime');
});
