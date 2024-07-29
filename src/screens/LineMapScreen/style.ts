import {StyleSheet, Dimensions} from 'react-native';
import theme from '../../global/theme';

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
    top: '40%',
    marginHorizontal: 'auto',
    position: 'absolute',
    backgroundColor: theme.colors.secondary,
    borderRadius: 100,
    zIndex: 5000,
  },
});
