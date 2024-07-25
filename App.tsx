import { StatusBar } from 'react-native';
import { StyleSheet, Text, View } from 'react-native';
import { Home } from './src/screens/Home';

export default function App() {
  return (
    <View style={styles.container}>
      <StatusBar 
        barStyle='light-content'
        backgroundColor='transparent'
        translucent
      />
      <Home/>
      
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#29292E',
    alignItems: 'center',
    justifyContent: 'center',
  },
});