import React, { useState } from 'react';
import { View, TextInput, TouchableOpacity, StyleSheet, Text, FlatList, ActivityIndicator } from 'react-native';
import { colors, width } from '../../utils/GlobalVal';
import Lines from '../../utils/Lines/Lines';

export default function Bus() {
    const [inputText, setInputText] = useState('');
    const [linesInfo, setLinesInfo] = useState([]);
    const [loading, setLoading] = useState(false);

    const handlePress = async () => {
        setLoading(true);
        try {
            const lines = await Lines(inputText);
            if (lines) setLinesInfo(lines);
            else { setLinesInfo([]) }
        } catch (error) {
            console.error('Erro ao buscar linhas:', error);
            setLinesInfo([]);
        } finally {
            setLoading(false);
        }
    };

    const renderItem = ({ item }) => (
        <View key={item.cl} style={styles.lineItem}>
            <Text style={styles.cardText}><Text style={styles.boldText}>Letreiro:</Text> {item.lt}-{item.tl}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Terminal Principal:</Text> {item.tp}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Terminal Secundário:</Text> {item.ts}</Text>
            <Text style={styles.cardText}><Text style={styles.boldText}>Sentido:</Text> {item.sl === 1 ? 'T. Principal para T. Secundário' : 'T. Secundário para T. Principal'}</Text>
        </View>
    );

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                placeholder="Busque a Linha"
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
                linesInfo.length > 0 ? (
                    <FlatList
                        data={linesInfo}
                        renderItem={renderItem}
                        keyExtractor={item => item.cl.toString()}
                        style={styles.resultsContainer}
                    />
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
        width: width * 0.95,
        borderWidth: 1,
        borderColor: colors.white,
        padding: 10
    },
    lineItem: {
        backgroundColor: colors.white,
        marginVertical: 10,
        padding: 12,
        borderRadius: 8,
    },
    cardText: {
        color: colors.black,
        fontSize: width * 0.04,
        marginBottom: 6,
    },
    loadingContainer: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    noResultsText: {
        marginTop: 20,
        fontSize: width * 0.04,
        color: colors.black,
    },
    boldText: {
        fontWeight: 'bold',
    },
});
