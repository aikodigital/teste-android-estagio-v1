import React from 'react';
import { View, Text } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome5';

const ClusterIcon = ({ count }) => {
  return (
    <View style={{ justifyContent: 'center', alignItems: 'center' }}>
      <Icon name="bus" size={30} color="#007bff" />
      <View style={{
        position: 'absolute',
        bottom: -10,
        backgroundColor: 'white',
        borderRadius: 10,
        padding: 2,
        paddingHorizontal: 8,
      }}>
        <Text style={{ fontSize: 12, color: '#007bff' }}>{count}</Text>
      </View>
    </View>
  );
};

export default ClusterIcon;
