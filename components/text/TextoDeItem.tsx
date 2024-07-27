import React from 'react';
import { StyleSheet, Text, TextProps } from 'react-native';

const TextoDeItem = (props: TextProps) => {
  return (
    <Text {...props} style={styles.itemText}>
      {props.children}
    </Text>
  );
};

export default TextoDeItem;

const styles = StyleSheet.create({
  itemText: {
    fontSize: 16,
    color: '#333',
    marginBottom: 4,
  },
});
