import React from 'react';
import styles from './style';
import {Image} from 'react-native';

const busLogo = require('../../assets/images/bus_logo.png');

const Logo: React.FC = () => {
  return <Image source={busLogo} style={styles.logo} resizeMode="contain" />;
};

export default Logo;
