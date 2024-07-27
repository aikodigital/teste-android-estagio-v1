import { StyleSheet, TextInput, View } from 'react-native';
import { useRef, useState } from 'react';
import { Parada } from '@/types/posicao';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
import Title from '@/components/text/Title';
import MapView, { MapMarker } from 'react-native-maps';

export type Trajeto = { de: string | null; para: string | null };

export default function BuscarParadaDeOnibus() {
  const [paradas, setParadas] = useState<Parada[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const [formState, setFormState] = useState({ loading: false, error: '' });
  const timeoutRef = useRef<NodeJS.Timeout>();
  const cordernadasSpValorInicial = {
    latitude: -23.550522,
    longitude: -46.633328,
    latitudeDelta: 1,
    longitudeDelta: 0.012,
  };
  const [cordenadasSp, setCordenadasSp] = useState(cordernadasSpValorInicial);

  const fetchData = async () => {
    try {
      await autenticarNaApi();
      const res = await fetch(
        process.env.API_URL + '/Parada/Buscar?termosBusca=' + inputDePesquisa
      );
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as Parada[];
      if (!json.length) throw new Error(`Nenhuma parada encontrada.`);
      setParadas(json);
      setCordenadasSp(cordernadasSpValorInicial);
    } catch (error) {
      await autenticarNaApi();
      if (error instanceof Error) {
        console.log(error.message);
        setFormState((prev) => ({ ...prev, error: error.message }));
      }
    } finally {
      setFormState((prev) => ({ ...prev, loading: false }));
    }
  };

  const lidarComPesquisa = () => {
    setFormState({ error: '', loading: true });
    setParadas([]);
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(async () => {
      await fetchData();
    }, 2000);
  };

  return (
    <View style={styles.pageContainer}>
      <Title>Buscar Paradas de Ônibus</Title>
      <View style={styles.container}>
        <EvilIcons style={styles.icon} name='search' size={24} color='black' />
        <TextInput
          style={styles.input}
          placeholder='Ex: 340015329'
          value={inputDePesquisa}
          onChangeText={setInputDePesquisa}
          onChange={lidarComPesquisa}
        />
      </View>
      {formState.error && <ErrorComponent messagem={formState.error} />}
      {formState.loading && !formState.error ? (
        <Loading />
      ) : (
        inputDePesquisa.length > 0 &&
        paradas.length > 0 && (
          <MapView
            region={cordenadasSp}
            initialRegion={cordenadasSp}
            style={styles.map}
          >
            {paradas?.map(({ px, py, np }, i) => (
              <MapMarker
                key={i}
                tracksViewChanges={false}
                coordinate={{ latitude: py, longitude: px }}
                style={{ opacity: 0.5 }}
                title={np}
              />
            ))}
          </MapView>
        )
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  pageContainer: {
    paddingTop: 28,
    paddingBottom: 180,
    paddingHorizontal: 14,
    backgroundColor: '#f0f4f8',
  },
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 10,
    paddingHorizontal: 10,
    marginVertical: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 2,
  },
  icon: {
    marginBottom: 4,
    marginRight: 8,
  },
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
    color: '#333',
  },
  item: {
    borderWidth: 1,
    borderRadius: 6,
    borderColor: '#ddd',
    backgroundColor: '#fff',
    padding: 16,
    fontSize: 18,
    marginTop: 8,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 2,
    elevation: 2,
  },
  itemText: {
    fontSize: 16,
    color: '#333',
    marginBottom: 4,
  },
  map: {
    // flex: 1,
    width: '100%',
    height: '100%',
    // position: 'relative',
  },
});
