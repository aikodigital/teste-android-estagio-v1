import { StyleSheet, Text, View } from 'react-native';

export function Lines() {
  return (
    <View style={styles.container}>
      <Text>Linhas</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
