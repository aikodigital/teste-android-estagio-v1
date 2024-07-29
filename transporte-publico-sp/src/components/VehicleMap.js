import React, { useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import { useDispatch, useSelector } from 'react-redux';
import { fetchVehiclePositions } from '../redux/actions';

const VehicleMap = () => {
  const dispatch = useDispatch();
  const positions = useSelector((state) => state.positions.data);

  useEffect(() => {
    dispatch(fetchVehiclePositions());
  }, [dispatch]);

  return (
    <MapContainer center={[-23.55052, -46.63331]} zoom={12} style={{ height: '100vh' }}>
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      {positions.map((vehicle, index) => (
        <Marker key={index} position={[vehicle.lat, vehicle.lng]}>
          <Popup>
            Linha: {vehicle.line} <br /> Horário: {vehicle.time}
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default VehicleMap;