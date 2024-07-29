import {StyleSheet} from 'react-native';
import theme from '../../global/theme';
import {RFValue} from 'react-native-responsive-fontsize';

export default StyleSheet.create({
  container: {
    padding: RFValue(15),
  },
  itemContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: RFValue(10),
    borderBottomWidth: 1,
    borderBottomColor: theme.colors.secondary,
  },
  itemTextMain: {
    fontSize: RFValue(14),
    fontWeight: 'bold',
    color: theme.colors.contrast,
  },
  itemText: {
    fontSize: RFValue(12),
    fontStyle: 'italic',
    paddingVertical: RFValue(5),
    color: theme.colors.contrast,
  },
  arrivalText: {
    fontSize: RFValue(12),
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
  itemTextWarning: {
    fontSize: RFValue(14),
    paddingTop: RFValue(5),
    color: theme.colors.contrast,
  },
});
