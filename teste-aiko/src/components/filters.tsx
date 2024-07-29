import React, { useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import FilterOption from './filter-option';

export const Filters = (props: {
  onOpen: () => void;
  onSync: () => void;
  onOpenFilterParadas: () => void;
}) => {
  return (
    <View style={styles.container}>
      <FilterOption name='bus' color='red' onPress={props.onOpen} />

      <FilterOption name='bus' color='blue' onPress={props.onOpenFilterParadas} />
      <FilterOption name='rotate-right' color='white' onPress={props.onSync} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    width: 'auto',
    display: 'flex',
    flexDirection: 'column',
    gap: 5,
    position: 'absolute',
    top: 20,
    left: 10,
    padding: 5,
  },
});
