import React from "react";
import { View, StyleSheet } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';
import Header from "../../components/Header";
import Conteudo from "../../components/Conteudo";
import { useRoute } from '@react-navigation/native';

export default function Home() {
    const route = useRoute();

    return (
        <View>
            <LinearGradient
                colors={['#F8f8f8', '#f6f6f6', '#E2EFFF']}
                style={styles.bgHome}>
                <Header />
                <Conteudo />
            </LinearGradient>
        </View >
    );
}

const styles = StyleSheet.create({
    container: {
        justifyContent: 'center',
        alignSelf: 'center',
        alignItems: 'center',
    },

    bgHome: {
        height: '100%',
        width: '100%',
        alignItems: 'center',
        alignSelf: 'center',
        justifyContent: 'space-between',
    }
});
