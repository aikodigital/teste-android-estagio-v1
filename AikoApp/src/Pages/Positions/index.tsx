import React, { useState, useRef } from 'react';
import { View, Text, StyleSheet, ActivityIndicator, TouchableOpacity, TextInput } from 'react-native';
import MapView, { Marker } from 'react-native-maps';
import api from '../../services/api';
import authenticate from '../../services/auth';
import Header from '../../components/Header';
import { Ionicons } from '@expo/vector-icons';

export default function Dashboard() {
    const [paradas, setParadas] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const mapRef = useRef(null);

    const handleSearch = async () => {
        setIsLoading(true);
        try {
            const authResponse = await authenticate();
            console.log('Authentication Response:', authResponse);

            if (authResponse) {
                api.defaults.headers.common['Authorization'] = `Bearer ${authResponse}`;
                const response = await api.get(`/Posicao/Linha?codigoLinha=${searchTerm}`);
                console.log('Vehicle Position Response:', response.data);

                if (response.data && Array.isArray(response.data.vs)) {
                    setParadas(response.data.vs);
                } else {
                    console.error('Resposta da API não tem a estrutura esperada:', response.data);
                    setParadas([]);
                }

                centerMapInSaoPaulo();
            } else {
                console.error('Failed to authenticate');
            }
        } catch (error) {
            console.error('Vehicle Position API Error:', error);
            setParadas([]);
        }
        setIsLoading(false);
    };

    const handleUpdateAll = async () => {
        setIsLoading(true);
        try {
            const authResponse = await authenticate();
            console.log('Authentication Response:', authResponse);

            if (authResponse) {
                api.defaults.headers.common['Authorization'] = `Bearer ${authResponse}`;
                const response = await api.get('/Posicao');
                console.log('Vehicle Position Response:', response.data);

                if (response.data && Array.isArray(response.data.l)) {
                    setParadas(response.data.l.flatMap(line => line.vs));
                } else {
                    console.error('Resposta da API não tem a estrutura esperada:', response.data);
                    setParadas([]);
                }

                centerMapInSaoPaulo();
            } else {
                console.error('Failed to authenticate');
            }
        } catch (error) {
            console.error('Vehicle Position API Error:', error);
            setParadas([]);
        }
        setIsLoading(false);
    };

    const centerMapInSaoPaulo = () => {
        if (mapRef.current) {
            mapRef.current.animateToRegion({
                latitude: -23.550520,
                longitude: -46.633308,
                latitudeDelta: 0.05,
                longitudeDelta: 0.1,
            });
        }
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
            <View style={styles.searchInput}>
                <TextInput
                    style={styles.inputText}
                    placeholder="Digite o código da linha (ex: 2506)"
                    value={searchTerm}
                    onChangeText={setSearchTerm}
                />
                <TouchableOpacity onPress={handleSearch} style={styles.button}>
                    <Ionicons name="search" size={30} color="#091833" />
                </TouchableOpacity>
            </View>
            <View style={styles.container}>
                <TouchableOpacity style={styles.updateButton} onPress={handleUpdateAll}>
                    <Text style={styles.updateButtonText}>Atualizar Posições</Text>
                </TouchableOpacity>
                {isLoading && <ActivityIndicator color="#091833" size="small" />}
                <MapView
                    style={styles.map}
                    ref={mapRef}
                    initialRegion={{
                        latitude: -23.550520,
                        longitude: -46.633308,
                        latitudeDelta: 0.05,
                        longitudeDelta: 0.2,
                    }}
                >
                    {paradas.map((parada, index) => (
                        <Marker
                            key={index}
                            coordinate={{
                                latitude: parada.py,
                                longitude: parada.px,
                            }}
                            title={`Veículo ${parada.p}`}
                            description={parada.ta}
                        />
                    ))}
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
        width: '80%',
        padding: 8,
    },
    inputText: {
        width: '80%',
    },
    button: {
        width: '10%',
    },
    updateButton: {
        marginTop: 10,
        backgroundColor: '#091833',
        padding: 10,
        borderRadius: 5,
    },
    updateButtonText: {
        color: '#fff',
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
