// src/components/VehiclePositionsMap.js
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchVehiclePositions } from '../redux/actions';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';

const VehiclePositionsMap = () => {
  const dispatch = useDispatch();
  const { data: vehiclePositions, loading, error } = useSelector((state) => state.vehiclePositions);

  const initialLat = -23.55052; // Latitude de São Paulo
  const initialLng = -46.633308; // Longitude de São Paulo

  useEffect(() => {
    dispatch(fetchVehiclePositions());
  }, [dispatch]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <MapContainer center={[initialLat, initialLng]} zoom={13} style={{ height: "100vh", width: "100%" }}>
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      {vehiclePositions.map(vehicle => (
        <Marker key={vehicle.id} position={[vehicle.latitude, vehicle.longitude]}>
          <Popup>
            {vehicle.line} - {vehicle.vehicleId}
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default VehiclePositionsMap;
