import React from 'react';
import {TouchableOpacity, Text, StyleSheet, Image, View} from 'react-native';
import {Line} from '../types/interfaces';

type LineButtonProps = {
  line: Line;
  onPress: (line: Line) => void;
};

const LineButton: React.FC<LineButtonProps> = ({line, onPress}) => {
  return (
    <TouchableOpacity style={styles.button} onPress={() => onPress(line)}>
      <Image
        source={require('../assets/busIcone.png')}
        style={{width: 40, height: 40, marginRight: 15}}
        resizeMode="cover"
      />
      <View>
        <Text style={styles.text}>
          {line.lt0} -&gt;
        </Text>
        <Text style={styles.text}>
          {line.lt1}
        </Text>
        <Text style={{ color: "gray" }}> {line.c}</Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  button: {
    padding: 10,
    margin: 5,
    backgroundColor: 'white',
    borderRadius: 15,
    flexDirection: 'row',
    alignItems: 'center',
  },
  text: {
    color: 'black',
    fontSize: 18,
    paddingVertical: 5,
    fontWeight: '500',
    
  },
});

export default LineButton;
