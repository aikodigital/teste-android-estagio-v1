import { StyleSheet } from 'react-native';

export const linestyles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#E7E7E7',
  },
  header: {
    fontSize: 16,
    marginBottom: 20,
    textAlign: 'center',
    color: 'white',
  },
  searchBar: {
    height: 60,
    borderColor: 'white',
    borderWidth: 1,
    marginBottom: 20,
    paddingHorizontal: 10,
    backgroundColor: "white",
    color:'black',
    borderRadius: 20,
    elevation: 5,
    fontSize: 15
  },
  center: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    color: "red"
  },
  error: {
    color: 'red',
  },
});
