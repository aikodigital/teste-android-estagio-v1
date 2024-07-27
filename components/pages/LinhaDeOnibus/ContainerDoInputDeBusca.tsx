import React, { memo, PropsWithChildren } from 'react';
import { StyleSheet, View } from 'react-native';

const ContainerDoInputDeBusca = ({ children }: PropsWithChildren) => {
  return <View style={styles.container}>{children}</View>;
};

export default memo(ContainerDoInputDeBusca);

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 10,
    paddingHorizontal: 10,
    marginVertical: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 2,
  },
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#333',
  },
});
