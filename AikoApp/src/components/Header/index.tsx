import React from "react";
import { View, Text, StyleSheet, Image } from 'react-native';

export default function Header() {

    const urlImg = "../../img/logo1.png";

    return (
        <View style={styles.Header}>
            <Image style={styles.Logo} source={require(urlImg)} />
            <Text style={styles.SubText}>Developed by Herbert Sampaio</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    Header: {
        width: "100%",
        height: "13%",
        borderBottomLeftRadius: 14,
        borderBottomRightRadius: 14,
        backgroundColor: '#091833',
        marginBottom: '4%',
    },

    Logo: {
        width: 200,
        height: 50,
        borderRadius: 20,
        alignContent: 'center',
        alignSelf: 'center',
        marginTop: "2%",
    },

    Title: {
        alignContent: 'center',
        alignSelf: 'center',
        fontSize: 20,
        fontWeight: '500',
        color: "#ffffff",
        marginTop: "6%",
    },

    SubText: {
        alignContent: 'center',
        alignSelf: 'center',
        fontSize: 8,
        fontWeight: '400',
        color: "#fff",
        marginTop: "1%",
    },

    mapa: {
        width: '60%',
        height: '60%',
    },
});