import React from 'react';
import { render, fireEvent } from '@testing-library/react-native';
import RealTime from '../src/layouts/RealTime'; // Atualize o caminho conforme necessário

test('deve exibir mensagem de erro se o campo estiver vazio ao buscar', () => {
  const { getByPlaceholderText, getByText } = render(<RealTime />);
  const button = getByText('Buscar');

  fireEvent.press(button);
  
  expect(getByText('Digite uma linha de ônibus:')).toBeTruthy();
});
