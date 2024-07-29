import React, { useEffect, useState } from "react";
import { FlatList } from "react-native"; 
import { Card } from "../../components/Card";
import { Search } from "../../components/Search";
import { Container, Title } from "./styles";
import * as Location from 'expo-location';
import useOlhoVivoAPI, { NearestBusProps } from "../../hooks";
import { Items } from "../../components/Items";

export function Home() {
  const [nearestBus, setNearestBus] = useState<{ number: string; destination: string; linha: number } | null>(null);
  const [arrivalForecasts, setArrivalForecasts] = useState<Array<{ number: string; time: string; arrivalMessage?: string }> | null>(null);
  const [address, setAddress] = useState<Location.LocationGeocodedAddress | null>(null);
  const [location, setLocation] = useState<Location.LocationObject | null>(null);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const { vehiclePositions, fetchVehiclePositions, findNearestBus, fetchArrivalForecast } = useOlhoVivoAPI();

  useEffect(() => {
    (async () => {
      try {
        let location = await Location.getCurrentPositionAsync({});
        setLocation(location);
        let reverseGeocode = await Location.reverseGeocodeAsync({
          latitude: location.coords.latitude,
          longitude: location.coords.longitude,
        });

        if (reverseGeocode.length > 0) {
          setAddress(reverseGeocode[0]);
        }
        
        await fetchVehiclePositions();
      } catch (error) {
        setErrorMsg('Erro ao obter localização ou buscar posições dos veículos.');
        console.error(error);
      }
    })();
  }, [fetchVehiclePositions]);

  useEffect(() => {
    if (location && vehiclePositions) {
      //@ts-ignore
      const nearestBus: NearestBusProps = findNearestBus({
        latitude: location.coords.latitude,
        longitude: location.coords.longitude
      });

      if (nearestBus) {
        console.log('o que ta vindo aqui',nearestBus)
        setNearestBus(nearestBus);
        fetchArrivalForecast(nearestBus.linha, nearestBus.number)
          .then(() => {
            if (vehiclePositions) {
              const forecasts = vehiclePositions.l.flatMap(line => 
                line.vs.map(vehicle => ({
                  cl: line.cl,
                  number: line.c,
                  time: vehicle.ta 
                }))
              ).filter(forecast => forecast.cl === nearestBus.linha);

              const arrivalMessages = forecasts.map(forecast => {
                const minutes = parseInt(forecast.time, 10); 
                let arrivalMessage: string;

                if (minutes <= 0) {
                  arrivalMessage = 'Chegada iminente';
                } else if (minutes < 60) {
                  arrivalMessage = `${minutes} minutos`;
                } else {
                  const hours = Math.floor(minutes / 60);
                  const remainingMinutes = minutes % 60;
                  arrivalMessage = `${hours}h ${remainingMinutes}m`;
                }

                return {
                  ...forecast,
                  arrivalMessage,
                };
              });

              setArrivalForecasts(arrivalMessages);
            }
          })
          .catch(err => {
            console.error('Erro ao buscar previsão de chegada:', err);
          });
      }
    }
  }, [location, findNearestBus, fetchArrivalForecast, vehiclePositions]);

  let text = "Localização não disponível";
  if (errorMsg) {
    text = errorMsg;
  } else if (address) {
    text = `${address.city} - ${address.region}`;
  }

  return (
    <Container>
      <Title>
        {text}
      </Title>
      <Search />
      {location && (
        <Card
          latitude={location.coords.latitude}
          longitude={location.coords.longitude}
          station={`${nearestBus?.number} - ${nearestBus?.destination}`}
          title={address?.city || ''}
        >
          {arrivalForecasts && arrivalForecasts.length > 0 ? (
            <FlatList
              data={arrivalForecasts}
              keyExtractor={(item, index) => index.toString()}
              renderItem={({ item }) => (
                <Items
                  destiny={nearestBus?.destination || 'Desconhecido'}
                  number={item.number}
                  time={item.arrivalMessage || 'Não disponível'}
                />
              )}
            />
          ) : (
            <Items
              destiny={nearestBus?.destination || 'Desconhecido'}
              number="Não disponível"
              time="Não disponível"
            />
          )}
        </Card>
      )}
    </Container>
  );
}
