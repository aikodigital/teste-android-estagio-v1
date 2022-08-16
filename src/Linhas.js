import React, { useState } from "react";
import { View, TouchableOpacity, Text, TextInput, StyleSheet } from "react-native"
import { getlinhas } from "./Api"

const Linhas = () => {

    const [mensege, setmensege] = useState("")
    const [termo, settermo] = useState("")


    const onButtonPress = async () => {
        const response = await getlinhas(termo)
        const newmensege = `Código da linha: ${response[0].cl}
Vai para: ${response[0].ts}.`

        setmensege(newmensege)
    }


    return (
        <View style = {styles.container}>
            <Text style={styles.textStyle}>
                Informe a linha para obter detalhes
            </Text>
            <TextInput style = {styles.input} value={termo} onChangeText={settermo} placeholder="Informe a linha" />
            <TouchableOpacity onPress={() => onButtonPress()}>
                <Text style={styles.buttonStyle1}>
                    CONFIRMA
                </Text>
            </TouchableOpacity>

            <Text style={styles.textStyle}>
                {mensege}
            </Text>
        </View>

    )

}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: "#fcfcfc",
        alignItems: "center"
    },
    textStyle: {
        fontSize: 22,
        textAlign: "justify",
        margin: 40
    },
    input: {
        height: 50,
        borderColor: 'black',
        borderWidth: 1,
        marginBottom: 10,
        borderRadius: 6,
        paddingLeft: 108,
        paddingRight: 108,
    },
    buttonStyle1: {
        backgroundColor: "#880606",
        borderRadius: 6,
        marginTop: 20,
        marginBottom: 20,
        padding: 10,
        paddingLeft: 28,
        paddingRight: 28,
        color: "#fff1c1"
    }
})

export default Linhas