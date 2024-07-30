import React from 'react';
import { View, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { Ionicons } from '@expo/vector-icons';

export default function Conteudo() {

    const navigation = useNavigation();

    return (
        <View style={styles.BoxContent}>
            <View style={styles.BoxContent2}>

                <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Positions')}>
                    <Ionicons name={"navigate"} size={24} color={'#091833'} />
                    <Text style={styles.textBtn}>Posições</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Linhas')}>
                    <Ionicons name={"bus"} size={24} color={'#091833'} />
                    <Text style={styles.textBtn}>Linhas</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Paradas')}>
                    <Ionicons name={"pin"} size={24} color={'#091833'} />
                    <Text style={styles.textBtn}>Paradas</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Previsao')}>
                    <Ionicons name={"time"} size={24} color={'#091833'} />
                    <Text style={styles.textBtn}>Previsão</Text>
                </TouchableOpacity>

            </View>
        </View>
    );
}

const styles = StyleSheet.create({

    BoxContent: {
        backgroundColor: '#091833',
        borderRadius: 20,
        width: '90%',
        marginBottom: '45%',
        justifyContent: 'space-around',
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.4,
        shadowRadius: 10,
    },

    BoxContent2: {
        gap: 20,
        width: '80%',
        padding: 30,
        alignSelf: 'center',
    },

    button: {
        backgroundColor: '#f8f8f8',
        width: '100%',
        padding: 14,
        borderRadius: 20,
        alignSelf: 'center',
        alignItems: 'center',
        paddingLeft: 34,
        flexDirection: 'row',
        gap: 20,
    },

    textBtn: {
        fontSize: 20,
        color: '#333',
        fontWeight: '400',
    },

});