import Erro from '@/components/form/error/Erro';
import Carregando from '@/components/form/loading/Carregando';
import { MapRegion, Parada } from '@/types/types';
import React from 'react';
import { StyleSheet } from 'react-native';
import MapView, { MapMarker } from 'react-native-maps';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  coordenadasSp: MapRegion;
  paradas: Parada[];
  inputDePesquisa: string;
};

const MapResult = ({
  formState,
  coordenadasSp,
  paradas,
  inputDePesquisa,
}: Props) => {
  if (formState.error) return <Erro messagem={formState.error} />;
  return formState.loading && !formState.error ? (
    <Carregando />
  ) : (
    inputDePesquisa.length > 0 && paradas.length > 0 && (
      <MapView
        region={coordenadasSp}
        initialRegion={coordenadasSp}
        style={styles.map}
      >
        {paradas?.map(({ px, py, np }, i) => (
          <MapMarker
            key={i}
            tracksViewChanges={false}
            coordinate={{ latitude: py, longitude: px }}
            style={{ opacity: 0.5 }}
            title={np}
          />
        ))}
      </MapView>
    )
  );
};

export default MapResult;

const styles = StyleSheet.create({
  map: {
    width: '100%',
    height: '100%',
  },
});
