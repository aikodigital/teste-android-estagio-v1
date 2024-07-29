// src/components/ArrivalPredictions.js
import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchArrivalPredictions } from '../redux/actions';

const ArrivalPredictions = ({ stopId }) => {
  const dispatch = useDispatch();
  const { data: predictions, loading, error } = useSelector((state) => state.arrivalPredictions);

  useEffect(() => {
    if (stopId) {
      dispatch(fetchArrivalPredictions(stopId));
    }
  }, [dispatch, stopId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h2>Previsão de Chegada</h2>
      <ul>
        {predictions.map(prediction => (
          <li key={prediction.vehicleId}>
            {prediction.vehicleId} - {prediction.arrivalTime}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ArrivalPredictions;
