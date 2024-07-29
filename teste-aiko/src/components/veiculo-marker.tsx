import React, { memo } from 'react';
import { Marker } from 'react-native-maps';
import { FontAwesome } from '@expo/vector-icons';
import { Linha, Veiculo } from '../types/types'; // Ajuste o caminho conforme necessário

interface VeiculoMarkerProps {
  linha: Linha;
  veiculo: Veiculo;
  index: number;
  onPress: (veiculo: Veiculo, linha: Linha) => void;
}

const VeiculoMarker: React.FC<VeiculoMarkerProps> = memo(({ linha, veiculo, onPress }) => {
  if (!veiculo.py || !veiculo.px) return null;

  return (
    <Marker
      onPress={() => onPress(veiculo, linha)}
      coordinate={{ latitude: veiculo.py, longitude: veiculo.px }}
      title={`Veículo ${veiculo.p}`}
    >
      <FontAwesome name='bus' size={10} color='red' />
    </Marker>
  );
});

export default VeiculoMarker;
