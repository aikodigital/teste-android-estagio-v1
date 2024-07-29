import { FontAwesome } from '@expo/vector-icons';
import React, { forwardRef } from 'react';
import { Pressable, StyleSheet } from 'react-native';

export const FilterOption = (props: {
  name: React.ComponentProps<typeof FontAwesome>['name'];
  color: string;
  onPress: () => void;
}) => {
  return (
    <Pressable style={styles.button} onPress={props.onPress}>
      {({ pressed }) => (
        <FontAwesome style={styles.icon} size={15} {...props} />
      )}
    </Pressable>
  );
};

const styles = StyleSheet.create({
  button: {
    alignItems: 'center',
    backgroundColor: '#0c0c0c',
    borderRadius: 50,
    elevation: 5,
    flexDirection: 'row',
    justifyContent: 'center',
    padding: 15,
    shadowColor: '#000',
  },

  icon: {
    width: 16,
    height: 16,
  },
});

export default FilterOption;
