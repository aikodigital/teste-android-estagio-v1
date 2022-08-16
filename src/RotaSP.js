import React, { useEffect } from "react";
import { View, TouchableOpacity, Text, StyleSheet } from "react-native"
import { postautorization } from "./Api"

const RotaSP = ({ navigation }) => {

    useEffect(() => {
        postautorization()
    }, [])

    return (
        <View style={styles.container}>
            <TouchableOpacity style={styles.buttonStyle1} onPress={() => navigation.navigate("Linhas")}>
                <Text style={styles.textStyle}>
                    LINHAS
                </Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonStyle2} onPress={() => navigation.navigate("Paradas")}>
                <Text style={styles.textStyle}>
                    PARADAS
                </Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonStyle3} onPress={() => navigation.navigate("Posicao")}>
                <Text style={styles.textStyle}>
                    POSIÇÃO
                </Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.buttonStyle4} onPress={() => navigation.navigate("Previsao")}>
                <Text style={styles.textStyle}>
                    CHEGADA
                </Text>
            </TouchableOpacity>
            
        </View>

    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fff1c1",
        alignItems: "center",
        justifyContent: "center"
    },
    buttonStyle1: {
        backgroundColor: "#880606",
        borderRadius: 6,
        marginTop: 20,
        paddingLeft: 30,
        paddingRight: 30
    },
    textStyle: {
        fontWeight: 'bold',
        fontSize: 50,
        color: "#fcfcfc",
        margin: 40,
    },
    buttonStyle2: {
        backgroundColor: "#880606",
        borderRadius: 6,
        marginTop: 20,
        paddingLeft: 7,
        paddingRight: 7
    },
    buttonStyle3: {
        backgroundColor: "#880606",
        borderRadius: 6,
        marginTop: 20,
        paddingLeft: 13,
        paddingRight: 13
    },
    buttonStyle4: {
        backgroundColor: "#880606",
        borderRadius: 6,
        marginTop: 20,
        paddingLeft: 5,
        paddingRight: 5
    }

})
export default RotaSP