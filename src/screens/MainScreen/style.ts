import {StyleSheet} from 'react-native';
import theme from '../../global/theme';
import {RFValue} from 'react-native-responsive-fontsize';

export default StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: theme.colors.backgroundColor,
    padding: RFValue(15),
    gap: RFValue(75),
  },
  containerLogo: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    paddingTop: '20%',
  },
  titleLogo: {
    color: theme.colors.contrast,
    fontSize: RFValue(24),
    fontWeight: 'bold',
  },
  containerButtons: {
    flex: 1,
    width: '100%',
    justifyContent: 'flex-start',
    gap: RFValue(25),
    alignItems: 'center',
  },
});
