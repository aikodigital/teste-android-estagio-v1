import React from 'react';
import VehicleMap from './components/VehicleMap';
import StopsMap from './components/StopsMap';
import SearchFilter from './components/SearchFilter';

const App = () => {
  return (
    <div>
      <h1>Transporte Público SP</h1>
      <SearchFilter />
      <VehicleMap />
      <StopsMap />
    </div>
  );
};

export default App;
