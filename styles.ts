import { StyleSheet } from 'react-native'

export const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    alignItems: 'flex-start',
    justifyContent: 'flex-start',
  },
  map: {
    flex: 0.7,
    width: '100%',
  },
  header: {
    height: 60,
    width: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  headerText: {
    color: 'black',
    fontSize: 28,
    fontWeight: '900',
  },
  containerContent: {
    flex: 0.4,
    width: '100%',
    padding: 20,
  },
  containerContentHeader: {
    fontSize: 24,
    fontWeight: '700',
  },
  containerContentRefresh: {
    fontSize: 14,
    fontWeight: '500',
    color: '#aaa',
  },
  containerContentSearch: {
    padding: 5,
    borderRadius: 8,
    height: 40,
    width: '100%',
    backgroundColor: '#e5e5e5',
    marginTop: 15,
  },
  containerContentSearchResult: {
    marginTop: 15,
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  cardLinhas: {
    marginBottom: 10,
    backgroundColor: '#e5e5e5',
    height: 70,
    padding: 12,
    borderRadius: 8,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
})
