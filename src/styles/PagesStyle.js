import { StyleSheet } from 'react-native';

import AppLoading from 'expo-app-loading';

const styles= StyleSheet.create({
  splashLogo: {
    backgroundColor: '#ffff',
    justifyContent: 'center',
    alignItems: 'center',
    flex: 1,
  },
  splashTitle: {
    fontSize: 24,
    fontFamily: 'Poppins-Regular',
    fontWeight: 'bold',
    textAlign: 'left',
    paddingBottom: 50,
    color: '#50f05b',
  },
  splashFooter: {
    fontSize: 16,
    color: '#333',
    marginBottom: 20,
  }
});

export default styles;