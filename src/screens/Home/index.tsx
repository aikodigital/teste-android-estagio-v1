import { StyleSheet, TouchableOpacity, View, Text } from "react-native";


export function Home() {

    async function handleTest() {

         const token =
            "5f0a0959542a2e34e6d593fa860cb720d6a65261c9c186be3e6909c3f652a546";

            try {
                const response = await fetch(
                    `https://api.olhovivo.sptrans.com.br/v2.1/Login/Autenticar?token=${token}`,
                    {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    }
                );
        
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(
                    `Erro na autenticação! status: ${response.status}, message: ${errorText}`
                    );
                }

                console.log(response.ok)
                } catch (error) {
                    console.error("Fetch Error:", error);
                } 
          
    }
    

    return (
        <View>
            
            <TouchableOpacity onPress={handleTest}>
                <Text style={styles.text}>Clique aqui para ver se autenticou</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    text: {
        color: '#ffff',
    },
})