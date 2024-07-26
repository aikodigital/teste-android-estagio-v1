import MapView, { Marker } from 'react-native-maps';
import { Container, ContentIconHeader, ContentMap, ContentTextHeader, Header, Icon, SubTitle, Title } from "./styles";
import { Items } from '../Items';

export function Card() {
    return (
        <Container>
            <Header>
                <ContentIconHeader>
                    <Icon name="bus" />
                </ContentIconHeader>
                <ContentTextHeader>
                    <Title>
                        Estação mais próxima
                    </Title>
                    <SubTitle>
                        DF - 001 | Recanto das emas (passarela da Unire)
                    </SubTitle>
                </ContentTextHeader>
            </Header>
            <ContentMap>
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
            </ContentMap>
            <Items />
            <Items />
        </Container>
    );
}

