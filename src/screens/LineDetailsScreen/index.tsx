import React, {useEffect, useState} from 'react';
import {View, Text, FlatList, ActivityIndicator} from 'react-native';
import {RouteProp} from '@react-navigation/native';
import {NativeStackNavigationProp} from '@react-navigation/native-stack';
import {getLinePrediction, LinePrediction} from '../../services/api';
import {RootStackParamList} from '../../routes/Navigation';
import ButtonPrimary from '../../components/ButtonPrimary';
import styles from './style';

type LineDetailsScreenProps = {
  route: RouteProp<RootStackParamList, 'LineDetailsScreen'>;
  navigation: NativeStackNavigationProp<
    RootStackParamList,
    'LineDetailsScreen'
  >;
};

const LineDetailsScreen: React.FC<LineDetailsScreenProps> = ({
  route,
  navigation,
}) => {
  const {lineId} = route.params;
  const [stops, setStops] = useState<LinePrediction['ps']>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchLinePrediction = async () => {
      const result = await getLinePrediction(lineId);
      if (result) {
        setStops(result.ps);
      }
      setLoading(false);
    };

    fetchLinePrediction();
  }, [lineId]);

  if (loading) {
    return <ActivityIndicator size="large" color="red" />;
  }

  return (
    <View style={styles.container}>
      <ButtonPrimary
        title="Ver Mapa"
        onPress={() => navigation.navigate('LineMapScreen', {lineId})}
      />

      {stops.length === 0 ? (
        <Text style={styles.itemTextWarning}>
          Não existem paradas registradas para essa linha.
        </Text>
      ) : (
        <FlatList
          data={stops}
          keyExtractor={item => item.cp.toString()}
          renderItem={({item}) => (
            <View style={styles.itemContainer}>
              <Text style={styles.itemTextMain}>{item.np || item.cp}</Text>
              <Text
                style={styles.itemText}>{`Código da Parada: ${item.cp}`}</Text>
              {item.vs.map((vehicle, index) => (
                <Text key={index} style={styles.arrivalText}>
                  {`Véiculo ${vehicle.p} às ${vehicle.t}`}
                </Text>
              ))}
            </View>
          )}
        />
      )}
    </View>
  );
};

export default LineDetailsScreen;
