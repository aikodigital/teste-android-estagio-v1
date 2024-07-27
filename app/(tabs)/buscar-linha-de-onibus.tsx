import { FlatList, StyleSheet, Text, TextInput, View } from 'react-native';
import { useState } from 'react';
import { Linha } from '@/types/types';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import useFormState from '@/custom-hooks/useFormState';
import useOnChangeTimeout from '@/custom-hooks/useOnChangeTimeout';

export default function PaginaDeLinhaDeOnibus() {
  const [linhas, setLinhas] = useState<Linha[]>([]);
  const [inputDePesquisa, setInputDePesquisa] = useState('');
  const { formState, setFormState } = useFormState();
  const { runTimeout } = useOnChangeTimeout();

  const fetchData = async () => {
    try {
      const res = await fetch(
        process.env.API_URL + '/Linha/Buscar?termosBusca=' + inputDePesquisa
      );
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as Linha[];
      if (!json || !json.length) throw new Error(`Nenhuma linha encontrada.`);
      setLinhas(json);
    } catch (error) {
      if (error instanceof Error) {
        setFormState((prev) => ({ ...prev, error: error.message }));
      }
    } finally {
      setFormState((prev) => ({ ...prev, loading: false }));
    }
  };

  const lidarComPesquisa = () => {
    setFormState({ error: '', loading: true });
    setLinhas([]);
    runTimeout(fetchData);
  };

  return (
    <PageContainer>
      <Title>Buscar Linhas de Ônibus</Title>
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
        inputDePesquisa.length > 0 &&
        linhas.length > 0 && (
          <>
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
                    <Text style={styles.itemText}>
                      Linha: {item.lt} - {item.tl}
                    </Text>
                    <Text style={styles.itemText}>
                      Código Identificador: {item.cl}
                    </Text>
                    <Text style={styles.itemText}>
                      Terminal Principal: {item.tp}
                    </Text>
                    <Text style={styles.itemText}>
                      Terminal Secundário: {item.ts}
                    </Text>
                    <Text style={styles.itemText}>Sentido: {sl}</Text>
                    <Text style={styles.itemText}>
                      Operação: {item.lc ? 'Circular' : 'Terminal'}
                    </Text>
                  </View>
                );
              }}
            />
          </>
        )
      )}
    </PageContainer>
  );
}

const styles = StyleSheet.create({
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
});
