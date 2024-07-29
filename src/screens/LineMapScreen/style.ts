import {StyleSheet, Dimensions} from 'react-native';

export default StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  map: {
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').height,
  },
  loadingContainer: {
    position: 'absolute',
    backgroundColor: 'rgba(255, 255, 255, 0)',
    zIndex: 5000,
  },
});
