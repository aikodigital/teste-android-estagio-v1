import { FlatList, StyleSheet, Text, TextInput, View } from 'react-native';
import { useRef, useState } from 'react';
import { autenticarNaApi } from '@/helpers/autenticarNaApi';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import { Linha, ParadaPrevisaoChegada, PrevisaoChegada } from '@/types/types';
import ItemText from '@/components/text/ItemText';

export default function PaginaDeLinhaDeOnibus() {
  const [parada, setParada] = useState<ParadaPrevisaoChegada | null>(null);
  const [inputDePesquisa, setInputDePesquisa] = useState({
    codigoParada: '',
    codigoLinha: '',
  });
  const [formState, setFormState] = useState({ loading: false, error: '' });
  const timeoutRef = useRef<NodeJS.Timeout>();

  const fetchData = async () => {
    try {
      await autenticarNaApi();
      const res = await fetch(
        process.env.API_URL +
          `/Previsao?codigoParada=${340015329}&codigoLinha=${1989}`
      );
      if (!res.ok) throw new Error('Houve um erro ao pesquisar.');
      const json = (await res.json()) as PrevisaoChegada;
      if (!json) throw new Error(`Houve um erro ao pesquisar.`);
      if (json && !json.p)
        throw new Error('Nenhuma previsão foi encontrada para essa pesquisa.');
      setParada(json.p);
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
    if (!inputDePesquisa.codigoLinha || !inputDePesquisa.codigoParada) return;
    setFormState({ error: '', loading: true });
    setParada(null);
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
    timeoutRef.current = setTimeout(async () => {
      await fetchData();
    }, 2000);
  };

  return (
    <PageContainer>
      <Title>Previsao de chegada</Title>
      <View style={styles.container}>
        <EvilIcons style={styles.icon} name='search' size={24} color='black' />
        <TextInput
          style={styles.input}
          placeholder='Parada - Ex: 340015329'
          value={inputDePesquisa.codigoParada}
          onChangeText={(v) =>
            setInputDePesquisa((p) => ({ ...p, codigoParada: v }))
          }
          onChange={lidarComPesquisa}
        />
      </View>
      <View style={styles.container}>
        <EvilIcons style={styles.icon} name='search' size={24} color='black' />
        <TextInput
          style={styles.input}
          placeholder='Linha - Ex: 1989'
          value={inputDePesquisa.codigoLinha}
          onChangeText={(v) =>
            setInputDePesquisa((p) => ({ ...p, codigoLinha: v }))
          }
          onChange={lidarComPesquisa}
        />
      </View>
      {formState.error && <ErrorComponent messagem={formState.error} />}
      {formState.loading && !formState.error ? (
        <Loading />
      ) : (
        inputDePesquisa.codigoLinha.length > 0 &&
        inputDePesquisa.codigoParada.length > 0 &&
        parada && (
          <>
            <Title>{parada.np}</Title>
            <FlatList
              data={parada.l}
              renderItem={({ item }) => {
                return (
                  <View style={styles.item}>
                    <ItemText>
                      De "{item.lt0}" para "{item.lt1}":
                    </ItemText>
                    <FlatList
                      data={item.vs}
                      renderItem={({ item }) => {
                        return (
                          <View style={styles.item}>
                            <ItemText>Carro: {item.p}</ItemText>
                            <ItemText>
                              Previsão de chegada para {item.t}.
                            </ItemText>
                          </View>
                        );
                      }}
                    />
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
});
