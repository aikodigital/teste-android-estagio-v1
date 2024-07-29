import React, {useEffect, useState} from 'react';
import {View, ActivityIndicator} from 'react-native';
import MapView, {Marker} from 'react-native-maps';
import {RouteProp} from '@react-navigation/native';
import {NativeStackNavigationProp} from '@react-navigation/native-stack';
import {getLinePrediction, LinePrediction} from '../../services/api';
import {RootStackParamList} from '../../routes/Navigation';
import styles from './style';

type LineMapScreenProps = {
  route: RouteProp<RootStackParamList, 'LineMapScreen'>;
  navigation: NativeStackNavigationProp<RootStackParamList, 'LineMapScreen'>;
};

const LineMapScreen: React.FC<LineMapScreenProps> = ({route}) => {
  const {lineId} = route.params;
  const [stops, setStops] = useState<LinePrediction['ps']>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [refreshing, setRefreshing] = useState<boolean>(false);

  const fetchLinePrediction = async () => {
    if (!loading) {
      setRefreshing(true);
    }
    const result = await getLinePrediction(lineId);
    if (result) {
      setStops(result.ps);
    }
    setLoading(false);
    setTimeout(() => {
      setRefreshing(false);
    }, 1000);
  };

  useEffect(() => {
    fetchLinePrediction();

    const interval = setInterval(() => {
      fetchLinePrediction();
    }, 30000);

    return () => clearInterval(interval);
  }, [lineId]);

  if (loading) {
    return <ActivityIndicator size="large" color="red" />;
  }

  return (
    <View style={styles.container}>
      {refreshing && (
        <View style={styles.loadingContainer}>
          <ActivityIndicator size={75} color="red" />
        </View>
      )}
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.55052,
          longitude: -46.633308,
          latitudeDelta: 0.4,
          longitudeDelta: 0.4,
        }}>
        {stops.map(stop => (
          <Marker
            key={stop.cp}
            coordinate={{latitude: stop.py, longitude: stop.px}}
            title={stop.np}
            description={`Código da Parada: ${stop.cp}`}
          />
        ))}
        {stops.flatMap(stop =>
          stop.vs.map((vehicle, index) => (
            <Marker
              key={`${stop.cp}-${index}`}
              coordinate={{latitude: vehicle.py, longitude: vehicle.px}}
              title={`Veículo ${vehicle.p}`}
              description={`Acessibilidade: ${
                vehicle.a ? 'Acessível' : 'Não Acessível'
              }`}
              pinColor="blue"
            />
          )),
        )}
      </MapView>
    </View>
  );
};

export default LineMapScreen;
