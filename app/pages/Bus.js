import React, { useEffect, useState } from 'react';
import { StyleSheet, View, ActivityIndicator, Text } from 'react-native';
import { Marker } from 'react-native-maps';
import MapView from "react-native-map-clustering";

import { colors, width } from '../../utils/GlobalVal';
import ExpoIcons from '../../components/ExpoIcons';
import PositionAll from '../../utils/positions/PositionAll';
import Auth from '../../utils/Auth/Auth';

export default function Bus() {
    const [vehiclePositions, setVehiclePositions] = useState(null);
    const [loadingMarkers, setLoadingMarkers] = useState(true);
    const [region, setRegion] = useState({
        latitude: -23.55052,
        longitude: -46.633308,
        latitudeDelta: 0.01,
        longitudeDelta: 0.01,
    });

    useEffect(() => {
        const initializeApp = async () => {
            try {
                await Auth();
                const positions = await PositionAll();
                setVehiclePositions(positions);
            } catch (error) {
                console.error('Erro na inicialização:', error);
            } finally {
                setLoadingMarkers(false);
            }
        };

        initializeApp();
    }, []);

    return (
        <View style={styles.container}>
            {vehiclePositions && (
                <>
                    <Text style={styles.title}>Última atualização: {vehiclePositions.hr}</Text>
                    <MapView
                        style={{ width: '100%', height: '100%' }}
                        initialRegion={region}
                        clusterColor={colors.gray}
                    >
                        {
                            vehiclePositions.l.map((line, index) => (
                                line.vs.map((vehicle, vehicleIndex) => (
                                    <Marker
                                        key={`${index}-${vehicleIndex}`}
                                        coordinate={{ latitude: vehicle.py, longitude: vehicle.px }}
                                        title={`Cod - ${vehicle.p}`}
                                        description={`${line.lt0} - ${line.lt1}`}
                                    >
                                        <ExpoIcons iconName={'PointBus'} color={colors.gray} />
                                    </Marker>
                                ))
                            ))
                        }
                    </MapView>
                </>
            )}
            {loadingMarkers && (
                <View style={styles.loadingOverlay}>
                    <MapView
                        style={{ width: '100%', height: '100%' }}
                        initialRegion={region}
                        clusterColor={colors.gray}
                    >
                    </MapView>
                    <ActivityIndicator size="large" color={colors.gray} style={styles.loadingIndicator} />
                </View>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    title: {
        fontSize: width * 0.05,
        marginTop: width * 0.05,
    },
    loadingOverlay: {
        ...StyleSheet.absoluteFillObject,
        backgroundColor: 'transparent',
        alignItems: 'center',
        justifyContent: 'center',
    },
    loadingIndicator: {
        position: 'absolute',
    },
});
