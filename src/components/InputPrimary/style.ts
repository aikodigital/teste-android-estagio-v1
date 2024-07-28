import {StyleSheet} from 'react-native';
import {RFValue} from 'react-native-responsive-fontsize';

const styles = StyleSheet.create({
  textInput: {
    height: RFValue(45),
    padding: RFValue(10),
    borderWidth: RFValue(2),
    borderRadius: 8,
    fontSize: RFValue(16),
  },
});

export default styles;
