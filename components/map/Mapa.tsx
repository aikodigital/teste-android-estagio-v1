import { RegiaoDoMapa } from '@/types/types';
import React, { PropsWithChildren } from 'react';
import { StyleSheet } from 'react-native';
import MapView from 'react-native-maps';

type Props = {
  region: RegiaoDoMapa;
  initialRegion: RegiaoDoMapa;
} & PropsWithChildren;

const Mapa = ({ children, region, initialRegion }: Props) => {
  return (
    <MapView region={region} initialRegion={initialRegion} style={styles.map}>
      {children}
    </MapView>
  );
};

export default Mapa;

const styles = StyleSheet.create({
  map: {
    width: '100%',
    height: '100%',
  },
  inputContainer: {
    display: 'flex',
    flexWrap: 'wrap',
    flexDirection: 'row',
    gap: 8,
    shadowColor: '#000',
    padding: 12,
    backgroundColor: '#f5f5f5',
    opacity: 0.9,
    alignItems: 'center',
    position: 'absolute',
    bottom: 50,
    left: 0,
    width: '105%',
  },
});
