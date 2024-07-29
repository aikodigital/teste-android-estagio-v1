// src/components/StopsMap.js
import React from 'react';
import { useSelector } from 'react-redux';

const StopsMap = () => {
  const stops = useSelector((state) => state.stops.data);

  if (!Array.isArray(stops)) {
    console.error('stops is not an array:', stops);
    return null;
  }

  return (
    <div>
      {stops.map((stop) => (
        <div key={stop.id}>
          {stop.name} - {stop.location}
        </div>
      ))}
    </div>
  );
};

export default StopsMap;
