import {StyleSheet} from 'react-native';
import theme from '../../global/theme';
import {RFValue} from 'react-native-responsive-fontsize';

export default StyleSheet.create({
  button: {
    backgroundColor: theme.colors.primary,
    paddingVertical: RFValue(12),
    paddingHorizontal: RFValue(12),
    borderRadius: 8,
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
  },
  buttonPressed: {
    opacity: 0.8,
    backgroundColor: theme.colors.darker,
  },
  buttonText: {
    color: theme.colors.backgroundColor,
    fontSize: RFValue(16),
  },
});
