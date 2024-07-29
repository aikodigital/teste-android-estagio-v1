import { useNavigation } from "@react-navigation/native"
import { Text, View, TouchableOpacity, StyleSheet} from "react-native"


interface HeaderProps {
    title: string
}


export function Header({ title }: HeaderProps){

    const navigation = useNavigation();

    return(
        <View style={styles.container}>
            <TouchableOpacity onPress={() => { navigation.goBack() }}><Text style={styles.button}>{`<`}</Text></TouchableOpacity>
            <Text style={styles.title}>{title}</Text>
        </View>
    )
}

const styles = StyleSheet.create({

    container:{
        flexDirection: "row",
        alignItems: "center",
        justifyContent: "space-between",
        gap: 10,
    },

    title: {  
        padding: 10,
        fontSize: 30,
        color: "#ffffff",
        justifyContent: "center",
        alignItems: "center",
    },

    button:{
        fontSize: 25,
        color: "#00875F"
    }
})