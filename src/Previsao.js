import React, { useState } from "react"
import {View, TouchableOpacity, Text, TextInput, StyleSheet} from "react-native"
import { getprevision, getparada } from "./Api"


const Previsao = () => {

    const [mensege, setmensege] = useState("")
    const [parada, setparada] = useState("")
    const [linha, setlinha] = useState("")
    const [buscaParada, setbuscaParada] = useState("")


    const onButtonPress = async () => {
        const response = await getprevision(parada, linha)
        const newmensege = `Chega às ${response.hr}.`
        setmensege(newmensege)
    }

    const onButtonPressParada = async () => {
        const response = await getparada(buscaParada)
        const newmensege = `Chega às ${response[0].cp}.`
        const newMensege = `Chega às ${response[1].cp}.`
        setmensege(newmensege, newMensege)
    }

    return(
        <View style={styles.container}>
            <Text style={styles.textStyle}>
                Para verificar o horário que seu ônibus vai passar primeiro informe o nome da parada ou endereço e depois a linha.
            </Text>
            <TextInput style={styles.input1} value={buscaParada} onChangeText={setbuscaParada} placeholder="Nome da parada/endereço"/>
            <TouchableOpacity onPress={() => onButtonPressParada()}>
                <Text style={styles.buttonStyle1}>
                    CONFIRMA
                </Text>
            </TouchableOpacity>
            <TextInput style={styles.input1} value={parada} onChangeText={setparada} placeholder="           Digite a parada           "/>
            <TextInput style={styles.input} value={linha} onChangeText={setlinha} placeholder="           Digite a linha           "/>
            <TouchableOpacity onPress={() => onButtonPress()}>
                <Text style={styles.buttonStyle1}>
                    CONFIRMA
                </Text>
            </TouchableOpacity>
            <Text style={styles.text}>
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
    input: {
        height: 50,
        borderColor: 'black',
        borderWidth: 1,
        marginBottom: 10,
        borderRadius: 6,
        paddingLeft: 108,
        paddingRight: 108
    },
    input1: {
        height: 50,
        borderColor: 'black',
        borderWidth: 1,
        marginBottom: 10,
        borderRadius: 6,
        paddingLeft: 100,
        paddingRight: 100
    },
    textStyle: {
        fontSize: 22,
        textAlign: "justify",
        margin: 40
    },
    text: {
        fontSize: 22,
        textAlign: "center",
        margin: 40        
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

export default Previsao