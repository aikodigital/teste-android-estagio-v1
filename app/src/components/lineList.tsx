import React from 'react';
import {FlatList, StyleSheet, Text, View} from 'react-native';

const styles = StyleSheet.create({
    container: {
      flex: 1,
      paddingTop: 22,
    },
    item: {
      padding: 10,
      fontSize: 18,
      height: 44,
    },
  });

export default function ListLine() {
 return (
    <View style={styles.container}>
    <FlatList
      data={[
        {key: 'Devin'},
        {key: 'Dan'},
        {key: 'Dominic'},
        {key: 'Jackson'},
        {key: 'James'},
        {key: 'Joel'},
        {key: 'John'},
        {key: 'Jillian'},
        {key: 'Jimmy'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
        {key: 'Julie'},
      ]}
      renderItem={({item}) => <Text style={styles.item}>{item.key}</Text>}
    />
  </View>
  );
}