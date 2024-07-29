import React, {useState} from 'react';
import styles from './style';
import {TouchableOpacity, Text, ViewStyle, StyleProp} from 'react-native';

interface CustomButtonProps {
  title: string;
  onPress: () => void;
}

const CustomButton: React.FC<CustomButtonProps> = ({title, onPress}) => {
  const [isPressed, setIsPressed] = useState(false);

  const handlePressIn = () => {
    setIsPressed(true);
  };

  const handlePressOut = () => {
    setIsPressed(false);
    onPress();
  };

  const buttonStyle: StyleProp<ViewStyle> = [
    styles.button,
    isPressed && styles.buttonPressed,
  ];

  return (
    <TouchableOpacity
      style={buttonStyle}
      onPressIn={handlePressIn}
      onPressOut={handlePressOut}>
      <Text style={styles.buttonText}>{title}</Text>
    </TouchableOpacity>
  );
};

export default CustomButton;
