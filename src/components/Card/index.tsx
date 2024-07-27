import React from 'react';
import MapView, { Marker } from 'react-native-maps';
import { Container, ContentIconHeader, ContentMap, ContentTextHeader, Header, Icon, SubTitle, Title } from "./styles";
import { ReactNode } from 'react';

type CardProps = {
    station?: string;
    latitude?: number;
    longitude?: number;
    title?: string;
    children?: ReactNode;
};

export function Card({ latitude, longitude, station, title, children }: CardProps) {
    const hasLocation = latitude !== undefined && longitude !== undefined;

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
                        {station}
                    </SubTitle>
                </ContentTextHeader>
            </Header>
            <ContentMap>
                <MapView
                    style={{ width: '100%', height: '100%' }}
                    initialRegion={
                        hasLocation ? {
                            latitude: latitude!,
                            longitude: longitude!,
                            latitudeDelta: 0.0922,
                            longitudeDelta: 0.0421,
                        } : undefined
                    }
                >
                    {hasLocation && (
                        <Marker
                            coordinate={{ latitude: latitude!, longitude: longitude! }}
                            title={title}
                        />
                    )}
                </MapView>
            </ContentMap>
            {children}
        </Container>
    );
}
