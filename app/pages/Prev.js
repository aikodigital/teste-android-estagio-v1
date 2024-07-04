import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, StyleSheet, Text, FlatList, ActivityIndicator } from 'react-native';
import { colors, width } from '../../utils/GlobalVal';
import StopPrev from '../../utils/Prev/StopPrev';

export default function Bus() {
    const [inputText, setInputText] = useState('');
    const [stopData, setStopData] = useState(null);
    const [loading, setLoading] = useState(false);

    const handlePress = async () => {
        setLoading(true);
        try {
            const resp = await StopPrev(inputText);
            if (resp) setStopData(resp);
        } catch (error) {
            console.error('Erro ao buscar Parada:', error);
            setStopData(null);
        } finally {
            setLoading(false);
        }
    };

    const renderItem = ({ item }) => (
        <View style={styles.vehicleCard}>
            <Text style={styles.cardText}><Text style={styles.boldText}>Linha:</Text> {item.cl} - {item.lt0} - {item.lt1}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Prefixo do Veículo:</Text> {item.p}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Horário Previsto:</Text> {item.t}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Acessível:</Text> {item.a ? 'Sim' : 'Não'}</Text>
        </View>
    );

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                placeholder="Digite o código da parada"
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
                stopData ? (
                    <>
                        <Text style={styles.referenceText}><Text style={styles.boldText}>Horário de Referência:</Text> {stopData.hr}</Text>
                        <Text style={styles.stopName}><Text style={styles.boldText}>Nome da Parada:</Text> {stopData.p.np}</Text>
                        <FlatList
                            data={stopData.p.l.flatMap((line) =>
                                line.vs.map((vehicle) => ({
                                    ...vehicle,
                                    cl: line.cl,
                                    lt0: line.lt0,
                                    lt1: line.lt1,
                                    sl: line.sl,
                                }))
                            )}
                            renderItem={renderItem}
                            keyExtractor={(item, index) => `${item.cl}-${item.p}-${index}`}
                            style={styles.resultsContainer}
                        />
                    </>
                ) : (
                    <View></View>
                )
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
    },
    btnText: {
        color: colors.black,
        fontSize: width * 0.05,
    },
    resultsContainer: {
        marginTop: 20,
        width: width * 0.8,
    },
    vehicleCard: {
        backgroundColor: colors.white,
        marginTop: 5,
        padding: 8,
        borderRadius: 4,
    },
    cardText: {
        color: colors.black,
        fontSize: width * 0.04,
        marginBottom: 6,
    },
    boldText: {
        fontWeight: 'bold',
    },
    referenceText: {
        fontSize: width * 0.05,
        marginTop: 10
    },
    stopName: {
        fontSize: width * 0.05,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
});
