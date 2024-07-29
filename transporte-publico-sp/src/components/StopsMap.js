import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchStops } from '../redux/actions';
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';



const StopsMap = () => {
  const dispatch = useDispatch();
  const { data: stops, loading, error } = useSelector((state) => state.stops);

  const initialLat = -23.55052; // Latitude de São Paulo
  const initialLng = -46.633308; // Longitude de São Paulo

  useEffect(() => {
    dispatch(fetchStops());
  }, [dispatch]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <MapContainer center={[initialLat, initialLng]} zoom={13} style={{ height: "100vh", width: "100%" }}>
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      {stops.map(stop => (
        <Marker key={stop.id} position={[stop.latitude, stop.longitude]}>
          <Popup>
            {stop.name}
          </Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default StopsMap;
