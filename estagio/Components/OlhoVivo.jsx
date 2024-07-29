import React, { useState, useEffect, useContext } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, TextInput, ScrollView,Modal,Button } from 'react-native';
import axios from 'axios';
import { AppContext } from './../appContext';

const OlhoVivo = () => {
  const { vehicles, setVehicles, paradas, setParadas, API_BASE_URL, API_KEY } = useContext(AppContext);
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [linha, setLinha] = useState('');
  const [linhasDisponiveis, setLinhasDisponiveis] = useState([]);
  const [linhasFiltradas, setLinhasFiltradas] = useState([]);
  const [linhaDetalhes, setLinhaDetalhes] = useState([]); // Armazena os detalhes de uma linha
  const [modalVisible, setModalVisible] = useState(false); // Controla a visibilidade do modal
  const [selectedLinha, setSelectedLinha] = useState(''); // Armazena a linha selecionada

  const authenticate = async () => {
    try {
      const response = await axios.post(`${API_BASE_URL}/Login/Autenticar`, null, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        params: {
          token: API_KEY,
        },
      });
      return response.data;
    } catch (error) {
      console.error('Erro de autenticação:', error);
      throw error;
    }
  };

  const fetchPosicaoCarros = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/Posicao`, {
        headers: {
          'Authorization': `Bearer ${API_KEY}`,
          'Content-Type': 'application/json',
        },
      });
      return response.data;
    } catch (error) {
      console.error('Erro ao buscar posição dos carros:', error);
      throw error;
    }
  };

  const processarDadosPosicao = (dados) => {
    const { l: linhas, hr: timestamp } = dados;
    const coordenadasVeiculosAtivos = [];
    const linhasDisponiveis = [];

    for (const linha of linhas) {
      const { lt1: origem, lt0: destino, vs: veiculos, c: linhaOnibus } = linha;

      linhasDisponiveis.push(linhaOnibus);

      for (const veiculo of veiculos) {
        if (veiculo.a) {
          coordenadasVeiculosAtivos.push({
            id: veiculo.p,
            veicLinha: linhaOnibus,
            origem,
            destino,
            latitude: veiculo.py,
            longitude: veiculo.px,
            timestamp,
          });
        }
      }
    }

    return {
      coordenadasVeiculosAtivos,
      linhasDisponiveis
    };
  };

  const fetchParadasPorLinha = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/Parada/Buscar?termosBusca=`, {
        headers: {
          'Authorization': `Bearer ${API_KEY}`,
          'Content-Type': 'application/json',
        },
      });
      return response.data;
    } catch (error) {
      console.error('Erro ao buscar paradas:', error);
      throw error;
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const result = await authenticate();
        if (result) {
          setIsAuthenticated(true);
        }
      } catch (error) {
        console.error('Erro durante a autenticação:', error);
      }
    };

    fetchData();
  }, []);

  useEffect(() => {
    if (!isAuthenticated) return;

    const fetchPosicaoData = async () => {
      try {
        const posicaoData = await fetchPosicaoCarros();
        const { coordenadasVeiculosAtivos, linhasDisponiveis } = processarDadosPosicao(posicaoData);

      

        addVehiclesGradually(coordenadasVeiculosAtivos)
        // Atualizar veículos em um único batch
      
        setLinhasDisponiveis(linhasDisponiveis);

      } catch (error) {
        console.error('Erro ao buscar dados de posição dos veículos:', error);
      }
    };

    const fetchData = async () => {
      try {
        await Promise.all([fetchParadasData(), fetchPosicaoData()]);
      } catch (error) {
        console.error('Erro ao buscar dados:', error);
      }
    };
    const fetchParadasData = async () => {
      try {
        const paradasData = await fetchParadasPorLinha();
        setParadas(paradasData);
      } catch (error) {
        console.error('Erro ao buscar dados das paradas:', error);
      }
    };
    fetchData();

    const paradasIntervalId = setInterval(fetchParadasPorLinha, 3600000);
    const posicaoIntervalId = setInterval(fetchPosicaoData, 300000);

    return () => {
      clearInterval(paradasIntervalId);
      clearInterval(posicaoIntervalId);
    };
  }, [isAuthenticated]);


  const addVehiclesGradually = (coordenadasVeiculosAtivos) => {
    let index = 0;
    const batchSize = 25;
    
    // Função para adicionar veículos gradualmente
    const addBatch = () => {
      // Verifica se ainda há veículos para adicionar
      if (index < coordenadasVeiculosAtivos.length) {
        setVehicles((prevVehicles) => [
          ...prevVehicles,
          ...coordenadasVeiculosAtivos.slice(index, index + batchSize),
        ]);
  
        index += batchSize;
  
        // Continua a adicionar veículos a cada 1 segundo
        setTimeout(addBatch, 1000);
      }
    };
  
    // Adiciona um atraso inicial de 3 segundos antes de começar
    setTimeout(() => {
      addBatch();
    }, 3000); // Atraso de 3 segundos
  };
  



  const handleSearchLinha = async (linha) => {
    try {
      const response = await axios.get(`${API_BASE_URL}/Linha/Buscar?termosBusca=${linha}`, null, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        params: {
          token: API_KEY,
        },
      });
      setLinhaDetalhes(response.data);
      setSelectedLinha(linha);
      setModalVisible(true); 
    } catch (error) {
      console.error('Erro ao buscar detalhes da linha:', error);
    }
  };

  useEffect(() => {
    const filtered = linhasDisponiveis.filter(linhaDisponivel =>
      linhaDisponivel.toLowerCase().includes(linha.toLowerCase())
    );
    setLinhasFiltradas(filtered);
  }, [linha, linhasDisponiveis]);

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.textInput}
        placeholder="Procure pela linha aqui"
        onChangeText={setLinha}
        value={linha}
      />
      <ScrollView style={styles.scrollView}>
        {linhasFiltradas.length > 0 ? (
          linhasFiltradas.map((linha, index) => (
            <View key={index} style={styles.linhaContainer}>
              <TouchableOpacity
                onPress={() => handleSearchLinha(linha)}
                style={styles.button}
              >
                <Text style={styles.buttonText}>Linha: {linha}</Text>
              </TouchableOpacity>
            </View>
          ))
        ) : (
          <Text style={styles.noResultsText}>Nenhum resultado encontrado</Text>
        )}
      </ScrollView>

      {/* Modal para exibir detalhes */}
      <Modal
        visible={modalVisible}
        animationType="slide"
        transparent={true}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Detalhes da Linha {selectedLinha}</Text>
            {linhaDetalhes.length > 0 ? (
              linhaDetalhes.map((detalhe, idx) => (
                <View key={idx} style={styles.detailBox}>
                  <Text style={styles.detailText}>Código: {detalhe.cl}</Text>
                  <Text style={styles.detailText}>Linha: {detalhe.lt}</Text>
                  <Text style={styles.detailText}>Tipo: {detalhe.tp}</Text>
                  <Text style={styles.detailText}>Terminal: {detalhe.ts}</Text>
                </View>
              ))
            ) : (
              <Text style={styles.noResultsText}>Nenhum detalhe encontrado</Text>
            )}
            <Button title="Fechar" onPress={() => setModalVisible(false)} />
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#fff',
  },
  textInput: {
    height: 60,
    borderColor: '#ccc',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginBottom: 16,
  },
  scrollView: {
    flex: 1,
    marginTop: 16,
  },
  linhaContainer: {
    marginBottom: 16,
  },
  button: {
    padding: 10,
    backgroundColor: '#007BFF',
    borderRadius: 5,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0,0,0,0.5)',
  },
  modalContent: {
    width: '80%',
    padding: 20,
    backgroundColor: '#fff',
    borderRadius: 10,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  detailBox: {
    marginBottom: 10,
  },
  detailText: {
    fontSize: 14,
    color: '#333',
  },
  noResultsText: {
    textAlign: 'center',
    marginTop: 20,
    fontSize: 16,
    color: '#888',
  },
});

export default OlhoVivo;