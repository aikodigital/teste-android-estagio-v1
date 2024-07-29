import { useEffect, useState } from "react";
import { StyleSheet, View, Text } from "react-native";

import MapView, { Marker } from "react-native-maps";

import { Loading } from "../components/Loading";
import { Header } from "../components/Header";


interface BusStop {
  cp: string;
  np: string;
  ed: string;
  py: number;
  px: number;
}


export const BusStopMap: React.FC = () => {

  const [ busStops, setBusStops ] = useState<BusStop[]>([]);
  const [ loading, setLoading ] = useState<boolean>(true);
  const [ error, setError ] = useState<string | null>(null);

  useEffect(() => {
    const token =
      "5f0a0959542a2e34e6d593fa860cb720d6a65261c9c186be3e6909c3f652a546";

    const fetchBusStops = async () => {
      try {
       
        const res = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Parada/BuscarParadasPorLinha?codigoLinha=1",
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!res.ok) {
          throw new Error("Network response failed");
        }

        const data = await res.json();

        const validBusStops = data.filter((busStop: BusStop) => {
          const latitude = parseFloat(busStop.py as unknown as string);
          const longitude = parseFloat(busStop.px as unknown as string);
          if (isNaN(latitude) || isNaN(longitude)) {
            console.warn(
              `Parada inválida ${busStop.np}: (${latitude}, ${longitude})`
            );
            return false;
          }
          return true;
        });

        setBusStops(validBusStops);
      } catch (err: unknown) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError("An unknown error occurred");
        }
        console.error("Erro ao buscar paradas:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchBusStops();
  }, []);

  if (loading) {
    return ( <Loading/> );
  }

  if (error) {
    return (
      <View style={styles.loadingContainer}>
        <Text>Error: {error}</Text>
      </View>
    );
  }

  return (

    <View style={styles.container}>

      <Header title="Paradas de Ônibus"/> 
      
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: -23.5489,
          longitude: -46.638823,
          latitudeDelta: 0.5,
          longitudeDelta: 0.5,
        }}
      >

        {
          busStops.map((busStop) => {

            const latitude = parseFloat(busStop.py as unknown as string);
            const longitude = parseFloat(busStop.px as unknown as string);

            return (
              <Marker
                key={busStop.cp}
                coordinate={{ latitude, longitude }}
                title={busStop.np}
                description={busStop.ed}
                pinColor="red"
              />
            );
        })}

      </MapView>
    </View>
  );
};

const styles = StyleSheet.create({

  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
    backgroundColor: '#29292E', 
  },


  map: {
    width: 400,
    height: 500,

    borderWidth: 5,
    borderColor: "#00875F",
  },

  loadingContainer: {
    flex: 1,

    justifyContent: "center",
    alignItems: "center",
  },
});