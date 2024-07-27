import { Link, Stack } from 'expo-router';
import { StyleSheet, Text } from 'react-native';
import Erro from '@/components/form/error/Erro';

export default function NotFoundScreen() {
  return (
    <>
      <Stack.Screen options={{ title: 'Oops!' }} />
      <Erro messagem="This screen doesn't exist." />
      <Link href='/posicoes-dos-veiculos' style={styles.link}>
        <Text>Go to home screen!</Text>
      </Link>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  link: {
    marginTop: 15,
    paddingVertical: 15,
  },
});
