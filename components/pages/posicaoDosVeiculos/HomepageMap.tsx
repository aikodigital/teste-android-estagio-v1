import Map from '@/components/map/Map';
import { LinhaParaPosicao, MapRegion, PosicaoVeiculo } from '@/types/types';
import React from 'react';
import { MapMarker } from 'react-native-maps';

type Props = {
  cordenadasSp: MapRegion;
  linhasFiltradas: LinhaParaPosicao<PosicaoVeiculo>[];
};

const HomepageMap = ({ cordenadasSp, linhasFiltradas }: Props) => {
  return (
    <Map region={cordenadasSp} initialRegion={cordenadasSp}>
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
    </Map>
  );
};

export default HomepageMap;
