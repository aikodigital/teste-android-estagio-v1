import { FlatList, StyleSheet, TextInput, View } from 'react-native';
import { useState } from 'react';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import EvilIcons from '@expo/vector-icons/EvilIcons';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import { ParadaPrevisaoChegada, PrevisaoChegada } from '@/types/types';
import ItemText from '@/components/text/ItemText';
import useFetchHook from '@/custom-hooks/useFetchHook';

export default function PaginaDeLinhaDeOnibus() {
  const [parada, setParada] = useState<ParadaPrevisaoChegada | null>(null);
  const [inputDePesquisa, setInputDePesquisa] = useState({
    codigoParada: '',
    codigoLinha: '',
  });
  const { pesquisar, formState } = useFetchHook({});

  const aoConcluirPesquisa = async (json: PrevisaoChegada) => {
    setParada(json.p);
  };

  const lidarComPesquisa = async () => {
    if (!inputDePesquisa.codigoLinha || !inputDePesquisa.codigoParada) return;
    setParada(null);
    await pesquisar<PrevisaoChegada>(
      aoConcluirPesquisa,
      `/Previsao?codigoParada=${inputDePesquisa.codigoParada}&codigoLinha=${inputDePesquisa.codigoLinha}`
    );
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
