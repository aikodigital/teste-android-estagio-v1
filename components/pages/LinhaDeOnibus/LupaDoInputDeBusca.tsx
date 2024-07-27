import { EvilIcons } from '@expo/vector-icons';
import React, { memo } from 'react';
import { StyleSheet } from 'react-native';

const LupaDoInputDeBusca = () => {
  return (
    <EvilIcons style={styles.icon} name='search' size={24} color='black' />
  );
};

export default memo(LupaDoInputDeBusca);

const styles = StyleSheet.create({
  icon: {
    marginBottom: 4,
    marginRight: 8,
  },
});
