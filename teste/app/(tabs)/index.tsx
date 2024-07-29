import React, { useState, useEffect } from 'react';
import { StyleSheet, Text, View, TextInput, Button, ScrollView, Alert } from 'react-native';

const SPTRANS_API_KEY = 'c0f5edd03e7e638f5c49159864abc5d965180de393221857083c7cfa813c61ec';

interface Line {
  cl: number;
  tp: string;
  sl: number;
}

interface Vehicle {
  p: number;
  t: string;
}

interface Prediction {
  hr: string; // Horário de referência
  p?: {
    cp: number; // Código da parada
    np: string; // Nome da parada
    px: number; // Longitude
    py: number; // Latitude
    l: Array<{
      c: string;
      cl: number;
      sl: number;
      lt0: string;
      lt1: string;
      qv: number;
      vs: Vehicle[];
    }>;
  };
}

interface Stop {
  cp: number;
  np: string;
  ed: string;
  py: number;
  px: number;
}

export default function App() {
  const [lineSearch, setLineSearch] = useState<string>('');
  const [stopSearch, setStopSearch] = useState<string>('');
  const [lineStopsSearch, setLineStopsSearch] = useState<string>('');
  const [authenticated, setAuthenticated] = useState<boolean>(false);

  useEffect(() => {
    authenticate();
  }, []);

  const authenticate = async () => {
    try {
      const response = await fetch('http://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=' + SPTRANS_API_KEY, {
        method: 'POST',
      });
      const result = await response.json();
      console.log('Autenticação result:', result);
      if (result && result.success) {
        setAuthenticated(true);
      } else {
        Alert.alert('Erro', 'Falha na autenticação com a API SPTrans');
      }
    } catch (error) {
      console.error('Erro ao autenticar:', error);
      Alert.alert('Erro', 'Falha na autenticação com a API SPTrans');
    }
  };

  const handleLineSearch = async () => {
    if (!authenticated) {
      Alert.alert('Erro', 'Não autenticado com a API SPTrans');
      return;
    }

    try {
      const response = await fetch(`http://api.olhovivo.sptrans.com.br/v2.1/Linha/Buscar?termosBusca=${lineSearch}&token=${SPTRANS_API_KEY}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data: Line[] = await response.json();
      console.log('Dados da linha:', data);
      if (data.length > 0) {
        const info = data.map((line) => 
          `Código: ${line.cl}\nLetreiro: ${line.tp}\nSentido: ${line.sl === 1 ? 'Terminal Principal para Terminal Secundário' : 'Terminal Secundário para Terminal Principal'}`
        ).join('\n\n');
        Alert.alert('Informações da Linha', info);
      } else {
        Alert.alert('Resultado', 'Nenhuma linha encontrada');
      }
    } catch (error) {
      console.error('Erro ao buscar informações da linha:', error);
      Alert.alert('Erro', 'Falha na busca de informações da linha');
    }
  };

  const handleArrivalPrediction = async () => {
    if (!authenticated) {
      Alert.alert('Erro', 'Não autenticado com a API SPTrans');
      return;
    }

    const stopCode = stopSearch.trim();
    if (!stopCode) {
      Alert.alert('Erro', 'Por favor, forneça o código da parada');
      return;
    }

    try {
      const response = await fetch(`http://api.olhovivo.sptrans.com.br/v2.1/Previsao/Parada?codigoParada=${stopCode}&token=${SPTRANS_API_KEY}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data: Prediction = await response.json();
      console.log('Dados da previsão de chegada:', data);

      if (data.p && data.p.l && data.p.l.length > 0) {
        const predictions = data.p.l.flatMap((line) => 
          line.vs.map((vehicle) => 
            `Veículo: ${vehicle.p}\nHorário previsto: ${vehicle.t}\n`
          )
        ).join('\n');
        Alert.alert('Previsão de Chegada', predictions || 'Nenhuma previsão disponível');
      } else {
        Alert.alert('Resultado', 'Nenhuma previsão encontrada');
      }
    } catch (error) {
      console.error('Erro ao buscar previsão de chegada:', error);
      Alert.alert('Erro', 'Falha na busca da previsão de chegada');
    }
  };

  const handleLineStopsSearch = async () => {
    if (!authenticated) {
      Alert.alert('Erro', 'Não autenticado com a API SPTrans');
      return;
    }

    try {
      const response = await fetch(`http://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha=${lineStopsSearch}&token=${SPTRANS_API_KEY}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const data: Stop[] = await response.json();
      console.log('Dados das paradas por linha:', data);
      if (data.length > 0) {
        const stops = data.map((stop) => 
          `Código: ${stop.cp}\nNome: ${stop.np}\nEndereço: ${stop.ed}`
        ).join('\n\n');
        Alert.alert('Paradas por Linha', stops);
      } else {
        Alert.alert('Resultado', 'Nenhuma parada encontrada');
      }
    } catch (error) {
      console.error('Erro ao buscar paradas por linha:', error);
      Alert.alert('Erro', 'Falha na busca das paradas por linha');
    }
  };

  return (
    <ScrollView contentContainerStyle={styles.container}>
      <Text style={styles.title}>Encontre seu ônibus</Text>
      <Text style={styles.subtitle}>
        Busque uma linha de ônibus ou informe o nome/endereço da sua parada.
      </Text>

      <View style={styles.sectionContainer}>
        <Text style={styles.sectionTitle}>Informações por linha:</Text>
        <Text style={styles.subtitle}>
          Realize uma busca das linhas do sistema SPTrans e veja informações sobre elas.
        </Text>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            placeholder="Digite a linha do ônibus"
            value={lineSearch}
            onChangeText={setLineSearch}
          />
          <Button title="BUSCAR" onPress={handleLineSearch} />
        </View>
      </View>

      <View style={styles.sectionContainer}>
        <Text style={styles.sectionTitle}>Previsão de chegada:</Text>
        <Text style={styles.subtitle}>
          Informe uma parada e descubra a previsão de chegada de cada veículo que passará por ela.
        </Text>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            placeholder="Digite o código da parada"
            keyboardType="numeric"
            value={stopSearch}
            onChangeText={setStopSearch}
          />
          <Button title="BUSCAR" onPress={handleArrivalPrediction} />
        </View>
      </View>

      <View style={styles.sectionContainer}>
        <Text style={styles.sectionTitle}>Buscar parada por linha:</Text>
        <Text style={styles.subtitle}>
          Realize uma busca por todos os pontos de parada atendidos por uma determinada linha.
        </Text>
        <View style={styles.inputContainer}>
          <TextInput
            style={styles.input}
            placeholder="Digite a linha do ônibus"
            value={lineStopsSearch}
            onChangeText={setLineStopsSearch}
          />
          <Button title="BUSCAR" onPress={handleLineStopsSearch} />
        </View>
      </View>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5f5f5',
    padding: 16,
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 16,
    marginBottom: 20,
    textAlign: 'center',
    paddingHorizontal: 10,
  },
  sectionContainer: {
    width: '100%',
    marginBottom: 20,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    width: '100%',
    marginBottom: 20,
  },
  input: {
    flex: 1,
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    borderRadius: 5,
    paddingHorizontal: 10,
    marginRight: 10,
  },
});