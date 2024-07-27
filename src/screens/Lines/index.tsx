import MapView, { Marker } from 'react-native-maps';
import { Container, Content, Title } from './styles';
import { Modalize } from 'react-native-modalize';
import { useRef } from 'react';
import { Items } from '../../components/Items';
import { Search } from '../../components/Search';

export function Lines() {
  const modalizeRef = useRef(null)
  return (
    <Container>
      <Content>
        <Search />
      </Content>
      <MapView
        style={{
          width: '100%',
          height: '100%'
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
      <Modalize
        ref={modalizeRef}
        snapPoint={200}
        alwaysOpen={200}
      >
        <Title>
          Linhas de ônibus
        </Title>
        <Items />
      </Modalize>
    </Container>
  );
}

