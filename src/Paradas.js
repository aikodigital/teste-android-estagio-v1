import React from "react";
import {View, TouchableOpacity, Text} from "react-native"

const Paradas = () => {
    
    const getapi = async () => {
        const url = "https://aiko-olhovivo-proxy.aikodigital.io"
        const loginurl = "/Login/Autenticar?token={0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e}"
        const acess = "/Posicao"
        const aux = url + acess
        console.log(aux)
        try {
            const settings = {
                method: 'GET',
                headers: new Headers({ 
                    'Authorization':'Bearer 0a69c12b3a23f2d424066f4c35bd292d2f61943ccd091f7b55ade4086919322e',
                    'Content-Type':'application/json' })
            }

            const fetchResponse = await fetch(aux, settings)
            const data = await fetchResponse.json()
            console.log(data)
        }
        catch (error){
            console.log(error)
        }
    }
    getapi()
}

export default Paradas