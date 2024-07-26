import MapView, { Marker } from 'react-native-maps';
import { Container } from './styles';

export function Lines() {
  return (
    <Container>
           <MapView
                    style={{
                        width: '100%',
                        height:'100%'
                    }}
                    initialRegion={{
                        latitude: -23.5505,
                        longitude: -46.6333,
                        latitudeDelta: 0.0922,
                        longitudeDelta: 0.0421,
                    }}
                >
                    <Marker coordinate={{ latitude: -23.5505, longitude: -46.6333 }} title="São Paulo" />
                </MapView>
    </Container>
  );
}

