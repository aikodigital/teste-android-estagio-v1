import React, { useState, useEffect, useRef } from 'react';
import { View, Text, StyleSheet, TextInput, ActivityIndicator, TouchableOpacity } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import api from '../../services/api';
import authenticate from '../../services/auth';
import { useRoute } from '@react-navigation/native';
import Header from '../../components/Header';
import { Ionicons } from '@expo/vector-icons';

export default function Paradas() {
    const route = useRoute();
    const [searchTerm, setSearchTerm] = useState('');
    const [paradas, setParadas] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const mapRef = useRef(null);

    useEffect(() => {
        console.log('Lines state updated:', paradas);
    }, [paradas]);

    const handleSearch = async () => {
        setIsLoading(true);
        try {
            const authResponse = await authenticate();
            console.log('Authentication Response:', authResponse);

            if (authResponse) {
                const response = await api.get(`/Parada/Buscar?termosBusca=${searchTerm}`);
                console.log('API Response:', response.data);
                setParadas(response.data);

                if (mapRef.current) {
                    mapRef.current.animateToRegion({
                        latitude: -23.550520,
                        longitude: -46.633308,
                        latitudeDelta: 0.05,
                        longitudeDelta: 0.4,
                    });
                }
            } else {
                console.error('Failed to authenticate');
            }
        } catch (error) {
            console.error('API Error:', error);
        }
        setIsLoading(false);
    };

    const zoomIn = () => {
        if (mapRef.current) {
            mapRef.current.getCamera().then(camera => {
                mapRef.current.animateCamera({
                    center: camera.center,
                    pitch: camera.pitch,
                    heading: camera.heading,
                    altitude: camera.altitude * 0.5,
                });
            });
        }
    };

    const zoomOut = () => {
        if (mapRef.current) {
            mapRef.current.getCamera().then(camera => {
                mapRef.current.animateCamera({
                    center: camera.center,
                    pitch: camera.pitch,
                    heading: camera.heading,
                    altitude: camera.altitude * 1.6,
                });
            });
        }
    };

    return (
        <>
            <Header />
            <View style={styles.container}>
                <View style={styles.searchInput}>
                    <View style={styles.input}>
                        <TextInput
                            placeholder="Digite o nome da Linha (ex: Lapa)"
                            value={searchTerm}
                            keyboardType='ascii-capable'
                            onChangeText={setSearchTerm}
                            onSubmitEditing={handleSearch}
                        />
                    </View>
                    <View style={styles.search}>
                        <TouchableOpacity onPress={() => handleSearch()} >
                            <Ionicons name={"search"} size={30} color={'#091833'} />
                        </TouchableOpacity>
                    </View>
                </View>
                {
                    isLoading && <ActivityIndicator color="#091833" size="small" />
                }
                <MapView
                    style={styles.map}
                    ref={mapRef}
                    initialRegion={{
                        latitude: -23.550520,
                        longitude: -46.633308,
                        latitudeDelta: 0.05,
                        longitudeDelta: 0.05,
                    }}
                >
                    {
                        paradas.map((parada, index) => (
                            <Marker
                                key={index}
                                coordinate={{
                                    latitude: parada.py,
                                    longitude: parada.px,
                                }}
                                title={parada.np}
                                description={parada.ed}
                            />
                        ))
                    }
                </MapView>

                <View style={styles.zoomControls}>
                    <TouchableOpacity style={styles.zoomButton} onPress={zoomIn}>
                        <Ionicons name="add" size={30} color="#091833" />
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.zoomButton} onPress={zoomOut}>
                        <Ionicons name="remove" size={30} color="#091833" />
                    </TouchableOpacity>
                </View>

            </View>
        </>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    searchInput: {
        flexDirection: 'row',
        borderColor: '#091833',
        borderWidth: 0.2,
        borderRadius: 10,
        marginBottom: 2,
        backgroundColor: '#f8f8f8',
        justifyContent: 'space-around',
        alignSelf: 'center',
        alignContent: 'center',
        alignItems: 'center',
        width: '80%',
        padding: 8,
    },
    input: {
        width: '80%',
    },
    search: {
        width: '10%',
    },
    map: {
        marginTop: 20,
        width: '94%',
        borderRadius: 20,
        height: '87%',
    },
    zoomControls: {
        position: 'absolute',
        bottom: 20,
        right: 20,
        flexDirection: 'column',
    },
    zoomButton: {
        backgroundColor: '#fff',
        borderRadius: 50,
        padding: 10,
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',

    },
});
