import React, { memo } from 'react';
import { Marker } from 'react-native-maps';
import { FontAwesome } from '@expo/vector-icons';
import { Parada } from '../types/types'; // Ajuste o caminho conforme necessário

interface ParadaMarkerProps {
  parada: Parada;
  onPress: (parada: Parada) => void;
}

const ParadaMarker: React.FC<ParadaMarkerProps> = memo(({ parada, onPress }) => {
  if (!parada.py || !parada.px) return null;

  return (
    <Marker
      onPress={() => onPress(parada)}
      coordinate={{ latitude: parada.py, longitude: parada.px }}
      title={`Parada ${parada.np}`}
    >
      <FontAwesome name='bus' color='blue' size={10} />
    </Marker>
  );
});

export default ParadaMarker;
