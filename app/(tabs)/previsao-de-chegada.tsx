import { FlatList, StyleSheet } from 'react-native';
import { useState } from 'react';
import Loading from '@/components/form/loading/Loading';
import ErrorComponent from '@/components/form/error/Error';
import Title from '@/components/text/Title';
import PageContainer from '@/components/containers/PageContainer';
import { ParadaPrevisaoChegada, PrevisaoChegada } from '@/types/types';
import ItemText from '@/components/text/ItemText';
import useFetchHook from '@/custom-hooks/useFetchHook';
import ItemContainer from '@/components/containers/ItemContainer';
import SearchInput from '@/components/pages/linhaDeOnibus/SearchInput';

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
      <SearchInput
        placeholder='Parada - Ex: 340015329'
        value={inputDePesquisa.codigoParada}
        onChangeText={(v) =>
          setInputDePesquisa((p) => ({ ...p, codigoParada: v }))
        }
        onChange={lidarComPesquisa}
      />
      <SearchInput
        placeholder='Linha - Ex: 1989'
        value={inputDePesquisa.codigoLinha}
        onChangeText={(v) =>
          setInputDePesquisa((p) => ({ ...p, codigoLinha: v }))
        }
        onChange={lidarComPesquisa}
      />
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
                  <ItemContainer>
                    <ItemText>
                      De "{item.lt0}" para "{item.lt1}":
                    </ItemText>
                    <FlatList
                      data={item.vs}
                      renderItem={({ item }) => {
                        return (
                          <ItemContainer>
                            <ItemText>Carro: {item.p}</ItemText>
                            <ItemText>
                              Previsão de chegada para {item.t}.
                            </ItemText>
                          </ItemContainer>
                        );
                      }}
                    />
                  </ItemContainer>
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
});
