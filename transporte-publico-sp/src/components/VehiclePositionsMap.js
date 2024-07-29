// src/components/VehiclePositionsMap.js
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchVehiclePositions } from '../redux/actions';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';

const VehiclePositionsMap = () => {
  const dispatch = useDispatch();
  const { data: vehiclePositions, loading, error } = useSelector((state) => state.vehiclePositions);

  useEffect(() => {
    dispatch(fetchVehiclePositions());
  }, [dispatch]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <MapContainer center={[51.505, -0.09]} zoom={13} style={{ height: '100vh', width: '100%' }}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="&copy; OpenStreetMap contributors"
      />
      {vehiclePositions.map((vehicle, index) => (
        <Marker key={index} position={[vehicle.latitude, vehicle.longitude]}>
          <Popup>{vehicle.name}</Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default VehiclePositionsMap;
