import { useEffect, useState } from "react";
import 
{
  View, Text, FlatList,
  StyleSheet, SafeAreaView,
} from "react-native";

import { Loading } from "../components/Loading";
import { Header } from "../components/Header";


interface BusLine {
  cl: number;
  lt: string;
  sl: number;
  tl: number;
  tp: string;
  ts: string;
}


export function BusLines() {

  const [ busLines, setBusLines ] = useState<BusLine[]>([]);
  const [ loading, setLoading ] = useState<boolean>(true);
  const [ error, setError ] = useState<string | null>(null);

  useEffect(() => {

    const fetchBusLines = async () => {
      try {
        
        const res = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Linha/BuscarLinhaSentido?termosBusca=800&sentido=1",
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (!res.ok) {
          const errorText = await res.text();
          throw new Error(
            `HTTP error! status: ${res.status}, message: ${errorText}`
          );
        }

        const json: BusLine[] = await res.json();
        setBusLines(json);
      } catch (error) {
        console.error("Fetch Error:", error);
        setError(
          error instanceof Error
            ? error.message
            : "Ocorreu um erro desconhecido"
        );
      } finally {
        setLoading(false);
      }
    };

    fetchBusLines();
  }, []); 


  if (loading) {
    return ( <Loading/> )
  }
  

  if (error) {
    return (
      <View style={styles.container}>
        <Text>Error: {error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>

       <Header title="Linhas de Ônibus"/> 

      <FlatList
        data={busLines}
        keyExtractor={(item, index) => `${item.cl}-${index}`}

        renderItem={({ item }) => (
          <SafeAreaView style={styles.item}>
            <View style={styles.content}>
              <Text style={styles.text}>{item.lt}</Text>
              <Text style={styles.text}>
                {item.tp} {item.ts}
              </Text>
              <Text style={styles.text}>
                {item.cl} - {item.lt} - {item.tl}
              </Text>
            </View>
          </SafeAreaView>
        )}
        
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#29292E', 
    alignItems: "center",
    paddingTop: 50
  },

  text: {
    color: "#ffffff",
    fontWeight: "bold",
  },

  item: {
    backgroundColor: "#00875F",

    borderRadius: 5,
    padding: 20,
    marginVertical: 8,
    marginHorizontal: 16,
  },

  content: {  
    padding: 10,    
    justifyContent: "center",
    alignItems: "center",
  },

  
});