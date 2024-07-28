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
    return <ActivityIndicator size="large" color="#0000ff" />;
  }

  return (
    <View style={styles.container}>
      <MapView style={styles.map}>
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
              description={`Próxima parada: ${stop.np}`}
              pinColor="blue"
            />
          )),
        )}
      </MapView>
    </View>
  );
};

export default LineMapScreen;
