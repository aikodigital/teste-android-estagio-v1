import Mapa from '@/components/map/Mapa';
import { LinhaParaPosicao, MapRegion, PosicaoVeiculo } from '@/types/types';
import React from 'react';
import { MapMarker } from 'react-native-maps';

type Props = {
  coordenadasSp: MapRegion;
  linhasFiltradas: LinhaParaPosicao<PosicaoVeiculo>[];
};

const MapaParaPaginaPrincipal = ({ coordenadasSp, linhasFiltradas }: Props) => {
  return (
    <Mapa region={coordenadasSp} initialRegion={coordenadasSp}>
      {linhasFiltradas?.map(({ vs, lt0, lt1 }) =>
        vs
          ?.flat(1)
          ?.map(({ px, py, p }, i) => (
            <MapMarker
              key={i}
              tracksViewChanges={false}
              coordinate={{ latitude: py, longitude: px }}
              style={{ opacity: 0.5 }}
              title={`De ${lt0} para ${lt1}`}
            />
          ))
      )}
    </Mapa>
  );
};

export default MapaParaPaginaPrincipal;
