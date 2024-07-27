import ErrorComponent from '@/components/form/error/Error';
import Loading from '@/components/form/loading/Loading';
import { MapRegion, Parada } from '@/types/types';
import React from 'react';
import { StyleSheet } from 'react-native';
import MapView, { MapMarker } from 'react-native-maps';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  cordenadasSp: MapRegion;
  paradas: Parada[];
  inputDePesquisa: string;
};

const MapResult = ({
  formState,
  cordenadasSp,
  paradas,
  inputDePesquisa,
}: Props) => {
  if (formState.error) return <ErrorComponent messagem={formState.error} />;
  return formState.loading && !formState.error ? (
    <Loading />
  ) : (
    inputDePesquisa.length > 0 && paradas.length > 0 && (
      <MapView
        region={cordenadasSp}
        initialRegion={cordenadasSp}
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
