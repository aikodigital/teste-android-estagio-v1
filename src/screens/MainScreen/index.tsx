import React from 'react';
import {View, Text} from 'react-native';
import styles from './style';
import Logo from '../../components/Logo';
import ButtonPrimary from '../../components/ButtonPrimary';

const MainScreen: React.FC<{navigation: any}> = ({navigation}) => {
  return (
    <View style={styles.container}>
      <View style={styles.containerLogo}>
        <Logo />
        <Text style={styles.titleLogo}>Transporte Fácil SP</Text>
      </View>
      <View style={styles.containerButtons}>
        <ButtonPrimary
          title="Buscar por Linha"
          onPress={() => navigation.navigate('SearchLineScreen')}
        />
        <ButtonPrimary title="Buscar por Parada" onPress={() => null} />
        <ButtonPrimary title="Buscar por Corredor" onPress={() => null} />
        <ButtonPrimary title="Checar Congestionamentos" onPress={() => null} />
        <ButtonPrimary title="Sobre o Aplicativo" onPress={() => null} />
      </View>
    </View>
  );
};

export default MainScreen;
