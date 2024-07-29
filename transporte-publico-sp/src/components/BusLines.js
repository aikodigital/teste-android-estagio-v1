import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchBusLines } from '../redux/actions';

const BusLines = () => {
  const dispatch = useDispatch();
  const { data: busLines, loading, error } = useSelector((state) => state.busLines);

  useEffect(() => {
    dispatch(fetchBusLines());
  }, [dispatch]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h2>Linhas de Ônibus</h2>
      <ul>
        {busLines.map(line => (
          <li key={line.id}>
            {line.name} - {line.description}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default BusLines;
