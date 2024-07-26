import MapView, { Marker } from 'react-native-maps';
import { Container, Content, ContentIcon, ContentIconHeader, ContentItems, ContentMap, ContentTextHeader, Header, Icon, SubTitle, TextIcon, Title } from "./styles";

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
            </ContentMap>
            <Content>
                <ContentIcon>
                    <Icon name="bus" />
                    <TextIcon>
                        0.823
                    </TextIcon>
                </ContentIcon>
                <ContentItems>
                    <SubTitle>
                        Recanto das emas(Zoológico)
                    </SubTitle>
                    <SubTitle type="PRIMARY">
                        13 á 20 min
                    </SubTitle>
                </ContentItems>
                <Icon type="PRIMARY" name="wifi" />
            </Content>
            <Content>
                <ContentIcon>
                    <Icon name="bus" />
                    <TextIcon>
                        0.813
                    </TextIcon>
                </ContentIcon>
                <ContentItems>
                    <SubTitle>
                        Taguatinga
                    </SubTitle>
                    <SubTitle type="SECUNDARY">
                        13 á 20 min
                    </SubTitle>
                </ContentItems>
                <Icon type="PRIMARY" name="wifi" />
            </Content>
        </Container>
    );
}

