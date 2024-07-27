import React from 'react';
import { StyleSheet, View, ViewProps } from 'react-native';

const ContainerParaItem = ({ children, ...props }: ViewProps) => {
  return (
    <View style={styles.item} {...props}>
      {children}
    </View>
  );
};

export default ContainerParaItem;

const styles = StyleSheet.create({
  item: {
    borderWidth: 1,
    borderRadius: 6,
    borderColor: '#ddd',
    backgroundColor: '#fff',
    padding: 16,
    fontSize: 18,
    marginTop: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 2,
  },
});
