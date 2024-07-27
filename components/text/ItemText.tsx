import React from 'react';
import { StyleSheet, Text, TextProps } from 'react-native';

const ItemText = (props: TextProps) => {
  return (
    <Text {...props} style={styles.itemText}>
      {props.children}
    </Text>
  );
};

export default ItemText;

const styles = StyleSheet.create({
  itemText: {
    fontSize: 16,
    color: '#333',
    marginBottom: 4,
  },
});
