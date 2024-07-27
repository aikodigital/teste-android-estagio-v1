import React, { useEffect, useState } from "react";
import { Card } from "../../components/Card";
import { Search } from "../../components/Search";
import { Container, Title } from "./styles";
import * as Location from 'expo-location';
import useOlhoVivoAPI from "../../hooks";

export function Home() {
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [address, setAddress] = useState<Location.LocationGeocodedAddress | null>(null);
  const [location, setLocation] = useState<Location.LocationObject | null>(null);
  const [nearestBus, setNearestBus] = useState<{ number: string; destination: string } | null>(null);
  console.log(nearestBus)
  const { vehiclePositions, fetchVehiclePositions, findNearestBus } = useOlhoVivoAPI();

  useEffect(() => {
    (async () => {
      let location = await Location.getCurrentPositionAsync({});
      setLocation(location);
      let reverseGeocode = await Location.reverseGeocodeAsync({
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
      });

      if (reverseGeocode.length > 0) {
        setAddress(reverseGeocode[0]);
      }

      fetchVehiclePositions();
    })();
  }, []);

  useEffect(() => {
    if (vehiclePositions && location) {
      const nearest = findNearestBus({
        latitude: location.coords.latitude,
        longitude: location.coords.longitude
      });
      setNearestBus(nearest);
    }
  }, [vehiclePositions, location]);

  let text = 'Aguardando..';
  if (errorMsg) {
    text = errorMsg;
  } else if (address) {
    text = `${address.street}, ${address.city}`;
  }

  return (
    <Container>
      <Title>
        {text}
      </Title>
      <Search />
      {location && nearestBus && (
        <Card
          latitude={location.coords.latitude}
          longitude={location.coords.longitude}
          station={`${nearestBus.number} - ${nearestBus.destination}`}
          title={address?.city || ''}
        />
      )}
    </Container>
  );
}
