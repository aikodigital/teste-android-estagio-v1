import React, {useEffect, useState} from 'react';
import {View, Text, FlatList, ActivityIndicator} from 'react-native';
import {RouteProp} from '@react-navigation/native';
import {NativeStackNavigationProp} from '@react-navigation/native-stack';
import {getStopsPrediction, StopsPrediction} from '../../services/api';
import {RootStackParamList} from '../../routes/Navigation';
import styles from './style';

type StopDetailsScreenProps = {
  route: RouteProp<RootStackParamList, 'StopDetailsScreen'>;
  navigation: NativeStackNavigationProp<
    RootStackParamList,
    'StopDetailsScreen'
  >;
};

const StopDetailsScreen: React.FC<StopDetailsScreenProps> = ({route}) => {
  const {stopId} = route.params;
  const [predictions, setPredictions] = useState<StopsPrediction | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchStopPredictions = async () => {
      try {
        const result = await getStopsPrediction(stopId);
        if (result) {
          setPredictions(result);
        } else {
          setPredictions(null);
        }
      } catch (error) {
        console.error('Erro ao buscar previsões para a parada', error);
        setPredictions(null);
      }
      setLoading(false);
    };

    fetchStopPredictions();
  }, [stopId]);

  if (loading) {
    return <ActivityIndicator size="large" color="red" />;
  }

  if (!predictions || !predictions.p.l || predictions.p.l.length === 0) {
    return (
      <View style={styles.container}>
        <Text style={styles.itemTextWarning}>
          Não foram encontradas linhas registradas para essa parada.
        </Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <FlatList
        data={predictions.p.l}
        keyExtractor={item => item.cl.toString()}
        renderItem={({item}) => (
          <View style={styles.itemContainer}>
            <Text style={styles.itemTextMain}>{`${item.c} - ${item.lt1}`}</Text>
            <Text
              style={styles.itemText}>{`Linha ${item.lt1} - ${item.lt0}`}</Text>
            {item.vs.map((vehicle, index) => (
              <Text key={index} style={styles.arrivalText}>
                {`Veículo ${vehicle.p} às ${vehicle.t} - ${
                  vehicle.a ? 'Acessível' : 'Não Acessível'
                }`}
              </Text>
            ))}
          </View>
        )}
      />
    </View>
  );
};

export default StopDetailsScreen;
