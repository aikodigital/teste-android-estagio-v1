import {StyleSheet} from 'react-native';
import theme from '../../global/theme';
import {RFValue} from 'react-native-responsive-fontsize';

export default StyleSheet.create({
  container: {
    padding: RFValue(15),
  },
  containerButton: {
    marginTop: RFValue(10),
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
    paddingBottom: RFValue(5),
  },
  itemText: {
    fontSize: RFValue(12),
    fontStyle: 'italic',
    color: theme.colors.contrast,
  },
  linkContainer: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: RFValue(5),
  },
  buttonText: {
    color: theme.colors.primary,
    fontSize: RFValue(13),
  },
});
