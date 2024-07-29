import { View, Text, StyleSheet, ActivityIndicator } from "react-native";


export function Loading(){
    return(
        <View style={styles.container}>
            <ActivityIndicator size="large" color="#00875F" />
            <Text style={styles.text}>Carregando...</Text>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#29292E', 
        alignItems: "center",
        justifyContent: "center"
    },
    text:{
        color: "#ffff"
    }
})