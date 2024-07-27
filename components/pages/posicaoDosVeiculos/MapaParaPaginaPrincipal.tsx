import Mapa from '@/components/map/Mapa';
import { LinhaParaPosicao, RegiaoDoMapa, PosicaoVeiculo } from '@/types/types';
import React, { memo } from 'react';
import { View } from 'react-native';
import { MapMarker } from 'react-native-maps';

type Props = {
  coordenadasSp: RegiaoDoMapa;
  linhasFiltradas: LinhaParaPosicao<PosicaoVeiculo>[];
};

const MapaParaPaginaPrincipal = ({ coordenadasSp, linhasFiltradas }: Props) => {
  return (
    <View style={{ height: '100%', paddingBottom: 40 }}>
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
    </View>
  );
};

export default memo(MapaParaPaginaPrincipal);
