import { FlatList, StyleSheet, Text, TextInput, View } from 'react-native';
import { useRef, useState } from 'react';
import { Linha } from '@/types/posicao';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
export type Trajeto = { de: string | null; para: string | null };

export default function PaginaDeLinhaDeOnibus() {
  const [linhas, setLinhas] = useState<Linha[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const [formState, setFormState] = useState({ loading: false, error: '' });
  const timeoutRef = useRef<NodeJS.Timeout>();

  const fetchData = async () => {
    try {
      await autenticarNaApi();
      const res = await fetch(
        process.env.API_URL + '/Linha/Buscar?termosBusca=' + inputDePesquisa
      );
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as Linha[];
      if (!json.length) throw new Error(`Nenhuma linha encontrada.`);
      setLinhas(json);
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
    setLinhas([]);
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(async () => {
      await fetchData();
    }, 2000);
  };

  return (
    <View style={styles.pageContainer}>
      <Text style={styles.title}>Buscar Linhas de Ônibus</Text>
      <View style={styles.container}>
        <EvilIcons style={styles.icon} name='search' size={24} color='black' />
        <TextInput
          style={styles.input}
          placeholder='Ex: 8000'
          value={inputDePesquisa}
          onChangeText={setInputDePesquisa}
          onChange={lidarComPesquisa}
        />
      </View>
      {formState.error && <ErrorComponent messagem={formState.error} />}
      {formState.loading && !formState.error ? (
        <Loading />
      ) : (
        inputDePesquisa.length > 0 && (
          <>
            <Text style={styles.title}>{linhas[0].lt}</Text>
            <FlatList
              data={linhas}
              renderItem={({ item }) => {
                let sl: string;
                switch (item.sl) {
                  case 1:
                    sl = 'Principal para Secundário';
                    break;
                  case 2:
                    sl = 'Secundário para Principal';
                    break;
                  default:
                    sl = '';
                    break;
                }
                return (
                  <View style={styles.item}>
                    <Text>
                      Linha: {item.lt} - {item.tl}
                    </Text>
                    <Text>Código Identificador: {item.cl}</Text>
                    <Text>Terminal Principal: {item.tp}</Text>
                    <Text>Terminal Secundário: {item.ts}</Text>
                    <Text>Sentido: {sl}</Text>
                    <Text>Operação: {item.lc ? 'Circular' : 'Terminal'}</Text>
                  </View>
                );
              }}
            />
          </>
        )
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  pageContainer: {
    paddingTop: 28,
    paddingBottom: 100,
    paddingHorizontal: 14,
  },
  title: { textAlign: 'center' },
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#F2F2F2',
    borderRadius: 10,
    paddingHorizontal: 10,
    marginVertical: 10,
  },
  icon: { marginBottom: 4 },
  input: {
    flex: 1,
    height: 40,
    fontSize: 16,
  },
  item: {
    borderWidth: 1,
    borderRadius: 6,
    borderColor: '#eee',
    backgroundColor: 'white',
    padding: 8,
    fontSize: 18,
    marginTop: 8,
    color: '#333',
    // width: 180,
  },
});
