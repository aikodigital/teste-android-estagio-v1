import { Pressable, StyleSheet, View } from 'react-native';
import { useEffect, useState } from 'react';

import { colors, width } from '../utils/GlobalVal';
import PositionAll from '../utils/positions/PositionAll';
import Auth from '../utils/Auth/Auth';
import MenuButtons from '../components/MenuButtons';

import { useNavigation } from '@react-navigation/native';

export default function App() {
  const [vehiclePositions, setVehiclePositions] = useState('');
  const navigation = useNavigation();

  const initializeApp = async () => {
    try {
      await Auth();
      setVehiclePositions(await PositionAll())
    } catch (error) {
      console.error('Erro na inicialização:', error);
    }
  };

  useEffect(() => {
    initializeApp();
  }, []);

  return (
    <View style={styles.container}>

      <Pressable style={styles.btn} onPress={() => navigation.navigate('pages/Bus', { data: vehiclePositions })}>
        <MenuButtons text={'Exibir Veículos'} iconName={'PointMenu'} />
      </Pressable>

      <Pressable style={styles.btn} onPress={() => navigation.navigate('pages/Lines')}>
        <MenuButtons text={'Informação das Linhas'} iconName={'Lines'} />
      </Pressable>

      <Pressable style={styles.btn} onPress={() => navigation.navigate('pages/Stops')}>
        <MenuButtons text={'Exibir Paradas'} iconName={'BusStop'} />
      </Pressable>

      <Pressable style={styles.btn} onPress={() => navigation.navigate('pages/Prev')}>
        <MenuButtons text={'Previsão de Chegada'} iconName={'Timer'} />
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: colors.yellow,
    alignItems: 'center',
    justifyContent: 'center',
  },
  btn: {
    margin: width * 0.03,
  }
});
