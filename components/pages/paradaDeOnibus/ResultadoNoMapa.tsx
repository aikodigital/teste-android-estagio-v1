import Erro from '@/components/form/error/Erro';
import Carregando from '@/components/form/loading/Carregando';
import { RegiaoDoMapa, Parada } from '@/types/types';
import React from 'react';
import { StyleSheet } from 'react-native';
import MapView, { MapMarker } from 'react-native-maps';

type Props = {
  formState: {
    loading: boolean;
    error: string;
  };
  coordenadasSp: RegiaoDoMapa;
  paradas: Parada[];
  inputDePesquisa: string;
};

const ResultadoNoMapa = ({
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
        {paradas.map((item, i) => (
          <MapMarker
            key={i}
            tracksViewChanges={false}
            coordinate={{ latitude: item.py, longitude: item.px }}
            style={{ opacity: 0.5 }}
            title={item.np}
          />
        ))}
      </MapView>
    )
  );
};

export default ResultadoNoMapa;

const styles = StyleSheet.create({
  map: {
    width: '100%',
    height: '100%',
  },
});
