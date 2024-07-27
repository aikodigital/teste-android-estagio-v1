import { EvilIcons } from '@expo/vector-icons';
import React from 'react';
import { StyleSheet, TextInput, TextInputProps, View } from 'react-native';

const InputDeBusca = ({ ...props }: TextInputProps) => {
  return (
    <View style={styles.container}>
      <EvilIcons style={styles.icon} name='search' size={24} color='black' />
      <TextInput style={styles.input} {...props} />
    </View>
  );
};

export default InputDeBusca;

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
  icon: {
    marginBottom: 4,
    marginRight: 8,
  },
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#333',
  },
});
