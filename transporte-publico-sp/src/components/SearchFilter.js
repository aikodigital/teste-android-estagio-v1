// src/components/SearchFilter.js
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { searchLines } from '../redux/actions';

const SearchFilter = () => {
  const [query, setQuery] = useState('');
  const dispatch = useDispatch();

  const handleSearch = () => {
    dispatch(searchLines(query));
  };

  return (
    <div>
      <input
        type="text"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        placeholder="Search for bus lines..."
      />
      <button onClick={handleSearch}>Search</button>
    </div>
  );
};

export default SearchFilter;
