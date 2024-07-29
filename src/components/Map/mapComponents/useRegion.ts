import { useState } from 'react';
import { SetStateAction } from 'react';

export const useRegion = () => {
  const [region, setRegion] = useState({
    latitude: -23.55052,
    longitude: -46.633308,
    latitudeDelta: 0.05,
    longitudeDelta: 0.05,
  });

  const onRegionChangeComplete = (
    newRegion: SetStateAction<{
      latitude: number;
      longitude: number;
      latitudeDelta: number;
      longitudeDelta: number;
    }>
  ) => {
    setRegion(newRegion);
  };

  return { region, onRegionChangeComplete };
};
