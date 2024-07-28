import { useEffect, useState, useRef } from "react";
import { StyleSheet, View, Text } from "react-native";

import MapView, { Marker } from "react-native-maps";

import { Loading } from "../components/Loading";
import { Header } from "../components/Header";

interface VehiclePositionProps {
  px: number; // longitude
  py: number; // latitude
  p: string; 
  ta: string; // last updated
}

interface Vehicle {
  c: string; 
  vs: VehiclePositionProps[];
}

export function VehicleMapPositionsMap() {

  const [ vehicles, setVehicles ] = useState<VehiclePositionProps[]>([]);
  const [ loading, setLoading ] = useState<boolean>(true);
  const [ error, setError ] = useState<string | null>(null);

  const mapRef = useRef<MapView>(null); 

  useEffect(() => {
    const token =
      "5f0a0959542a2e34e6d593fa860cb720d6a65261c9c186be3e6909c3f652a546";

    const fetchVehicles = async () => {
      try {
        const response = await fetch(
          "https://api.olhovivo.sptrans.com.br/v2.1/Posicao",
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          const errorText = await response.text();
          console.error(
            `Error ao buscar dados da API! status: ${response.status}, message: ${errorText}`
          );
          throw new Error(
            `Error ao buscar dados da API! status: ${response.status}, message: ${errorText}`
          );
        }

        const data = await response.json();
        if (data.l) {
          const allVehiclePositions = data.l.flatMap(
            (vehicle: Vehicle) => vehicle.vs
          );
        
          setVehicles(allVehiclePositions);
        } else {
          console.log("Nenhum dado de veículo encontrado.");
        }
      } catch (error) {
        console.error("Error ao buscar dados da API:", error);
        setError(
          error instanceof Error
            ? error.message
            : "Error ao buscar dados da API"
        );
      } finally {
        setLoading(false);
      }
    };

    const getLocationAndFetchVehicles = async () => {
      await fetchVehicles();
    };

    getLocationAndFetchVehicles();

    
  }, []);

  if (loading) {
    return ( <Loading/> );
  }

  if (error) {
    return (
      <View style={styles.loadingContainer}>
        <Text>Erro: {error}</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <Header title="Mapa de Posições dos Veiculos"/> 
      
      <MapView
        ref={mapRef}
        style={styles.map}
        initialRegion={{
          latitude: -23.55052,
          longitude: -46.633308,
          latitudeDelta: 1,
          longitudeDelta: 1,
        }}
      >
        {
          vehicles.map((vehicle, index) => (
            <Marker
              key={index}
              coordinate={{
                latitude: vehicle.py,
                longitude: vehicle.px,
              }}
              title={`Veículo ${vehicle.p}`}
              description={`Última atualização: ${vehicle.ta}`}
              pinColor="red"
            />
          ))
        }
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
    height: 400,
  },

  loadingContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
  },

});