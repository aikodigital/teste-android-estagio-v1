import React from 'react';
import { render } from '@testing-library/react-native';
import SplashScreen from '../src/screens/SplashScreen'; // Atualize o caminho conforme necessário

test('deve renderizar a tela de splash corretamente', () => {
  const { getByText, getByAltText } = render(<SplashScreen />);
  
  expect(getByText('SPTrans - Olho Vivo')).toBeTruthy();
  expect(getByText('por Fernando Santiago')).toBeTruthy();
  expect(getByAltText('Aiko logo')).toBeTruthy();
});