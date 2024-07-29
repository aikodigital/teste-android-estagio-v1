import React, { useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { authenticate } from '../api/api';

const AuthTest = () => {
  const [authStatus, setAuthStatus] = useState('Not Authenticated');
  const [token, setToken] = useState(null);
  const navigation = useNavigation();

  const requestToken = async () => {
    try {
      const token = '092dc54dfb33fd443722c740eba33b78fa2aceada0480546b411ed5eb71372b8';
      const result = await authenticate(token);
      if (result) {
        setToken(token);
        setAuthStatus('Authenticated Successfully');
      } else {
        setAuthStatus('Authentication Failed');
      }
    } catch (error) {
      console.error(error);
      setAuthStatus('Error during Authentication');
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.status}>{authStatus}</Text>
      <TouchableOpacity style={styles.button} onPress={requestToken}>
        <Text style={styles.buttonText}>Authenticate</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={[styles.button, !token && styles.disabledButton]}
        onPress={() => navigation.navigate('BusLines', { token })}
        disabled={!token}
      >
        <Text style={styles.buttonText}>Lines and Forecast</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={[styles.button, !token && styles.disabledButton]}
        onPress={() => navigation.navigate('TrackingBus', { token })}
        disabled={!token}
      >
        <Text style={styles.buttonText}>Tracking bus</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  status: {
    marginBottom: 20,
    fontSize: 18,
    fontWeight: 'bold',
  },
  button: {
    backgroundColor: '#007bff',
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 25,
    marginBottom: 10,
    alignItems: 'center',
  },
  buttonText: {
    color: '#ffffff',
    fontSize: 16,
  },
  disabledButton: {
    backgroundColor: '#cccccc',
  },
});

export default AuthTest;
