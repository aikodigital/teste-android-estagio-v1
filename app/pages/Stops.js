import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, StyleSheet, Text, ActivityIndicator } from 'react-native';
import { Marker } from 'react-native-maps';
import MapView from "react-native-map-clustering";
import { colors, width } from '../../utils/GlobalVal';
import Stops from '../../utils/Stops/Stops'
import ExpoIcons from '../../components/ExpoIcons'


export default function BusStop() {
    const [inputText, setInputText] = useState('');
    const [stops, setStops] = useState([]);
    const [loading, setLoading] = useState(false);
    const [region, setRegion] = useState({
        latitude: -23.55052,
        longitude: -46.633308,
        latitudeDelta: 0.01,
        longitudeDelta: 0.01,
    });

    const handlePress = async () => {
        setLoading(true);
        try {
            const resp = await Stops(inputText);
            if (resp) {
                setStops(resp);
                setRegion({
                    latitude: resp[0].py,
                    longitude: resp[0].px,
                    latitudeDelta: 0.1,
                    longitudeDelta: 0.1,
                });
            } else {
                setStops([]);
                setRegion({
                    latitude: -23.55052,
                    longitude: -46.633308,
                    latitudeDelta: 0.1,
                    longitudeDelta: 0.1,
                });
            }
        } catch (error) {
            console.error('Erro ao buscar paradas:', error);
            setStops([]);
        } finally {
            setLoading(false);
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                placeholder="Busque Paradas"
                value={inputText}
                onChangeText={setInputText}
            />
            <TouchableOpacity
                onPress={handlePress}
                style={styles.btn}
            >
                <Text style={styles.btnText}>Buscar</Text>
            </TouchableOpacity>
            {loading ? (
                <View style={styles.loadingContainer}>
                    <ActivityIndicator size="large" color={colors.black} />
                </View>
            ) : (
                <MapView
                    style={styles.map}
                    initialRegion={region}
                    clusterColor={colors.gray}
                >
                    {stops.map(stop => (
                        <Marker
                            key={stop.cp}
                            coordinate={{ latitude: stop.py, longitude: stop.px }}
                            title={`Cod: ${stop.cp} - ${stop.np}`}
                            description={`Endereço: ${stop.ed}`}
                        >
                            <ExpoIcons iconName={'BusStop'} color={colors.gray} />
                        </Marker>
                    ))}
                </MapView>
            )}
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: width * 0.1,
        alignItems: 'center',
        padding: 16,
        backgroundColor: colors.yellow,
    },
    input: {
        height: width * 0.12,
        borderColor: colors.black,
        borderWidth: 1,
        marginBottom: 12,
        paddingHorizontal: 8,
        width: width * 0.8,
        backgroundColor: colors.white,
    },
    btn: {
        backgroundColor: colors.white,
        padding: width * 0.02,
        borderRadius: 4,
        marginBottom: width * 0.03
    },
    btnText: {
        color: colors.black,
        fontSize: width * 0.05,
    },
    map: {
        width: '100%',
        height: '100%',
    },
    loadingContainer: {
        ...StyleSheet.absoluteFillObject,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: 'rgba(255, 255, 255, 0.8)',
    },
});
