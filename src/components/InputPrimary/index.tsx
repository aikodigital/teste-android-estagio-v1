import React, {useState} from 'react';
import {TextInput, TextInputProps} from 'react-native';
import theme from '../../global/theme';
import styles from './style';

interface CustomTextInputProps extends TextInputProps {
  borderColorFocused?: string;
  borderColorBlurred?: string;
}

const InputPrimary: React.FC<CustomTextInputProps> = ({
  borderColorFocused = theme.colors.primary,
  borderColorBlurred = theme.colors.secondary,
  style,
  ...props
}) => {
  const [isFocused, setIsFocused] = useState(false);

  return (
    <TextInput
      style={[
        styles.textInput,
        style,
        {borderColor: isFocused ? borderColorFocused : borderColorBlurred},
      ]}
      onFocus={() => setIsFocused(true)}
      onBlur={() => setIsFocused(false)}
      {...props}
    />
  );
};

export default InputPrimary;
